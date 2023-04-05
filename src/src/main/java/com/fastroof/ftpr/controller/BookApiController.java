package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.*;
import com.fastroof.ftpr.pojo.PatchBookRequestPojo;
import com.fastroof.ftpr.pojo.PostBookRequestPojo;
import com.fastroof.ftpr.repository.*;
import com.fastroof.ftpr.security.UserDetailsImpl;
import com.fastroof.ftpr.service.filestorage.FileStorage;
import com.fastroof.ftpr.service.filestorage.UploadFileResponse;
import com.fastroof.ftpr.service.filestorage.UploadImageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/books")
public class BookApiController {

    private final BookRepository bookRepository;
    private final BookFileRepository bookFileRepository;
    private final TagRepository tagRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PersonalLibraryRepository personalLibraryRepository;
    private final FileStorage fileStorage;

    @Autowired
    public BookApiController(BookRepository bookRepository,
                             BookFileRepository bookFileRepository,
                             TagRepository tagRepository,
                             CommentRepository commentRepository,
                             ReportRepository reportRepository,
                             UserRepository userRepository,
                             PersonalLibraryRepository personalLibraryRepository, FileStorage fileStorage) {
        this.bookRepository = bookRepository;
        this.bookFileRepository = bookFileRepository;
        this.tagRepository = tagRepository;
        this.commentRepository = commentRepository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.personalLibraryRepository = personalLibraryRepository;
        this.fileStorage = fileStorage;
    }

    @GetMapping("")
    public List<Book> getBooks(@RequestParam(value = "query", required = false) String query,
                               @RequestParam(value = "tag_id", required = false) Integer tagId,
                               @RequestParam(value = "owner_id", required = false) Integer ownerId) {
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

        if (ownerId != null) {
            // Search by ownerId
            return bookRepository.getBooksByOwnerId(ownerId);
        }

        // Return all books
        return StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/{bookId}")
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

    @PostMapping("")
    @Transactional
    @PreAuthorize("hasAuthority('user') or hasAuthority('moderator')")
    // @JsonProperty DOES NOT WORK HERE! Provide tagId and coverFile in JSON
    public ResponseEntity<Response> postNewBook(@Valid @ModelAttribute PostBookRequestPojo postBookRequestPojo) {
        User user = getUserByContext();
        if (postBookRequestPojo.getTagId() != null && !tagRepository.existsById(postBookRequestPojo.getTagId())) {
            return ResponseEntity
                    .internalServerError()
                    .body(new Response("Тега з id = " + postBookRequestPojo.getTagId() + " не існує"));
        }
        Book book = new Book();
        book.setCreatedAt(LocalDate.now());
        book.setUpdatedAt(LocalDate.now());
        book.setName(postBookRequestPojo.getName());
        book.setTagId(postBookRequestPojo.getTagId());
        book.setOwnerId(user.getId());

        // Upload cover if provided
        if (postBookRequestPojo.getCoverFile() != null) {
            try {
                UploadImageResponse response = fileStorage.uploadImage(postBookRequestPojo.getCoverFile());
                book.setLinkToCover(response.getData().getLink());
            } catch (RuntimeException | IOException e) {
                return ResponseEntity
                        .internalServerError()
                        .body(new Response(e.getMessage()));
            }
        }

        bookRepository.save(book);

        // Upload provided files
        for (MultipartFile file : postBookRequestPojo.getFiles()) {
            try {
                BookFile bookFile = new BookFile();
                bookFile.setBookId(book.getId());
                bookFile.setUploadedAt(LocalDate.now());

                UploadFileResponse response = fileStorage.uploadFile(file);

                bookFile.setName(response.getName());
                bookFile.setGoogleDriveId(response.getId());

                bookFileRepository.save(bookFile);
            } catch (RuntimeException | IOException e) {
                return ResponseEntity
                        .internalServerError()
                        .body(new Response(e.getMessage()));
            }
        }
        return ResponseEntity
                .ok(new Response("Book posted"));
    }

    @PatchMapping("/{bookId}")
    @Transactional
    @PreAuthorize("hasAuthority('user') or hasAuthority('moderator')")
    // @JsonProperty DOES NOT WORK HERE! Provide tagId and coverFile in JSON
    public ResponseEntity<Response> patchBook(@PathVariable Integer bookId, @ModelAttribute PatchBookRequestPojo patchBookRequestPojo) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (!patchBookRequestPojo.isValid()) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Path request body empty"));
        }

