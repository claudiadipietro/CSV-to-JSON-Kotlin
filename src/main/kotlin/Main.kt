import entities.Base64File
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import utils.getCsvInJson
import io.ktor.server.plugins.cors.*

fun main(){
    embeddedServer(Netty, port = 8080, watchPaths = listOf("classes")) {
        install(CORS) {
            allowMethod(HttpMethod.Put)
            allowMethod(HttpMethod.Post)
            allowMethod(HttpMethod.Delete)
            allowHeader(HttpHeaders.Authorization)
            allowHeader(HttpHeaders.ContentType)
            allowCredentials = true
            allowNonSimpleContentTypes = true
            anyHost()
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        routing {
            post("api/csv") {
                val request = call.receive<Base64File>()
                if (request.base64File.isEmpty()){
                    call.respond("The CSV is empty")
                }
                call.respond(getCsvInJson(request.base64File))
            }
        }
    }.start(wait = true)
}