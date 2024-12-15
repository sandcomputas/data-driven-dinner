package no.sondre.repository

interface SQLModel<POJO> {
    fun toPOJO(): POJO
}

interface SQLModelCreator<POJO, SQLModel> {
    fun fromPOJO(pojo: POJO): SQLModel
}
