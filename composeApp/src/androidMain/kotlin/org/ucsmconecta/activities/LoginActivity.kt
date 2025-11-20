package org.ucsmconecta.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.lifecycleScope
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import kotlinx.coroutines.launch
import org.ucsmconecta.interfaceApp.App
import org.ucsmconecta.util.TokenStorage
import org.ucsmconecta.util.getTokenStorage
import java.io.IOException
import java.security.GeneralSecurityException

class LoginActivity : ComponentActivity() {
    // Obtenemos una instancia del TokenStorage (que ya maneja la lógica de inicialización)
    private val tokenStorage: TokenStorage = getTokenStorage()

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Ocultar la barra de estado y navegación
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        lifecycleScope.launch {
            // getToken() ya se encarga de:
            // 1. Inicializar EncryptedSharedPreferences (con MasterKey y manejo de errores).
            // 2. Comprobar si hay un token válido.
            val token = tokenStorage.getToken()

            if (token != null) {
                startActivityInterface()
                finish()
                return@launch
            }

            setContent {
                App { startActivityInterface() }
            }
        }
    }

    private fun startActivityInterface() {
        startActivity(Intent(this, InterfaceActivity::class.java))
    }
}