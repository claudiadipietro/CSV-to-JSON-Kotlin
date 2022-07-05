import entities.Base64File
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json
import java.util.*

fun main(){
    embeddedServer(Netty, port = 8080, watchPaths = listOf("classes")) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        routing {
            post("/login") {
                val request = call.receive<Base64File>()
                if (request.base64File.isEmpty()){
                    call.respond("The CSV is empty")
                }
                val decodedCSV = Base64.getDecoder().decode(request.base64File)
                call.respond(decodedCSV)
            }
        }
    }.start(wait = true)
}

