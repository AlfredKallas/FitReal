package com.fitreal.data.account

import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    val currentUser: Flow<User?>
    val currentUserId: String
    fun hasUser(): Boolean
    suspend fun linkAccountWithGoogle(idToken: String)
    suspend fun linkAccountWithEmail(email: String, password: String)
    suspend fun signUp(email: String, password: String)
    suspend fun signInWithGoogle(idToken: String)
    suspend fun signInWithEmail(email: String, password: String)
    suspend fun signOut()
}