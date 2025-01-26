package no.sondre.domain

/**
 * Used so that compiler plugin creates a no-args constructor which is needed
 * by Jackson when deserializing.
 */

// TODO: not sure if this is needed anymore as we have added the jackson-kotlin dependency
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class NoArg
