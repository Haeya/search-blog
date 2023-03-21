package com.example.searchblog.controller

import com.example.searchblog.service.KeywordService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/keyword")
class KeywordController (
    private val keywordService: KeywordService,
) {
    val logger: Logger = LoggerFactory.getLogger(KeywordController::class.java)
    /**
     * 인기 검색어 목록
     * @return List<Map<String, Any>>
     */
    @GetMapping
    fun getKeywords(): Map<String, List<Map<String, Any>>> {
        val keywordMap = keywordService.countKeyword()
        val outputList = keywordMap
            .map { (keyword, count) -> mapOf("keyword" to keyword, "count" to count) }
            .sortedByDescending { it["count"] as Int}
            .take(10)
        return mapOf("popularKeywords" to outputList)
    }
}