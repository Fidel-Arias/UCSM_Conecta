package org.ucsmconecta.interfaceApp

import androidx.compose.runtime.*

@Composable
expect fun App(startActivityInterface: () -> Unit)