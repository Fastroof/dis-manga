package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.Book;
import com.fastroof.ftpr.entity.PersonalLibraryEntry;
import com.fastroof.ftpr.entity.User;
import com.fastroof.ftpr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class UserApiController {

    private final UserRepository userRepository;
    private final BookFileRepository bookFileRepository;
    private final BookRepository bookRepository;
    private final PersonalLibraryRepository personalLibraryRepository;

    @Autowired
    public UserApiController(UserRepository userRepository,
                             BookFileRepository bookFileRepository,
                             BookRepository bookRepository,
                             PersonalLibraryRepository personalLibraryRepository) {
        this.userRepository = userRepository;
        this.bookFileRepository = bookFileRepository;
        this.bookRepository = bookRepository;
        this.personalLibraryRepository = personalLibraryRepository;
    }

    @GetMapping("/user/personal-library")
    public List<Book> getPersonalLibrary() {
        User user = getUserByToken();

        List<PersonalLibraryEntry> personalLibraryEntries = personalLibraryRepository.getAllByUserId(user.getId());

        List<Book> result = new ArrayList<>();

        personalLibraryEntries.forEach(personalLibraryEntry -> {
            Optional<Book> book = bookRepository.findById(personalLibraryEntry.getBookId());
            book.ifPresent(result::add);
        });

        return result;
    }

    @DeleteMapping("/user/personal-library/{bookId}")
    public ResponseEntity<Response> deleteBookFromPersonalLibrary(@PathVariable Integer bookId) {
        User user = getUserByToken();

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


//    @RequestMapping("/user/createEditBookFileRequest")
//    public String createEditDataFileRequest(@RequestParam("id") Integer dataFileId,
//                                            @RequestParam("link") String linkToFile,
//                                            @RequestParam("name") String name) {
//        if (!tokenHasUserRole()) {
//            return null;
//        }
//        User user = getUserByToken();
//        Optional<BookFile> dataFileOptional = bookFileRepository.findById(dataFileId);
//        if (dataFileOptional.isPresent()) {
//            EditBookFileRequest editBookFileRequest = new EditBookFileRequest();
//            editBookFileRequest.setStatus(1);
//            editBookFileRequest.setUserId(user.getId());
//            editBookFileRequest.setBookFileId(dataFileId);
//            editBookFileRequest.setUpdatedAt(LocalDate.now());
//            editBookFileRequest.setLinkToFile(linkToFile);
//            editBookFileRequest.setName(name);
//
//            editBookFileRequestRepository.save(editBookFileRequest);
//            return "Edit data file request created. Id: " + editBookFileRequest.getId();
//        } else {
//            return "Data file with this id does not exist.";
//        }
//    }
//
//    @RequestMapping("/user/deleteBookFile")
//    public String deleteDataFile(@RequestParam("id") Integer dataFileId) {
//        User user = getUserByToken();
//        Optional<BookFile> dataFileOptional = bookFileRepository.findById(dataFileId);
//        if (dataFileOptional.isPresent()) {
//            BookFile bookFile = dataFileOptional.get();
//            if (bookFile.getOwnerId().equals(user.getId())) {
//                bookFileRepository.deleteById(dataFileId);
//                return "Data file deleted.";
//            } else {
//                return "Only owner can delete this data file.";
//            }
//        } else {
//            return "Data file with this id does not exist.";
//        }
//    }
//
//    @RequestMapping("/user/deleteBook")
//    public String deleteBook(@RequestParam("id") Integer dataSetId) {
//        User user = getUserByToken();
//        Optional<Book> dataSetOptional = bookRepository.findById(dataSetId);
//        if (dataSetOptional.isPresent()) {
//            Book book = dataSetOptional.get();
//            if (book.getOwnerId().equals(user.getId())) {
//                List<BookFile> dataSetFiles = bookFileRepository.findAllByBookId(dataSetId);
//                bookFileRepository.deleteAll(dataSetFiles);
//                bookRepository.deleteById(dataSetId);
//                return "Data set deleted.";
//            } else {
//                return "Only owner can delete this data set.";
//            }
//        } else {
//            return "Data set with this id does not exist.";
//        }
//    }

    private User getUserByToken() {
        return userRepository.findById(8).orElse(null);
        //return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    private boolean tokenHasUserRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString().equals("ROLE_USER");
    }

}
