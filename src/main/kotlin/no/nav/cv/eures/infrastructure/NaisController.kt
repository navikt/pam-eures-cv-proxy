package no.nav.cv.eures.infrastructure

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/internal")
class NaisController {

    @Get("/ready")
    fun ready() = "Ok"

    @Get("/alive")
    fun alive() = "Ok"

}