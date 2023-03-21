package com.example.searchblog.controller

import com.example.searchblog.service.KeywordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/keyword")
class KeywordController (
    private val keywordService: KeywordService,
) {
    /**
     * 인기 검색어 목록
     * @return ResponseEntity<Map<String, Any>>
     */
    @GetMapping
    fun getKeywords(
        @RequestParam(required = false, defaultValue = "10") size: Int,
    ): ResponseEntity<Map<String, Any>> {
        if (size !in 1 .. 10)
            throw IllegalArgumentException(
                "size 값은 1 ~ 10 사이여야 합니다. size: $size"
            )
        val keywordMap = keywordService.countKeyword()
        val outputList = keywordMap
            .map { (keyword, count) -> mapOf("keyword" to keyword, "count" to count) }
            .sortedByDescending { it["count"] as Int}
            .take(size)
        val result = mapOf("data" to outputList, "message" to "success")
        return ResponseEntity.ok(result)
    }
}