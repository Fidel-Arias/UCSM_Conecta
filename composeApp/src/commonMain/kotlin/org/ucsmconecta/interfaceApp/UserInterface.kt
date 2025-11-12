package org.ucsmconecta.interfaceApp

import androidx.compose.runtime.Composable
import org.ucsmconecta.util.TokenStorage

@Composable
expect fun InterfazUserApp(tokenStorage: TokenStorage)
