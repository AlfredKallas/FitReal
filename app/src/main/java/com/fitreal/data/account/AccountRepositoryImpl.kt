package com.fitreal.data.account

import com.fitreal.di.IoDispatcher
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : AccountRepository {

    override val currentUser: Flow<User?>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser.toNotesUser())
                }
            Firebase.auth.addAuthStateListener(listener)
            awaitClose { Firebase.auth.removeAuthStateListener(listener) }
        }.flowOn(dispatcher)

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override fun hasUser(): Boolean {
        return Firebase.auth.currentUser != null
    }

    override suspend fun linkAccountWithGoogle(idToken: String) {
        withContext(dispatcher) {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            Firebase.auth.currentUser?.linkWithCredential(firebaseCredential)?.await()
        }
    }

    override suspend fun linkAccountWithEmail(email: String, password: String) {
        withContext(dispatcher) {
            val credential = EmailAuthProvider.getCredential(email, password)
            Firebase.auth.currentUser?.linkWithCredential(credential)?.await()
        }
    }

    override suspend fun signUp(email: String, password: String) {
        withContext(dispatcher) {
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun signInWithGoogle(idToken: String) {
        withContext(dispatcher) {
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
            Firebase.auth.signInWithCredential(firebaseCredential).await()
        }
    }

    override suspend fun signInWithEmail(email: String, password: String) {
        withContext(dispatcher) {
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun signOut() {
        withContext(dispatcher) {
            Firebase.auth.signOut()
        }
    }

    private fun FirebaseUser?.toNotesUser(): User {
        return if (this == null) User() else User(
            id = this.uid,
            email = this.email ?: "",
            provider = this.providerId,
            displayName = this.displayName ?: "",
            isAnonymous = this.isAnonymous
        )
    }
}