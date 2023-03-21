package com.example.searchblog.service

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BlogServiceTest(private val blogService: BlogService) : AnnotationSpec() {

    @Test
    fun `test GetBlogs`() {
        val query = "example query"
        val page = 1
        val size = 10
        val kakaoResult1 = blogService.getBlogs(query, page, size, "accuracy")
        val kakaoResult2 = blogService.getBlogs(query, page, size, "recency")
        val naverResult1 = blogService.getNaverBlogs(query, page, size, "sim")
        val naverResult2 = blogService.getNaverBlogs(query, page, size, "date")
        kakaoResult1 shouldNotBe kakaoResult2
        kakaoResult1 shouldNotBe naverResult1
        kakaoResult1 shouldNotBe naverResult2
        kakaoResult2 shouldNotBe naverResult1
        kakaoResult2 shouldNotBe naverResult2
    }
}
