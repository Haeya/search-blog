package com.example.searchblog.service

import com.example.searchblog.repository.KeywordRepository
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
@SpringBootTest
class KeywordServiceTest(
    private val keywordService: KeywordService,
    private val keywordRepository: KeywordRepository
) : AnnotationSpec()  {
    val logger: Logger = LoggerFactory.getLogger(KeywordServiceTest::class.java)
    @Test
    fun `test saveKeyword`() {
        val keyword = "save"
        keywordService.saveKeyword(keyword)
        val savedKeyword = keywordRepository.findByKeyword(keyword).get()
        logger.info(savedKeyword.toString())
        keyword shouldBe savedKeyword.keyword
    }

    @Test
    fun `test countKeyword`() {
        val keyword = "count"
        keywordService.saveKeyword(keyword)
        val count  = keywordService.countKeyword()
        logger.info(count.toString())
        count.toString() shouldBe "{count=1}"
    }
}