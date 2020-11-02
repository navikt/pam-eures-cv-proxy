package no.nav.cv.eures.proxy

import io.micronaut.context.annotation.Value
import io.micronaut.core.async.publisher.Publishers
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.RxHttpClient
import io.micronaut.http.client.annotation.Client
import org.reactivestreams.Publisher
import java.util.function.Function

@Controller("naiv/input/api/cv/v1.0")
class ProxyController (
        @Client("\${pam-eures-cv-eksport.url}")  private val client: RxHttpClient,
        @Value("\${pam-eures-cv-eksport.scheme}") private val scheme: String,
        @Value("\${pam-eures-cv-eksport.proxiedhost}") private val host: String,
        @Value("\${pam-eures-cv-eksport.proxiedport}") private val port: Int
) {


        @Get("ping")
        fun ping(): Publisher<String> = Publishers.map(
                client.retrieve("/input/api/cv/v1.0/ping"),
                Function { response: String -> response }
        )

        @Get("getAll", produces = ["application/json"])
        fun getAll() =
                client.retrieve("/input/api/cv/v1.0/getAll")

        @Get("getChanges/{modificationTimestamp}", produces = ["application/json"])
        fun getChanges(modificationTimestamp: String) =
                client.retrieve("/input/api/cv/v1.0/getChanges/$modificationTimestamp")

        @Post("getDetails", produces = ["application/json"])
        fun getDetails(@Body references: String) =
                client.exchange(
                        HttpRequest
                                .POST("/input/api/cv/v1.0/ping", references)
                                .contentType(MediaType.APPLICATION_JSON_TYPE)
                                .accept(MediaType.TEXT_PLAIN_TYPE), String::class.java
                )


}