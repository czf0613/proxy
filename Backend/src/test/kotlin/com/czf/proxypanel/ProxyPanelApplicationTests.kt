package com.czf.proxypanel

import com.czf.proxypanel.controller.AccountController
import com.czf.proxypanel.controller.dto.SignUpRequest
import com.czf.proxypanel.entity.ChargingStrategy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ProxyPanelApplicationTests {
    @Autowired
    lateinit var accountController: AccountController

    @Test
    fun contextLoads() {
        accountController.signUp(SignUpRequest("leo", "leo123465", ChargingStrategy.ByAmount))
    }

}
