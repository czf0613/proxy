package com.czf.proxypanel.entity

import java.math.BigInteger
import java.util.*
import javax.persistence.*

@Entity(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id = 0

    @Column(name = "username", unique = true, length = 50, nullable = false)
    var userName = ""

    @Column(unique = true, length = 50, nullable = false)
    var password = ""

    @Column(scale = 20, columnDefinition = "BIGINT", nullable = false)
    var quota = BigInteger("-1")

    @Column(scale = 20, columnDefinition = "BIGINT", nullable = false)
    var upload = BigInteger.ZERO

    @Column(scale = 20, columnDefinition = "BIGINT", nullable = false)
    var download = BigInteger.ZERO

    @Column(name = "valid_before", nullable = false, columnDefinition = "datetime")
    var validBefore = Date()

    @Column(name = "charging_strategy", nullable = false, columnDefinition = "INT", length = 11)
    var chargingStrategy = ChargingStrategy.ByDateRange
}

enum class ChargingStrategy {
    ByDateRange,
    ByAmount
}