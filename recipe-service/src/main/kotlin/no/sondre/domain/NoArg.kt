package no.sondre.domain

/**
 * Used so that compiler plugin creates a no-args constructor which is needed
 * by Jackson when deserializing.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class NoArg