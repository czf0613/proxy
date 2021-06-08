package com.czf.proxypanel.service

import org.springframework.stereotype.Service
import java.math.BigInteger
import java.security.MessageDigest

@Service
class Encoder {
    fun sha244(raw: String): String {
        val md = MessageDigest.getInstance("SHA-224")
        val messageDigest = md.digest(raw.encodeToByteArray())
        val no = BigInteger(1, messageDigest)

        return no.toString(16)
    }
}