package com.lexwilliam.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.lexwilliam.data.mapper.TestResultMapper
import com.lexwilliam.data.model.TestResultResponse
import com.lexwilliam.domain.TestResultRepository
import com.lexwilliam.domain.UserRepository
import com.lexwilliam.domain.model.Result
import com.lexwilliam.domain.model.TestResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

class TestResultRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userRepository: UserRepository,
    private val testResultMapper: TestResultMapper
): TestResultRepository {

    override suspend fun insertTestResult(testResult: TestResult) {
        userRepository.getUserProfile().collect { user ->
            when (user) {
                is Result.Success -> {
                    val userId = user.data.uid
                    firestore.collection("results").document(userId)
                        .set(testResult)
                        .addOnSuccessListener {
                            Timber.d("DocumentSnapshot successfully written!")
                        }
                        .addOnFailureListener { e ->
                            Timber.tag("Error adding document").e(e)
                        }
                }
                is Result.Loading -> {
                    Timber.d("Loading")
                }
                is Result.Error -> {
                    Timber.d("Error")
                }
            }
        }
    }

    override suspend fun getTestResult(): Flow<Result<TestResult>> = callbackFlow {
        trySend(Result.Loading)

        lateinit var subscription: ListenerRegistration

        userRepository.getUserProfile().collect { user ->
            when (user) {
                is Result.Success -> {
                    subscription = firestore.collection("results").document(user.data.uid)
                        .addSnapshotListener { value, error ->
                            if (error != null) {
                                error.message?.let {
                                    Timber.d(it)
                                    trySend(Result.Error(it))
                                }
                            } else {
                                value?.let { snapshot ->
                                    if (!snapshot.exists()) {
                                        val testResult = TestResultResponse(user.data.uid, emptyList())
                                        firestore.collection("results").document(user.data.uid)
                                            .set(testResult)
                                            .addOnSuccessListener {
                                                Timber.d("DocumentSnapshot successfully written!")
                                            }
                                            .addOnFailureListener { e ->
                                                Timber.tag("Error adding document").e(e)
                                            }
                                    }
                                    val data = snapshot.toObject(TestResultResponse::class.java)
                                    data?.let {
                                        Timber.d(it.toString())
                                        trySend(Result.Success(testResultMapper.toDomain(it)))
                                    }
                                }
                            }
                        }
                }
                is Result.Loading -> {
                    Timber.d("Loading")
                    trySend(Result.Loading)
                }
                is Result.Error -> {
                    trySend(Result.Error("User not found"))
                }
            }
        }

        awaitClose {
            subscription.remove()
        }
    }
}