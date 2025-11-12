package org.ucsmconecta.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.util.InternalAPI
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import okio.IOException
import org.ucsmconecta.data.errors.ErrorResponse
import org.ucsmconecta.data.model.asistencia.DataResponseContarAsistencias
import org.ucsmconecta.data.model.bloque.BloqueResponse
import org.ucsmconecta.data.model.comentario.ComentarioRequest
import org.ucsmconecta.data.model.comentario.ComentarioResponse
import org.ucsmconecta.data.model.dia.TimeResponse
import org.ucsmconecta.data.model.login.LoginRequest
import org.ucsmconecta.data.model.login.LoginResponse
import org.ucsmconecta.data.model.participante.ParticipanteResponse
import org.ucsmconecta.infra.errors.ConnectionError
import org.ucsmconecta.infra.errors.EntityNotFound
import org.ucsmconecta.infra.errors.UnauthorizedException
import org.ucsmconecta.util.TokenStorage
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class ApiService(
    private val client: HttpClient,
    private val tokenStorage: TokenStorage?
) {
    private val BASE_URL = "http://vps-5440778-x.dattaweb.com:8080"

    private suspend fun llamarToken() = tokenStorage?.getToken() ?: throw IllegalStateException("Token no encontrado")

    suspend fun login(request: LoginRequest): Result<LoginResponse> {
        return try {
            val response: HttpResponse = client.post("$BASE_URL/api/auth/login") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            val bodyText = response.bodyAsText()
            val json = Json { ignoreUnknownKeys = true }

            if (response.status.isSuccess()) {
                // Intentar parsear como LoginResponse
                val loginResponse = json.decodeFromString<LoginResponse>(bodyText)
                Result.success(loginResponse)
            } else {
                // Si no es éxito, intentar leer mensaje de error
                val errorResponse = try {
                    json.decodeFromString<ErrorResponse>(bodyText)
                } catch (_: Exception) {
                    ErrorResponse("Error desconocido: ${response.status}")
                }
                Result.failure(Exception(errorResponse.error))
            }

        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }

    suspend fun obtenerDatosParticipante(id: Long): Result<ParticipanteResponse> {
        return try {
            val token = llamarToken()

            val response = client.get("$BASE_URL/api/participante/$id") {
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }

            return when(response.status) {
                HttpStatusCode.Forbidden -> {
                    Result.failure(UnauthorizedException("Token expirado o no autorizado"))
                }

                HttpStatusCode.NotFound -> {
                    Result.failure(EntityNotFound("Participante no existe"))
                }
                else -> {
                    val body = response.body<ParticipanteResponse>()
                    Result.success(body)
                }
            }
        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }

    suspend fun obtenerBloques(): Result<List<BloqueResponse>> {
        return try {
            val token = llamarToken()

            val response = client.get("$BASE_URL/api/participante/bloques") {
                contentType(ContentType.Application.Json)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }

            println("RESPONSE STATUS: ${response.status}")
            return when(response.status) {
                HttpStatusCode.Forbidden -> {
                    Result.failure(UnauthorizedException("Token expirado o no autorizado"))
                }
                HttpStatusCode.NotFound -> {
                    Result.failure(EntityNotFound("No existen ponencias"))
                }
                HttpStatusCode.NoContent -> {
                    Result.success(emptyList())
                }
                else -> {
                    val body = response.body<List<BloqueResponse>>()
                    Result.success(body)
                }
            }
        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }

    @OptIn(ExperimentalTime::class)
    suspend fun obtenerFechaServidor(): String {
        val token = llamarToken()
        val response = client.get("$BASE_URL/api/participante/time") {
            contentType(ContentType.Application.Json)
            headers {
                append(HttpHeaders.Authorization, "Bearer $token")
            }
        }
        val body = response.body<TimeResponse>()
        // Parsear la fecha del servidor con kotlinx-datetime
        val instant = Instant.parse(body.serverTime)
        val timeZone = TimeZone.currentSystemDefault()
        val localDate = instant.toLocalDateTime(timeZone).date
        return localDate.toString()
    }

    @OptIn(InternalAPI::class)
    suspend fun obtenerCantidadAsistencias(
        numDocumento: String,
        congresoCod: String
    ): Result<DataResponseContarAsistencias> {
        return try {
            val token = llamarToken()

            val response = client.get("$BASE_URL/api/participante/contar") {
                contentType(ContentType.Application.Json)
                parameter("numDocumento", numDocumento)
                parameter("congresoCod", congresoCod)
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                }
            }

            when (response.status) {
                HttpStatusCode.OK -> {
                    val body = response.body<DataResponseContarAsistencias>()
                    Result.success(body)
                }
                HttpStatusCode.Forbidden -> {
                    Result.failure(UnauthorizedException("Token expirado o no autorizado"))
                }
                HttpStatusCode.NotFound -> {
                    Result.failure(EntityNotFound("No se encontraron asistencias"))
                }
                else -> {
                    Result.failure(Exception("Error inesperado: ${response.status}"))
                }
            }
        } catch (e: SocketTimeoutException) {
            Result.failure(ConnectionError("Tiempo de espera agotado"))
        } catch (e: IOException) {
            Result.failure(ConnectionError("Error de conexión"))
        } catch (e: ConnectionError) {
            Result.failure(ConnectionError("Conéctate a Internet"))
        }
    }
}