        User user = getUserByContext();
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

        // Upload new cover, if provided
        if (patchBookRequestPojo.getCoverFile() != null) {
            try {
                UploadImageResponse response = fileStorage.uploadImage(patchBookRequestPojo.getCoverFile());
                book.setLinkToCover(response.getData().getLink());
            } catch (RuntimeException | IOException e) {
                return ResponseEntity
                        .internalServerError()
                        .body(new Response(e.getMessage()));
            }
        }

        // Upload new files
        if (patchBookRequestPojo.getFiles() != null) {
            for (MultipartFile file : patchBookRequestPojo.getFiles()) {
                try {
                    BookFile bookFile = new BookFile();
                    bookFile.setBookId(book.getId());
                    bookFile.setUploadedAt(LocalDate.now());

                    UploadFileResponse response = fileStorage.uploadFile(file);

                    bookFile.setName(response.getName());
                    bookFile.setGoogleDriveId(response.getId());

                    bookFileRepository.save(bookFile);
                } catch (RuntimeException | IOException e) {
                    return ResponseEntity
                            .internalServerError()
                            .body(new Response(e.getMessage()));
                }
            }
        }

        bookRepository.save(book);
        return ResponseEntity
                .ok(new Response("Book updated"));
    }

    @DeleteMapping("/{bookId}")
    @Transactional
    @PreAuthorize("hasAuthority('user') or hasAuthority('moderator')")
    public ResponseEntity<Response> deleteBook(@PathVariable Integer bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        User user = getUserByContext();

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

    @GetMapping("/{bookId}/comments")
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

    @GetMapping("/{bookId}/files")
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

    @DeleteMapping("/{bookId}/files/{fileId}")
    @PreAuthorize("hasAuthority('user') or hasAuthority('moderator')")
    public ResponseEntity<Response> deleteBookFile(@PathVariable Integer bookId, @PathVariable Integer fileId) {
        Optional<Book> book = bookRepository.findById(bookId);
        User user = getUserByContext();

        if (book.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        if (!Objects.equals(book.get().getOwnerId(), user.getId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("User not owner of the book"));
        }

        BookFile bookFile = bookFileRepository.findByIdAndBookId(fileId, book.get().getId());

        if (bookFile != null) {
            bookFileRepository.delete(bookFile);
            return ResponseEntity
                    .ok(new Response("Book file deleted"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Book file not found"));
        }
    }

    @PostMapping("/{bookId}/comments")
    @PreAuthorize("hasAuthority('user') or hasAuthority('moderator')")
    public ResponseEntity<Response> postComment(@PathVariable Integer bookId, @RequestBody String text) {
        User user = getUserByContext();
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

    @PostMapping("/{bookId}/report")
    @PreAuthorize("hasAuthority('user') or hasAuthority('moderator')")
    public ResponseEntity<Response> postReport(@PathVariable Integer bookId, @RequestBody String text) {
        User user = getUserByContext();
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
                    .ok(new Response("Скаргу відправлено"));
        }
        return ResponseEntity
                .notFound()
                .build();
    }

    @PostMapping("/{bookId}/personal-library")
    @PreAuthorize("hasAuthority('user') or hasAuthority('moderator')")
    public ResponseEntity<Response> addToPersonalLibrary(@PathVariable Integer bookId) {
        User user = getUserByContext();
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
                    .ok(new Response("Книга додана до вашої бібліотеки"));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Дана книга вже у вашій бібліотеці"));
        }

    }

    private User getUserByContext() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userDetails.getEmail());
    }
}
