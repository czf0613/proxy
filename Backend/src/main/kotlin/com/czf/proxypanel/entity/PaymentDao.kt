package com.czf.proxypanel.entity

import org.springframework.data.jpa.repository.JpaRepository

interface PaymentDao : JpaRepository<Payment, Int> {
    fun findAllByUserId(userId: Int): List<Payment>
}