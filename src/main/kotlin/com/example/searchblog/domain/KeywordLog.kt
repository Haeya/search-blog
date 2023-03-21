package com.example.searchblog.domain

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Entity
import java.time.LocalDateTime

@Entity(name = "Keyword")
data class KeywordLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "keyword")
    val keyword: String = "",

    @Column(name = "createdAt")
    val createdAt: LocalDateTime = LocalDateTime.now()

)