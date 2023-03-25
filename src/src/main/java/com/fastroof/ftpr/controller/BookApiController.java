package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.Book;
import com.fastroof.ftpr.entity.Tag;
import com.fastroof.ftpr.repository.BookRepository;
import com.fastroof.ftpr.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class BookApiController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/books")
    public List<Book> getDataSets(@RequestParam(value = "query", required = false) String query,
                                  @RequestParam(value = "tagId", required = false) Integer tagId) {
        if (query != null) {
            // Search by name
            return bookRepository.getDataSetsByNameContains(query);
        }

        if (tagId != null) {
            // Search by tag
            Optional<Tag> tagOptional = tagRepository.findById(tagId);
            if (tagOptional.isPresent()) {
                return bookRepository.findAllByTagId(tagId);
            } else {
                return new ArrayList<>();
            }
        }

        // Return all datasets
        return StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
