package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.*;
import com.fastroof.ftpr.pojo.PatchBookRequestPojo;
import com.fastroof.ftpr.pojo.PostBookRequestPojo;
import com.fastroof.ftpr.repository.*;
import com.fastroof.ftpr.security.UserDetailsImpl;
import com.fastroof.ftpr.service.filestorage.FileStorage;
import com.fastroof.ftpr.service.filestorage.UploadFileResponse;
import com.fastroof.ftpr.service.filestorage.UploadImageResponse;
import org.jetbrains.annotations.Nullable;
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

/**
 * The BookApiController Class.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/books")
public class BookApiController {

    /** The book repository. */
    private final BookRepository bookRepository;
    
    /** The book file repository. */
    private final BookFileRepository bookFileRepository;
    
    /** The tag repository. */
    private final TagRepository tagRepository;
    
    /** The comment repository. */
    private final CommentRepository commentRepository;
    
    /** The report repository. */
    private final ReportRepository reportRepository;
    
    /** The user repository. */
    private final UserRepository userRepository;
    
    /** The personal library repository. */
    private final PersonalLibraryRepository personalLibraryRepository;
    
    /** The file storage. */
    private final FileStorage fileStorage;

    /**
     * Instantiates a new book api controller.
     *
     * @param bookRepository the book repository
     * @param bookFileRepository the book file repository
     * @param tagRepository the tag repository
     * @param commentRepository the comment repository
     * @param reportRepository the report repository
     * @param userRepository the user repository
     * @param personalLibraryRepository the personal library repository
     * @param fileStorage the file storage
     */
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

    /**
     * Gets the books.
     *
     * @param query the query (Optional)
     * @param tagId the tag id (Optional)
     * @param ownerId the owner id (Optional)
     * @return the list of books
     */
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

    /**
     * Gets the book.
     *
     * @param bookId the book id
     * @return the book
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable Integer bookId) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        return bookOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity
                .notFound()
                .build());

    }

    /**
     * Post new book.
     *
     * @param postBookRequestPojo the post book request pojo
     * @return the response entity
     */
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
        ResponseEntity<Response> coverUploadResponse = uploadCover(book, postBookRequestPojo.getCoverFile());
        if (coverUploadResponse != null) return coverUploadResponse;

        bookRepository.save(book);

        // Upload provided files
        ResponseEntity<Response> fileUploadResponse = uploadFiles(book, postBookRequestPojo.getFiles());
        if (fileUploadResponse != null) return fileUploadResponse;

        return ResponseEntity
                .ok(new Response("Book posted"));
    }

    /**
     * Helper method to upload cover.
     *
     * @param book the book
     * @param coverFile the cover file
     * @return the response entity
     */
    @Nullable
    private ResponseEntity<Response> uploadCover(Book book, MultipartFile coverFile) {
        if (coverFile != null) {
            try {
                UploadImageResponse response = fileStorage.uploadImage(coverFile);
                book.setLinkToCover(response.getData().getLink());
            } catch (RuntimeException | IOException e) {
                return ResponseEntity
                        .internalServerError()
                        .body(new Response(e.getMessage()));
            }
        }
        return null;
    }

    /**
     * Helper method to upload files.
     *
     * @param book the book
     * @param files the files
     * @return the response entity
     */
    @Nullable
    private ResponseEntity<Response> uploadFiles(Book book, List<MultipartFile> files) {
        for (MultipartFile file : files) {
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
        return null;
    }

    /**
     * Patch book.
     *
     * @param bookId the book id
     * @param patchBookRequestPojo the patch book request pojo
     * @return the response entity
     */
    @PatchMapping("/{bookId}")
    @Transactional
    @PreAuthorize("hasAuthority('user') or hasAuthority('moderator')")
    // @JsonProperty DOES NOT WORK HERE! Provide tagId and coverFile in JSON
    public ResponseEntity<Response> patchBook(@PathVariable Integer bookId, @ModelAttribute PatchBookRequestPojo patchBookRequestPojo) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);

        if (!patchBookRequestPojo.isValid()) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Patch request body empty"));
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
                    .status(403)
                    .body(new Response("User not owner of the book"));
        }

        if (patchBookRequestPojo.getName() != null) {
            book.setName(patchBookRequestPojo.getName());
        }

        if (patchBookRequestPojo.getTagId() != null) {
            book.setTagId(patchBookRequestPojo.getTagId());
        }

        // Upload new cover, if provided
        ResponseEntity<Response> coverUploadResponse = uploadCover(book, patchBookRequestPojo.getCoverFile());
        if (coverUploadResponse != null) return coverUploadResponse;

        // Upload new files
        if (patchBookRequestPojo.getFiles() != null) {
            ResponseEntity<Response> fileUploadResponse = uploadFiles(book, patchBookRequestPojo.getFiles());
            if (fileUploadResponse != null) return fileUploadResponse;
        }

        bookRepository.save(book);
        return ResponseEntity
                .ok(new Response("Book updated"));
    }

    /**
     * Delete book.
     *
     * @param bookId the book id
     * @return the response entity
     */
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
                    .status(403)
                    .body(new Response("User not owner of the book"));
        }

        bookFileRepository.deleteAll(bookFileRepository.findAllByBookId(book.getId()));
        personalLibraryRepository.deleteAll(personalLibraryRepository.getAllByBookId(book.getId()));
        bookRepository.delete(book);

        return ResponseEntity
                .ok(new Response("Book deleted"));
    }

    /**
     * Gets the comments.
     *
     * @param bookId the book id
     * @return the list of comments
     */
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

    /**
     * Gets the book files.
     *
     * @param bookId the book id
     * @return the list of book files
     */
    @GetMapping("/{bookId}/files")
    public ResponseEntity<List<BookFile>> getBookFiles(@PathVariable Integer bookId) {
        Optional<Book> book = bookRepository.findById(bookId);

        return book.map(value -> ResponseEntity
                .ok(bookFileRepository.findAllByBookId(value.getId()))).orElseGet(() -> ResponseEntity
                .notFound()
                .build());
    }

    /**
     * Delete book file.
     *
     * @param bookId the book id
     * @param fileId the file id
     * @return the response entity
     */
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
                    .status(403)
                    .body(new Response("User not owner of the book"));
        }

        BookFile bookFile = bookFileRepository.findByIdAndBookId(fileId, book.get().getId());

        if (bookFile != null) {
            bookFileRepository.delete(bookFile);
            return ResponseEntity
                    .ok(new Response("Book file deleted"));
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    /**
     * Post comment.
     *
     * @param bookId the book id
     * @param text the comment text
     * @return the response entity
     */
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

    /**
     * Post report.
     *
     * @param bookId the book id
     * @param text the report text
     * @return the response entity
     */
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

    /**
     * Adds book to the personal library.
     *
     * @param bookId the book id
     * @return the response entity
     */
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

    /**
     * Gets the user by context.
     *
     * @return the user by context
     */
    private User getUserByContext() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userDetails.getEmail());
    }
}
