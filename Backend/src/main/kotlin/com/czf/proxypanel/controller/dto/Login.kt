package com.czf.proxypanel.controller.dto

import com.czf.proxypanel.entity.ChargingStrategy

data class LoginRequest(val userName: String, val password: String)

data class LoginResponse(
    val userName: String,
    val jwt: String,
    val chargingStrategy: ChargingStrategy,
    val usage: Long
)