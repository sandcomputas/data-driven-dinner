package no.sondre.repository

interface SQLModel<POJO> {
    fun toPOJO(): POJO
    fun update(new: POJO)
}

interface SQLModelCreator<POJO, SQLModel> {
    fun fromPOJO(pojo: POJO): SQLModel
}
