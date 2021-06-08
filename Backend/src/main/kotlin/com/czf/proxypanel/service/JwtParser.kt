package com.czf.proxypanel.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.*
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter


object JwtParser {
    private const val key = "dhhjsbkabgfhdbsakhbflkbasdvhkj"

    fun generateJwt(userName: String, ttlDay: Int, role: String): String {
        val signatureAlgorithm = SignatureAlgorithm.HS256
        val keyBytes = DatatypeConverter.parseBase64Binary(key)
        val signKey = SecretKeySpec(keyBytes, signatureAlgorithm.jcaName)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, ttlDay)

        val builder = Jwts.builder().setHeaderParam("typ", "JWT")
            .claim("userName", userName)
            .claim("role", role)
            .signWith(signatureAlgorithm, signKey)
            .setExpiration(calendar.time)
            .setNotBefore(Date())

        return builder.compact()
    }

    fun parseJwt(jwt: String): Claims? {
        val keyBytes = DatatypeConverter.parseBase64Binary(key)
        return try {
            Jwts.parser()
                .setSigningKey(keyBytes)
                .parseClaimsJws(jwt)
                .body
        } catch (e: Exception) {
            null
        }
    }
}