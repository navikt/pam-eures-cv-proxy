package no.nav.cv.eures

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("no.nav.cv.eures")
		.start()
}

