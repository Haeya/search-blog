package com.example.searchblog.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.dao.DataIntegrityViolationException

@SpringBootTest
class KeywordServiceTest {
    @Autowired
    private lateinit var keywordService: KeywordService

    @Test
    fun `save keyword`() {
        val keyword = "test"
        val keywordLog = keywordService.saveKeyword(keyword)
        assertThat(keywordLog.id).isNotNull
        assertThat(keywordLog.keyword).isEqualTo(keyword)
    }

    @Test
    fun `counting keywords should return correct counts`() {
        keywordService.saveKeyword("test1")
        keywordService.saveKeyword("test2")
        keywordService.saveKeyword("test2")
        keywordService.saveKeyword("test2")
        keywordService.saveKeyword("test3")

        val counts = keywordService.countKeyword()
        assertThat(counts).hasSize(3)
        assertThat(counts[0]["keyword"]).isEqualTo("test2")
        assertThat(counts[0]["count"]).isEqualTo(3)
        assertThat(counts[1]["keyword"]).isEqualTo("test3")
        assertThat(counts[1]["count"]).isEqualTo(1)
        assertThat(counts[2]["keyword"]).isEqualTo("test1")
        assertThat(counts[2]["count"]).isEqualTo(1)
    }

    @Test
    fun `saving keyword with empty string should throw exception`() {
        assertThrows<DataIntegrityViolationException> {
            keywordService.saveKeyword("")
        }
    }
}