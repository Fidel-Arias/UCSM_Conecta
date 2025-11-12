package org.ucsmconecta

import androidx.compose.ui.window.ComposeUIViewController
import org.ucsmconecta.interfaceApp.App

fun MainViewController() = ComposeUIViewController { App(startActivityInterface()) }