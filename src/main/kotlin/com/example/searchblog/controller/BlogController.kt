package com.example.demo.controller

import com.example.searchblog.service.BlogService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/search/blog")
class BlogController(private val blogService: BlogService) {
/*
    @GetMapping
    fun getBlogs(
        @RequestParam(required = true) query: String,
        @RequestParam(required = true, defaultValue = "1") page: Int,
        @RequestParam(required = true, defaultValue = "10") size: Int
    ): ResponseEntity<*> {
        val blogs = blogService.getBlogs(query, page, size)
        return ResponseEntity.ok(blogs)
    }*/
}