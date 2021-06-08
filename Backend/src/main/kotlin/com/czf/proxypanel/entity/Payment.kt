package com.czf.proxypanel.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

@Entity(name = "payment")
class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0

    @JsonIgnore
    @Column(name = "user_id", nullable = false, length = 11)
    var userId = 0

    @Column(nullable = false, scale = 2, precision = 6)
    var price = BigDecimal.ZERO

    @Column(nullable = false, columnDefinition = "datetime")
    var time = Date()
}