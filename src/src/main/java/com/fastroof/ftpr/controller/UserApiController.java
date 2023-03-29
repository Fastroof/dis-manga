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

@RestController
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('user') or hasAuthority('moderator')")
@RequestMapping("/user")
public class UserApiController {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PersonalLibraryRepository personalLibraryRepository;

    @Autowired
    public UserApiController(UserRepository userRepository,
                             BookRepository bookRepository,
                             PersonalLibraryRepository personalLibraryRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.personalLibraryRepository = personalLibraryRepository;
    }

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

    private User getUserByContext() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByEmail(userDetails.getEmail());
    }

}
