package no.sondre

import jakarta.persistence.*
import java.util.*


@Entity
@Table(name = "food")
class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
    lateinit var name: String
}

