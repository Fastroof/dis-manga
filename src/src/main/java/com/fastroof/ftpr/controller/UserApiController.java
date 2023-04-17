package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.Book;
import com.fastroof.ftpr.entity.PersonalLibraryEntry;
import com.fastroof.ftpr.entity.User;
import com.fastroof.ftpr.repository.*;
import com.fastroof.ftpr.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: Auto-generated Javadoc
/**
 * The Class UserApiController.
 */
@RestController
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('user') or hasAuthority('moderator')")
@RequestMapping("/user")
public class UserApiController {

    /** The user repository. */
    private final UserRepository userRepository;
    
    /** The book repository. */
    private final BookRepository bookRepository;
    
    /** The personal library repository. */
    private final PersonalLibraryRepository personalLibraryRepository;

    /**
     * Instantiates a new user api controller.
     *
     * @param userRepository the user repository
     * @param bookRepository the book repository
     * @param personalLibraryRepository the personal library repository
     */
    @Autowired
    public UserApiController(UserRepository userRepository,
                             BookRepository bookRepository,
                             PersonalLibraryRepository personalLibraryRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.personalLibraryRepository = personalLibraryRepository;
    }

    /**
     * Gets the personal library.
     *
     * @return the personal library
     */
    @GetMapping("/personal-library")
    public List<Book> getPersonalLibrary() {
        User user = getUserByContext();

        List<PersonalLibraryEntry> personalLibraryEntries = personalLibraryRepository.getAllByUserId(user.getId());

        List<Book> result = new ArrayList<>();

        personalLibraryEntries.forEach(personalLibraryEntry -> {
            Optional<Book> book = bookRepository.findById(personalLibraryEntry.getBookId());
            book.ifPresent(result::add);
        });

        return result;
    }

    /**
     * Delete book from personal library.
     *
     * @param bookId the book id
     * @return the response entity
     */
    @DeleteMapping("/personal-library/{bookId}")
    public ResponseEntity<Response> deleteBookFromPersonalLibrary(@PathVariable Integer bookId) {
        User user = getUserByContext();

        PersonalLibraryEntry entry = personalLibraryRepository.getByUserIdAndBookId(user.getId(), bookId);

        if (entry != null) {
            personalLibraryRepository.delete(entry);
            return ResponseEntity
                    .ok(new Response("Book removed from personal library"));
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
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
