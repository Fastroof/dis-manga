package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.*;
import com.fastroof.ftpr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class BookApiController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookFileRepository bookFileRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PersonalLibraryRepository personalLibraryRepository;

    @GetMapping("/books")
    public List<Book> getBooks(@RequestParam(value = "query", required = false) String query,
                               @RequestParam(value = "tagId", required = false) Integer tagId) {
        if (query != null) {
            // Search by name
            return bookRepository.getBooksByNameContains(query);
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

    @GetMapping("/books/{bookId}/comments")
    public List<Comment> getComments(@PathVariable Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            return commentRepository.findAllByBookId(bookId);
        } else {
            return new ArrayList<>();
        }
    }

    @GetMapping("/books/{bookId}/files")
    public List<BookFile> getBookFiles(@PathVariable Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            return bookFileRepository.findAllByBookId(book.get().getId());
        } else {
            return new ArrayList<>();
        }
    }

    @PostMapping("/books/{bookId}/comments")
    public Response postComment(@PathVariable Integer bookId, @RequestBody String text) {
        User user = getUserByToken();
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            Book presentBook = book.get();

            Comment newComment = new Comment();
            newComment.setCreatedAt(LocalDate.now());
            newComment.setText(text);
            newComment.setUserId(user.getId());
            newComment.setBookId(presentBook.getId());

            commentRepository.save(newComment);
            return new Response(200, "Successful");
        }
        return new Response(404, "Book not found");
    }

    @PostMapping("/books/{bookId}/report")
    public Response postReport(@PathVariable Integer bookId, @RequestBody String text) {
        User user = getUserByToken();
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            Book presentBook = book.get();

            Report newReport = new Report();
            newReport.setCreatedAt(LocalDate.now());
            newReport.setText(text);
            newReport.setUserId(user.getId());
            newReport.setStatus(1);
            newReport.setBookId(presentBook.getId());

            reportRepository.save(newReport);
            return new Response(200, "Successful");
        }
        return new Response(404, "Book not found");
    }

    @PostMapping("/books/{bookId}/personal-library")
    public Response addToPersonalLibrary(@PathVariable Integer bookId) {
        User user = getUserByToken();
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isEmpty()) {
            return new Response(404, "Book not found");
        }

        Book presentBook = book.get();

        if (personalLibraryRepository.getAllByUserId(user.getId())
                .stream()
                .noneMatch(personalLibraryEntry -> Objects.equals(personalLibraryEntry.getBookId(), presentBook.getId()))) {

            PersonalLibraryEntry newPersonalLibraryEntry = new PersonalLibraryEntry();
            newPersonalLibraryEntry.setUserId(user.getId());
            newPersonalLibraryEntry.setBookId(presentBook.getId());

            personalLibraryRepository.save(newPersonalLibraryEntry);

            return new Response(200, "Successful");
        } else {
            return new Response(406, "Book already in personal library");
        }

    }

    private User getUserByToken() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
