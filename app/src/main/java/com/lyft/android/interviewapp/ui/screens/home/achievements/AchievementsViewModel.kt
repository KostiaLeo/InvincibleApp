package com.lyft.android.interviewapp.ui.screens.home.achievements

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.lyft.android.interviewapp.data.remote.models.MyInfoResponse
import com.lyft.android.interviewapp.data.remote.models.Stat
import com.lyft.android.interviewapp.data.repository.IdentityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val identityRepository: IdentityRepository
) : ViewModel() {

    var uiState: AchievementsUiState by mutableStateOf(AchievementsUiState())
        private set

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable: Throwable ->
        Log.d("ERROR_ACHIEVEMENTS", throwable.message, throwable)
        uiState = uiState.copy(
            isLoading = false,
            errorMessage = throwable.message
        )
    }

    init {
        getAchievements()
    }

    private fun getAchievements() {
        viewModelScope.launch(exceptionHandler) {
            uiState = uiState.copy(
                isLoading = true
            )
            identityRepository.getMyInfo().let {
                Log.d("ACHIEVEMENTS", it.toString())
                uiState = uiState.copy(
                    isLoading = false,
                    experience = it.experience,
                    level = it.level,
                    statistics = it.statistics,
                    puzzlesCollection = it.mapToCollectionItems()
                )
            }
        }
    }

    fun refresh() {
        getAchievements()
    }
}

suspend fun MyInfoResponse.mapToCollectionItems(): List<PuzzleCollectionItem> = buildList {
    if (puzzleSlotsCount == 0) {
        add(PuzzleCollectionItem.NoItems)
        return@buildList
    }
    if (puzzleSlotsCount > completedPuzzlesIds.size && currentPuzzlePieces.isEmpty()) {
        add(PuzzleCollectionItem.EmptySlot)
    }
    val storageRef = Firebase.storage.reference

    if (currentPuzzlePieces.isNotEmpty()) {
        val currentPuzzlePiecesUrls = withContext(Dispatchers.IO) {
            currentPuzzlePieces
                .map { pieceId -> "puzzle_${currentPuzzleId}_$pieceId.png" }
                .map { puzzleName ->
                    val path = "puzzles/$puzzleName"
                    Log.d("ACHIEVEMENTS", path)
                    storageRef.child(path)
                }
                .map { puzzleRef -> async { puzzleRef.downloadUrl.await() } }
                .awaitAll()
                .map(Uri::toString)
        }
        add(PuzzleCollectionItem.CurrentPuzzle(currentPuzzlePiecesUrls, currentPuzzlePieces))
    }
    withContext(Dispatchers.IO) {
        addAll(
            completedPuzzlesIds
                .map { puzzleId -> "puzzle_$puzzleId.png" }
                .map { puzzleName -> storageRef.child("puzzles/$puzzleName") }
                .map { puzzleRef -> async { puzzleRef.downloadUrl.await() } }
                .awaitAll()
                .map(Uri::toString)
                .map { puzzleUrl ->
                    PuzzleCollectionItem.CompletedPuzzle(
                        puzzleUrl,
                        "Супергерой у своєму притулку",
                        "Андрій Науменко"
                    )
                }
        )
    }
    if (!contains(PuzzleCollectionItem.NoItems)) {
        add(PuzzleCollectionItem.Footer)
    }
}

sealed interface PuzzleCollectionItem {
    object NoItems : PuzzleCollectionItem
    object EmptySlot : PuzzleCollectionItem
    data class CurrentPuzzle(val piecesUrls: List<String>, val pieces: List<Int>) :
        PuzzleCollectionItem

    data class CompletedPuzzle(
        val puzzleUrl: String, val puzzleName: String, val author: String
    ) : PuzzleCollectionItem

    object Footer : PuzzleCollectionItem
}

data class AchievementsUiState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val experience: Int = 0,
    val level: Int = 0,
    val statistics: List<Stat> = emptyList(),
    val puzzlesCollection: List<PuzzleCollectionItem> = listOf(PuzzleCollectionItem.NoItems)
)