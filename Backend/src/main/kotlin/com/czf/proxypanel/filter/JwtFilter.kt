package com.czf.proxypanel.filter

import com.czf.proxypanel.service.JwtParser
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtFilter : GenericFilterBean() {
    override fun doFilter(p0: ServletRequest?, p1: ServletResponse?, p2: FilterChain?) {
        val req = p0 as HttpServletRequest
        val resp = p1 as HttpServletResponse
        val authHeader = req.getHeader("authorization")
        if (authHeader != null && authHeader.startsWith("bearer;")) {
            val token = authHeader.substring(7)
            //解析jwt字符串
            val claims = JwtParser.parseJwt(token)
            if (claims != null) {
                req.setAttribute("userName", claims["userName"])
                req.setAttribute("role", claims["role"])
                p2?.doFilter(req, resp)
                return
            }
        }

        //验证未通过直接返回
        resp.status = HttpServletResponse.SC_UNAUTHORIZED
        resp.characterEncoding = "UTF-8"
        resp.contentType = "application/json;charset=utf-8"
    }
}