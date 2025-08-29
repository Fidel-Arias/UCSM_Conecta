package org.ucsmconecta.model

import org.ucsmconecta.model.interfaces.UserFunctions

class AdminAuthentication: UserFunctions {
    var name: String? = null
    var email: String? = null
    private var password: String? = null
    private var role = "Admin"

    override fun getPassword(): String? {
        println("getPassword called")
        return password
    }

    override fun setPassword(password: String) {
        this.password = password
        println("setPassword called with: $password")
    }

    override fun getRole(): String? {
        println("getRole called")
        return role
    }

    override fun registerUser(
        name: String,
        email: String,
        password: String
    ): Boolean {
        println("user registered with name: $name, email: $email")
        return true
    }

    override fun loginUser(email: String, password: String): Boolean {
        println("User logged")
        return true
    }
}