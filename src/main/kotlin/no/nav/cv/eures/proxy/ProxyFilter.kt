package no.nav.cv.eures.proxy

import io.micronaut.context.annotation.Value
import io.micronaut.core.async.publisher.Publishers
import io.micronaut.core.util.StringUtils
import io.micronaut.http.HttpRequest
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.client.ProxyHttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.filter.OncePerRequestHttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.micronaut.http.uri.UriBuilder
import io.micronaut.runtime.server.EmbeddedServer
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import java.util.function.Function



@Filter("/input/api/cv/**")
class ProxyFilter(
        @Client private val client: ProxyHttpClient,
        @Value("\${pam-eures-cv-eksport.scheme}") private val scheme: String,
        @Value("\${pam-eures-cv-eksport.proxiedhost}") private val host: String,
        @Value("\${pam-eures-cv-eksport.proxiedport}") private val port: Int
) : OncePerRequestHttpServerFilter() {

    companion object {

        val log = LoggerFactory.getLogger(ProxyFilter::class.java)

    }

    override fun doFilterOnce(request: HttpRequest<*>, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> {

        log.info("Request recieved at ${request.path}. Forwarding to ${scheme}://${host}:${port}")
        return Publishers.map(client.proxy(
                request.mutate()
                        .uri { b: UriBuilder ->
                            b.apply {
                                scheme(scheme)
                                host(host)
                                port(port)
                                replacePath(request.path)
                            }.apply { log.debug("Constructed uri: ${toString()}") }
                        }
        ), Function {
            response: MutableHttpResponse<*> -> response })
    }

}