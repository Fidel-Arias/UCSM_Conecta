package org.ucsmconecta.model.interfaces

interface UserFunctions {
    fun getPassword(): String?
    fun setPassword(password: String)
    fun getRole(): String?
    fun registerUser(
        name: String,
        email: String,
        password: String
    ): Boolean
    fun loginUser(
        email: String,
        password: String
    ): Boolean
}