package com.czf.proxypanel.controller.dto

import com.czf.proxypanel.entity.ChargingStrategy
import com.czf.proxypanel.entity.Payment

data class Self(
    val userName: String,
    val chargingStrategy: ChargingStrategy,
    val usage: Long,
    val payment: List<Payment>
)