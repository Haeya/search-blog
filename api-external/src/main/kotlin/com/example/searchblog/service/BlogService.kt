package com.example.searchblog.service

import org.springframework.core.env.Environment
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class BlogService (
    env: Environment,
    val restTemplate: RestTemplate,
) {
    private val kakaoApiUrl = env.getProperty("kakao.openapi.url").toString()
    private val kakaoApiKey = env.getProperty("kakao.openapi.key").toString()
    private val naverApiUrl = env.getProperty("naver.openapi.url").toString()
    private val naverApiID = env.getProperty("naver.openapi.client.id").toString()
    private val naverApiKey = env.getProperty("naver.openapi.client.secret").toString()

    fun getBlogs(query: String, page: Int, size: Int, sort: String) : Any? {
        try {
            if (page !in 1 .. 50 || size !in 1 .. 50)
                throw IllegalArgumentException(
                    "page 혹은 size 값은 1 ~ 50 사이여야 합니다. page: $page, size: $size"
                )

            val headers = HttpHeaders()
            headers.add("Authorization", "KakaoAK $kakaoApiKey")
            val httpEntity = HttpEntity<String>("parameters", headers)
            var response = restTemplate.exchange(
                "$kakaoApiUrl?query=$query&page=$page&size=$size&sort=$sort",
                HttpMethod.GET,
                httpEntity,
                Any::class.java
            )
            if (response.statusCode != HttpStatus.OK)
                response = getNaverBlogs(query, page, size, sort)
            return response.body
        } catch (e: Exception) {
            throw RuntimeException("API 요청/응답 실패", e)
        }
    }

    fun getNaverBlogs(query: String, page: Int, size: Int, sort: String) :ResponseEntity<Any> {
        try {
            val headers = HttpHeaders()
            // naver api - sort parameter, default value = sim
            val naverSort = if (sort == "accuracy") "sim" else "date"
            val start = page * size + 1
            headers.add("X-Naver-Client-Id", "$naverApiID")
            headers.add("X-Naver-Client-Secret", "$naverApiKey")
            val httpEntity = HttpEntity<String>("parameters", headers)
            val response = restTemplate.exchange(
                "$naverApiUrl?query=$query&start=$start&display=$size&sort=$naverSort",
                HttpMethod.GET,
                httpEntity,
                Any::class.java
            )
            return response
        } catch (e: Exception) {
            throw RuntimeException("NAVER API 요청/응답 실패", e)
        }
    }
}