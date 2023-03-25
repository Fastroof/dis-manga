package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.*;
import com.fastroof.ftpr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ModeratorApiController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HelpRequestRepository helpRequestRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private AddBookFileRequestRepository addBookFileRequestRepository;
    @Autowired
    private EditBookFileRequestRepository editBookFileRequestRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookFileRepository bookFileRepository;

    @GetMapping("/moderator/help-requests")
    public List<HelpRequest> getHelpRequests() {
        if (!tokenHasModeratorRole()) {
            return Collections.emptyList();
        }

        return StreamSupport
                .stream(helpRequestRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping("/moderator/help-requests/{id}/process")
    public Response processHelpRequest(@PathVariable Integer id) {
        if (!tokenHasModeratorRole()) {
            return new Response(403, "User not moderator");
        }

        Optional<HelpRequest> helpRequestOptional = helpRequestRepository.findById(id);

        if (helpRequestOptional.isEmpty()) {
            return new Response(404, "Help request not found");
        }

        HelpRequest helpRequest = helpRequestOptional.get();

        if (helpRequest.getStatus() != 1) {
            return new Response(406, "Help request already processed");
        }

        helpRequest.setModeratorId(getUserByToken().getId());
        helpRequest.setStatus(2);
        helpRequestRepository.save(helpRequest);
        return new Response(200, "Help request processed");
    }

    @GetMapping("/moderator/reports")
    public List<Report> getReports() {
        if (!tokenHasModeratorRole()) {
            return Collections.emptyList();
        }

        return StreamSupport
                .stream(reportRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @PostMapping("/moderator/reports/{id}/process")
    public Response processReport(@PathVariable Integer id) {
        if (!tokenHasModeratorRole()) {
            return new Response(403, "User not moderator");
        }

        Optional<Report> reportOptional = reportRepository.findById(id);

        if (reportOptional.isEmpty()) {
            return new Response(404, "Report not found");
        }

        Report report = reportOptional.get();

        if (report.getStatus() != 1) {
            return new Response(406, "Report already processed");
        }

        report.setStatus(2);
        report.setModeratorId(getUserByToken().getId());
        reportRepository.save(report);
        return new Response(200, "Report processed");
    }

    @RequestMapping("/moderator/unprocessedAddBookFileRequests")
    public List<AddBookFileRequest> getUnprocessedAddBookFileRequests() {
        if (tokenHasModeratorRole()){
            return addBookFileRequestRepository.findAllByStatus(1);
        } else {
            return null;
        }
    }

    @RequestMapping("/moderator/approveAddBookFileRequest")
    public String approveAddBookFileRequest(@RequestParam("id") String requestId){
        if (!tokenHasModeratorRole()) {
            return null;
        }
        User moderator = getUserByToken();
        Optional<AddBookFileRequest> addDataFileRequestOptional = addBookFileRequestRepository.findById(Integer.valueOf(requestId));
        if (addDataFileRequestOptional.isPresent()) {
            AddBookFileRequest addBookFileRequest = addDataFileRequestOptional.get();
            addBookFileRequest.setStatus(2);
            addBookFileRequest.setModeratorId(moderator.getId());

            //Maybe change link_to_file to link_to_files in DB and process it as a list of links
            BookFile bookFile = new BookFile();
            bookFile.setName(addBookFileRequest.getName());
            bookFile.setCreatedAt(LocalDate.now());
            bookFile.setUpdatedAt(LocalDate.now());
            bookFile.setLinkToFile(addBookFileRequest.getLinkToFile());
            bookFile.setOwnerId(addBookFileRequest.getUserId());
            bookFile.setBookId(addBookFileRequest.getBookId());

            Book book = bookRepository.findById(addBookFileRequest.getBookId()).get();
            book.setUpdatedAt(LocalDate.now());

            bookFileRepository.save(bookFile);
            addBookFileRequestRepository.save(addBookFileRequest);
            bookRepository.save(book);
            return "Add data file request approved.";
        } else {
            return "Add data file request with this id does not exist.";
        }
    }

    @RequestMapping("/moderator/declineAddBookFileRequest")
    public String declineAddBookFileRequest(@RequestParam("id") String requestId){
        if (!tokenHasModeratorRole()) {
            return null;
        }
        User moderator = getUserByToken();
        Optional<AddBookFileRequest> addDataFileRequestOptional = addBookFileRequestRepository.findById(Integer.valueOf(requestId));
        if (addDataFileRequestOptional.isPresent()) {
            AddBookFileRequest addBookFileRequest = addDataFileRequestOptional.get();
            addBookFileRequest.setModeratorId(moderator.getId());
            addBookFileRequest.setStatus(3);
            addBookFileRequestRepository.save(addBookFileRequest);
            return "Add data file request declined.";
        } else {
            return "Add data file request with this id does not exist.";
        }
    }


    @RequestMapping("/moderator/unprocessedEditBookFileRequests")
    public List<EditBookFileRequest> getUnprocessedEditBookFileRequests() {
        if (tokenHasModeratorRole()){
            return editBookFileRequestRepository.findAllByStatus(1);
        } else {
            return null;
        }
    }

    @RequestMapping("/moderator/approveEditBookFileRequest")
    public String approveEditBookFileRequest(@RequestParam("id") String requestId){
        if (!tokenHasModeratorRole()) {
            return null;
        }
        User moderator = getUserByToken();
        Optional<EditBookFileRequest> editDataFileRequestOptional = editBookFileRequestRepository.findById(Integer.valueOf(requestId));
        if (editDataFileRequestOptional.isPresent()) {
            EditBookFileRequest editBookFileRequest = editDataFileRequestOptional.get();
            editBookFileRequest.setStatus(2);
            editBookFileRequest.setModeratorId(moderator.getId());

            BookFile bookFile = bookFileRepository.findById(editBookFileRequest.getBookFileId()).get();
            bookFile.setUpdatedAt(LocalDate.now());
            bookFile.setLinkToFile(editBookFileRequest.getLinkToFile());
            bookFile.setName(editBookFileRequest.getName());

            Book book = bookRepository.findById(bookFile.getBookId()).get();
            book.setUpdatedAt(LocalDate.now());

            editBookFileRequestRepository.save(editBookFileRequest);
            bookFileRepository.save(bookFile);
            bookRepository.save(book);
            return "Edit data file request approved.";
        } else {
            return "Edit data file request with this id does not exist.";
        }
    }

    @RequestMapping("/moderator/declineEditBookFileRequest")
    public String declineEditBookFileRequest(@RequestParam("id") String requestId){
        if (!tokenHasModeratorRole()) {
            return null;
        }
        User moderator = getUserByToken();
        Optional<EditBookFileRequest> editDataFileRequestOptional = editBookFileRequestRepository.findById(Integer.valueOf(requestId));
        if (editDataFileRequestOptional.isPresent()) {
            EditBookFileRequest editBookFileRequest = editDataFileRequestOptional.get();
            editBookFileRequest.setModeratorId(moderator.getId());
            editBookFileRequest.setStatus(3);
            editBookFileRequestRepository.save(editBookFileRequest);
            return "Edit data file request declined.";
        } else {
            return "Edit data file request with this id does not exist.";
        }
    }

    private User getUserByToken(){
        return userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    private boolean tokenHasModeratorRole(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString().equals("ROLE_MODERATOR");
    }

}
