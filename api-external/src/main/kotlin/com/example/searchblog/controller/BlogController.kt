package com.example.searchblog.controller

import com.example.searchblog.service.BlogService
import com.example.searchblog.service.KeywordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/blog")
class BlogController(
    private val blogService: BlogService,
    private val keywordService: KeywordService,
) {
    /**
     * 블로그 검색
     * @param query 검색키워드
     * @param page 페이지(default = 1)
     * @param size 데이터 개수(default = 10)
     * @param sort 검색 기준(accuracy|recency)
     * @return ResponseEntity<*>
     */
    @GetMapping
    fun getBlogs(
        @RequestParam(required = true) query: String,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "10") size: Int,
        @RequestParam(required = false, defaultValue = "accuracy") sort: String,
    ): ResponseEntity<*> {
        val blogs = blogService.getBlogs(query, page, size, sort)
        keywordService.saveKeyword(query)
        val result = mapOf("data" to blogs, "message" to "success")
        return ResponseEntity.ok(result)
    }
}