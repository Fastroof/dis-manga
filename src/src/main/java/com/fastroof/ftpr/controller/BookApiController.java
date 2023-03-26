package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.*;
import com.fastroof.ftpr.pojo.PatchBookRequestPojo;
import com.fastroof.ftpr.pojo.PostBookRequestPojo;
import com.fastroof.ftpr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin(origins = "*")
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

    @GetMapping("/books/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Integer bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity
                .ok(bookOptional.get());
    }

    @PostMapping("/books")
    @Transactional
    public ResponseEntity<Response> postNewBook(@Valid @ModelAttribute PostBookRequestPojo postBookRequestPojo) {
        User user = getUserByToken();
        Book book = new Book();
        book.setCreatedAt(LocalDate.now());
        book.setUpdatedAt(LocalDate.now());
        book.setName(postBookRequestPojo.getName());
        book.setTagId(postBookRequestPojo.getTagId());
        book.setOwnerId(user.getId());

        // TODO: upload cover if provided

        bookRepository.save(book);

        // TODO: upload provided book files

        return ResponseEntity
                .ok(new Response("Book posted"));
    }

    @PatchMapping("/books/{bookId}")
    @Transactional
    public ResponseEntity<Response> patchBook(@PathVariable Integer bookId, @ModelAttribute PatchBookRequestPojo patchBookRequestPojo) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        User user = getUserByToken();

        if (bookOptional.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        Book book = bookOptional.get();

        if (!Objects.equals(book.getOwnerId(), user.getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("User not owner of the book"));
        }

        if (patchBookRequestPojo.getName() != null) {
            book.setName(patchBookRequestPojo.getName());
        }

        if (patchBookRequestPojo.getTagId() != null) {
            book.setTagId(patchBookRequestPojo.getTagId());
        }

        if (patchBookRequestPojo.getCoverFile() != null) {
            // TODO: upload cover file
            // book.setLinkToCover(...);
        }

        if (patchBookRequestPojo.getFiles() != null) {
            // TODO: upload provided files
            // ...
            // bookFileRepository.saveAll(newFiles)
        }

        bookRepository.save(book);
        return ResponseEntity
                .ok(new Response("Book updated"));
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<Response> deleteBook(@PathVariable Integer bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        User user = getUserByToken();

        if (bookOptional.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        Book book = bookOptional.get();

        if (!Objects.equals(book.getOwnerId(), user.getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("User not owner of the book"));
        }

        bookFileRepository.deleteAll(bookFileRepository.findAllByBookId(book.getId()));
        bookRepository.delete(book);

        return ResponseEntity
                .ok(new Response("Book deleted"));
    }

    @GetMapping("/books/{bookId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            return ResponseEntity
                    .ok(commentRepository.findAllByBookId(bookId));
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @GetMapping("/books/{bookId}/files")
    public ResponseEntity<List<BookFile>> getBookFiles(@PathVariable Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isPresent()) {
            return ResponseEntity
                    .ok(bookFileRepository.findAllByBookId(book.get().getId()));
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PostMapping("/books/{bookId}/comments")
    public ResponseEntity<Response> postComment(@PathVariable Integer bookId, @RequestBody String text) {
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
            return ResponseEntity
                    .ok(new Response("Comment posted"));
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @PostMapping("/books/{bookId}/report")
    public ResponseEntity<Response> postReport(@PathVariable Integer bookId, @RequestBody String text) {
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
            return ResponseEntity
                    .ok(new Response("Report sent"));
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @PostMapping("/books/{bookId}/personal-library")
    public ResponseEntity<Response> addToPersonalLibrary(@PathVariable Integer bookId) {
        User user = getUserByToken();
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        Book presentBook = book.get();

        if (personalLibraryRepository.getAllByUserId(user.getId())
                .stream()
                .noneMatch(personalLibraryEntry -> Objects.equals(personalLibraryEntry.getBookId(), presentBook.getId()))) {

            PersonalLibraryEntry newPersonalLibraryEntry = new PersonalLibraryEntry();
            newPersonalLibraryEntry.setUserId(user.getId());
            newPersonalLibraryEntry.setBookId(presentBook.getId());

            personalLibraryRepository.save(newPersonalLibraryEntry);

            return ResponseEntity
                    .ok(new Response("Book added to personal library"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Book already in personal library"));
        }

    }

    private User getUserByToken() {
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
