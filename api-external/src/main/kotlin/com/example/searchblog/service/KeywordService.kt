package com.example.searchblog.service

import com.example.searchblog.domain.KeywordLog
import com.example.searchblog.repository.KeywordRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class KeywordService(
    private val keywordRepository: KeywordRepository
) {

    fun saveKeyword(keyword: String): KeywordLog {
        synchronized(this) {
            val keywordLog = KeywordLog(keyword = keyword)
            return keywordRepository.save(keywordLog)
        }
    }

    @Cacheable("keywordCounts")
    fun countKeyword(): Map<String, Int> {
        try {
            val keywordLogs = keywordRepository.findAll()
            val counts = HashMap<String, Int>()
            for (keywordLog in keywordLogs) {
                counts.compute(keywordLog.keyword) { _, count -> count?.plus(1) ?: 1 }
            }
            return counts
        } catch (e: Exception) {
            throw RuntimeException("countKeyword API 요청/응답 실패", e)
        }
    }
}
