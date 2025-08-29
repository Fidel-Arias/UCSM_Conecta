package org.ucsmconecta.structure

enum class Documents {
    DNI {
        // validar que se ingresa 8 caracteres
        fun validate(document: String): Boolean {
            return document.length == 8 && document.all { it.isDigit() }
        }
    },
    PASSPORT {
        // validar que se ingresa 9 caracteres
        fun validate(document: String): Boolean {
            return document.length == 9 && document.all { it.isDigit() }
        }
    },
    FOREIGNER_CARD {
        // validar que se ingresa 12 caracteres
        fun validate(document: String): Boolean {
            return document.length == 12 && document.all { it.isDigit() }
        }
    };
}