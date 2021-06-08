package com.czf.proxypanel.controller.dto

import com.czf.proxypanel.entity.ChargingStrategy
import java.util.*

data class SignUpRequest(val userName: String, val password: String, val chargingStrategy: ChargingStrategy)

data class SignUpResponse(val userName: String, val usage: Long, val validBefore: Date)