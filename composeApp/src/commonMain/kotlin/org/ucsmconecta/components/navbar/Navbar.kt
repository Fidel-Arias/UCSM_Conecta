package org.ucsmconecta.components.navbar

import androidx.compose.runtime.Composable

@Composable
expect fun Navbar(
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
)