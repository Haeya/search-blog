package com.example.searchblog.service

import org.springframework.core.env.Environment
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import kotlin.math.ceil

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

    fun getBlogs(query: String, page: Int, size: Int, sort: String) : ResponseEntity<Any> {
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
                throw Exception("Kakao API 호출 실패")
            val result = response.body as Map<*, *>
            val documents = result["documents"] as List<*>
            val meta = result["meta"] as Map<*, *>
            val totalCount = meta["total_count"] as Int
            val totalPages = if (totalCount == 0) 1 else ceil(totalCount.toDouble() / size).toInt()

            val paginationDto = PaginationDto(page, size, totalCount, totalPages)
            val responseBody = mapOf("documents" to documents, "pagination" to paginationDto)

            return ResponseEntity.ok().body(responseBody)
        } catch (e: Exception) {
            return getNaverBlogs(query, page, size, sort)
        }
    }

    fun getNaverBlogs(query: String, page: Int, size: Int, sort: String) : ResponseEntity<Any> {
        try {
            val headers = HttpHeaders()
            // naver api는 기본 sort parameter data가 sim
            val naverSort = if (sort == "accuracy") "sim" else "date"
            val start = (page-1) * size + 1
            headers.add("X-Naver-Client-Id", "$naverApiID")
            headers.add("X-Naver-Client-Secret", "$naverApiKey")
            val httpEntity = HttpEntity<String>("parameters", headers)
            val response = restTemplate.exchange(
                "$naverApiUrl?query=$query&start=$start&display=$size&sort=$naverSort",
                HttpMethod.GET,
                httpEntity,
                Any::class.java
            )
            val result = response.body as Map<*, *>
            val items = result["items"] as List<*>
            val total = result["total"] as Int
            val totalPages = if (total == 0) 1 else ceil(total.toDouble() / size).toInt()
            val paginationDto = PaginationDto(page, size, total, totalPages)
            val responseBody = mapOf("documents" to items, "pagination" to paginationDto)
            return ResponseEntity.ok().body(responseBody)
        } catch (e: Exception) {
            throw RuntimeException("getNaverBlogs API 요청/응답 실패", e)
        }
    }

    data class PaginationDto(
        val page: Int,
        val size: Int,
        val totalCount: Int,
        val totalPages: Int,
    )
}