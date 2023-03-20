package com.example.searchblog.controller

import com.example.searchblog.service.BlogService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/blog")
class BlogController(private val blogService: BlogService) {
    val logger: Logger = LoggerFactory.getLogger(BlogController::class.java)
    @GetMapping
    fun getBlogs(
        @RequestParam(required = true) query: String,
        @RequestParam(required = true, defaultValue = "1") page: Int,
        @RequestParam(required = true, defaultValue = "10") size: Int
    ): ResponseEntity<*> {
        val blogs = blogService.getBlogs(query, page, size)
        logger.info("test")
        return ResponseEntity.ok(blogs)
    }
}