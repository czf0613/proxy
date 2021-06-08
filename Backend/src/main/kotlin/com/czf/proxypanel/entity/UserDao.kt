package com.czf.proxypanel.entity

import org.springframework.data.jpa.repository.JpaRepository

interface UserDao : JpaRepository<User, Int> {
    fun findByUserName(userName: String): User?
}