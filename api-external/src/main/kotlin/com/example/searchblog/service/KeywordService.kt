package com.example.searchblog.service

import com.example.searchblog.domain.KeywordLog
import com.example.searchblog.repository.KeywordRepository
import org.springframework.stereotype.Service

@Service
class KeywordService(
    private val keywordRepository: KeywordRepository
) {

    fun saveKeyword(keyword: String): KeywordLog {
        val keywordLog = KeywordLog(keyword = keyword)
        return keywordRepository.save(keywordLog)
    }

    fun countKeyword(): Map<String, Int> {
        val keywordLogs = keywordRepository.findAll()
        val counts = mutableMapOf<String, Int>()
        for (keywordLog in keywordLogs) {
            counts[keywordLog.keyword] = (counts[keywordLog.keyword] ?: 0) + 1
        }
        return counts
    }
}
