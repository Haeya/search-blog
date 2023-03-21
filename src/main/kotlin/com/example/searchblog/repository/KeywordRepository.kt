package com.example.searchblog.repository

import com.example.searchblog.domain.KeywordLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface KeywordRepository : JpaRepository<KeywordLog, Long>