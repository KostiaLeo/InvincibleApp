package com.lyft.android.interviewapp.domain

import com.lyft.android.interviewapp.data.repository.Repository
import javax.inject.Inject

class UseCase @Inject constructor(
    private val repository: Repository
) {
    suspend operator fun invoke() = Unit
}