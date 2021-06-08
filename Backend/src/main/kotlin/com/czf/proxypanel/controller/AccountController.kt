package com.czf.proxypanel.controller

import com.czf.proxypanel.controller.dto.*
import com.czf.proxypanel.entity.ChargingStrategy
import com.czf.proxypanel.entity.PaymentDao
import com.czf.proxypanel.entity.User
import com.czf.proxypanel.entity.UserDao
import com.czf.proxypanel.service.Encoder
import com.czf.proxypanel.service.JwtParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/account")
class AccountController {
    @Autowired
    private lateinit var userDao: UserDao

    @Autowired
    private lateinit var encoder: Encoder

    @Autowired
    private lateinit var paymentDao: PaymentDao

    @PostMapping("/signUp")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<SignUpResponse> {
        try {
            var entity = User().apply {
                userName = signUpRequest.userName
                password = encoder.sha244(signUpRequest.password)
                chargingStrategy = signUpRequest.chargingStrategy

                val calendar = Calendar.getInstance()
                if (signUpRequest.chargingStrategy == ChargingStrategy.ByDateRange)
                    calendar.add(Calendar.DATE, 3)
                else
                    calendar.set(9999, 0, 1)

                validBefore = calendar.time
            }
            entity = userDao.save(entity)

            val resp = SignUpResponse(entity.userName, 0, entity.validBefore)
            return ResponseEntity(resp, HttpStatus.OK)
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        val user = userDao.findByUserName(loginRequest.userName) ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        if (user.password != encoder.sha244(loginRequest.password))
            return ResponseEntity(HttpStatus.BAD_REQUEST)

        val resp = LoginResponse(
            loginRequest.userName,
            JwtParser.generateJwt(loginRequest.userName, 1, "common"),
            user.chargingStrategy,
            (user.download + user.upload).toLong()
        )

        return ResponseEntity(resp, HttpStatus.OK)
    }

    @PostMapping("/changePassword")
    fun changePassword(@RequestBody newPassword: String, request: HttpServletRequest): ResponseEntity<String> {
        val user = userDao.findByUserName(request.getAttribute("userName") as String)!!
        user.password = encoder.sha244(newPassword)
        userDao.save(user)

        return ResponseEntity(HttpStatus.OK)
    }

    @GetMapping("/userName/{user}")
    fun getSelf(@PathVariable user: String, request: HttpServletRequest): ResponseEntity<Self> {
        if (user != request.getAttribute("userName"))
            return ResponseEntity(HttpStatus.FORBIDDEN)
        val userEntity = userDao.findByUserName(user)!!
        var payments = paymentDao.findAllByUserId(userEntity.id)

        payments = payments.sortedByDescending {
            it.time
        }
        val resp = Self(user, userEntity.chargingStrategy, (userEntity.download + userEntity.upload).toLong(), payments)

        return ResponseEntity(resp, HttpStatus.OK)
    }

    @DeleteMapping("/userName/{user}")
    fun killSelf(@PathVariable user: String, request: HttpServletRequest): ResponseEntity<String> {
        if (user != request.getAttribute("userName"))
            return ResponseEntity(HttpStatus.FORBIDDEN)
        val userEntity = userDao.findByUserName(user)!!
        userDao.delete(userEntity)

        return ResponseEntity(HttpStatus.OK)
    }
}