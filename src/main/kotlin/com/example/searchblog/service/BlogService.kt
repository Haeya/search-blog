package com.example.searchblog.service

import org.springframework.stereotype.Service
import org.springframework.core.env.Environment
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@Service
class BlogService (
    env: Environment,
    val restTemplate: RestTemplate,
) {
    private val openApiUrl = env.getProperty("kakao.openapi.url").toString()
    private val openApiKey = env.getProperty("kakao.openapi.key").toString()

    fun getBlogs(query: String, page: Int, size: Int): ResponseEntity<Any> {
        try{
            val headers = HttpHeaders()
            headers.add("Authorization", "KakaoAK $openApiKey")
            val httpEntity = HttpEntity<String>("parameters", headers)
            val response = restTemplate.exchange(
                "$openApiUrl?query=$query&page=$page&size=$size",
                HttpMethod.GET,
                httpEntity,
                Any::class.java
            )
            return response
        } catch (e : Exception) {

        }
    }
}