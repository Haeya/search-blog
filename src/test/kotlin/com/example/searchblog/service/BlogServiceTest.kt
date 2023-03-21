package com.example.searchblog.service

import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
@SpringBootTest
class BlogServiceTest {
    @Autowired
    private lateinit var blogService: BlogService

    @Test
    fun `getBlogs should return response body`() {
        val responseEntity = ResponseEntity("response body", HttpStatus.OK)
        `when`(blogService.restTemplate.exchange(
            "https://example.com", HttpMethod.GET, null, Any::class.java)
        ).thenReturn(responseEntity)

        val result = blogService.getBlogs("query", 1, 10, "accuracy")

        assertEquals("response body", result)
    }
}

private fun Any.thenReturn(responseEntity: ResponseEntity<String>) {

}
