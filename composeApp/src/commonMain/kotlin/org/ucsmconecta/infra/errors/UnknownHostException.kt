package org.ucsmconecta.infra.errors

class ConnectionError(message: String) : Exception(message)

class UnauthorizedException(message: String = "Token expirado o no autorizado"): Exception(message)