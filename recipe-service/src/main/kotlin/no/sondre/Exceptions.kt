package no.sondre

// TODO: add exception handler

class BadRequest(message: String) : Exception(message)

class NotFound(message: String): Exception(message)

// This message should probably just be logged and not returned to user
class InternalError(message: String) : Exception(message)