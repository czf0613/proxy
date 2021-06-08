package com.czf.proxypanel

import com.czf.proxypanel.filter.JwtFilter
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean

@SpringBootApplication
class ProxyPanelApplication

fun main(args: Array<String>) {
    runApplication<ProxyPanelApplication>(*args)
}

@Bean
fun addJwtFilter(): FilterRegistrationBean<JwtFilter> {
    return FilterRegistrationBean<JwtFilter>()
        .apply {
            filter = JwtFilter()
            urlPatterns = listOf(
                "/account/changePassword",
                "/account/userName/**",
                "/payment/**"
            )
        }
}
