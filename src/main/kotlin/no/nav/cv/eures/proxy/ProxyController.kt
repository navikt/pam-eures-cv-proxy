package no.nav.cv.eures.proxy

import io.micronaut.context.annotation.Value
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client

@Controller("input/api/cv/v1.0")
class ProxyController (
        @Client("\${pam-eures-cv-eksport.url}")  private val client: HttpClient,
        @Value("\${pam-eures-cv-eksport.scheme}") private val scheme: String,
        @Value("\${pam-eures-cv-eksport.proxiedhost}") private val host: String,
        @Value("\${pam-eures-cv-eksport.proxiedport}") private val port: Int
) {


        @Get("ping")
        fun ping() = client.toBlocking().retrieve("/input/api/cv/v1.0/ping")

        @Get("getAll", produces = ["application/json"])
        fun getAll() =
                client.toBlocking().retrieve("/input/api/cv/v1.0/getAll")

        @Get("getChanges/{modificationTimestamp}", produces = ["application/json"])
        fun getChanges(modificationTimestamp: String) =
                client.toBlocking().retrieve("/input/api/cv/v1.0/getChanges/$modificationTimestamp")

        @Post("getDetails", produces = ["application/json"])
        fun getDetails(@Body references: String) =
                client.toBlocking().exchange(
                        HttpRequest
                                .POST("/input/api/cv/v1.0/ping", references)
                                .contentType(MediaType.APPLICATION_JSON_TYPE)
                                .accept(MediaType.TEXT_PLAIN_TYPE), String::class.java
                )


}