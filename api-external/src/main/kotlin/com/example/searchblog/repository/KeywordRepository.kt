package com.example.searchblog.repository

import com.example.searchblog.domain.KeywordLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface KeywordRepository : JpaRepository<KeywordLog, Long> {
    fun findByKeyword(keyword: String): Optional<KeywordLog>
}