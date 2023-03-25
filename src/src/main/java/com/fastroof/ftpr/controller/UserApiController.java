package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.*;
import com.fastroof.ftpr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
public class UserApiController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookFileRepository bookFileRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AddBookFileRequestRepository addBookFileRequestRepository;
    @Autowired
    private EditBookFileRequestRepository editBookFileRequestRepository;

    @RequestMapping("/user/createEditBookFileRequest")
    public String createEditDataFileRequest(@RequestParam("id") Integer dataFileId,
                                            @RequestParam("link") String linkToFile,
                                            @RequestParam("name") String name) {
        if (!tokenHasUserRole()) {
            return null;
        }
        User user = getUserByToken();
        Optional<BookFile> dataFileOptional = bookFileRepository.findById(dataFileId);
        if (dataFileOptional.isPresent()) {
            BookFile bookFile = dataFileOptional.get();
            EditBookFileRequest editBookFileRequest = new EditBookFileRequest();
            editBookFileRequest.setStatus(1);
            editBookFileRequest.setUserId(user.getId());
            editBookFileRequest.setBookFileId(dataFileId);
            editBookFileRequest.setUpdatedAt(LocalDate.now());
            editBookFileRequest.setLinkToFile(linkToFile);
            editBookFileRequest.setName(name);

            editBookFileRequestRepository.save(editBookFileRequest);
            return "Edit data file request created. Id: " + editBookFileRequest.getId();
        } else {
            return "Data file with this id does not exist.";
        }
    }

    @RequestMapping("/user/deleteBookFile")
    public String deleteDataFile(@RequestParam("id") Integer dataFileId){
        User user = getUserByToken();
        Optional<BookFile> dataFileOptional = bookFileRepository.findById(dataFileId);
        if (dataFileOptional.isPresent()){
            BookFile bookFile = dataFileOptional.get();
            if (bookFile.getOwnerId().equals(user.getId())){
                bookFileRepository.deleteById(dataFileId);
                return "Data file deleted.";
            } else {
                return "Only owner can delete this data file.";
            }
        } else {
            return "Data file with this id does not exist.";
        }
    }

    @RequestMapping("/user/deleteBook")
    public String deleteDataSet(@RequestParam("id") Integer dataSetId){
        User user = getUserByToken();
        Optional<Book> dataSetOptional = bookRepository.findById(dataSetId);
        if (dataSetOptional.isPresent()){
            Book book = dataSetOptional.get();
            if (book.getOwnerId().equals(user.getId())){
                List<BookFile> dataSetFiles = bookFileRepository.findAllByDataSetId(dataSetId);
                bookFileRepository.deleteAll(dataSetFiles);
                bookRepository.deleteById(dataSetId);
                return "Data set deleted.";
            } else {
                return "Only owner can delete this data set.";
            }
        } else {
            return "Data set with this id does not exist.";
        }
    }

    private User getUserByToken(){
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    private boolean tokenHasUserRole(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString().equals("ROLE_USER");
    }

}
