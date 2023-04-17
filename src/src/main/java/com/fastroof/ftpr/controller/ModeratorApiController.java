package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.HelpRequest;
import com.fastroof.ftpr.entity.Report;
import com.fastroof.ftpr.entity.User;
import com.fastroof.ftpr.repository.HelpRequestRepository;
import com.fastroof.ftpr.repository.ReportRepository;
import com.fastroof.ftpr.repository.UserRepository;
import com.fastroof.ftpr.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

// TODO: Auto-generated Javadoc
/**
 * The Class ModeratorApiController.
 */
@RestController
@CrossOrigin(origins = "*")
@PreAuthorize("hasAuthority('moderator')")
@RequestMapping("/moderator")
public class ModeratorApiController {

    /** The user repository. */
    private final UserRepository userRepository;
    
    /** The help request repository. */
    private final HelpRequestRepository helpRequestRepository;
    
    /** The report repository. */
    private final ReportRepository reportRepository;

    /**
     * Instantiates a new moderator api controller.
     *
     * @param userRepository the user repository
     * @param helpRequestRepository the help request repository
     * @param reportRepository the report repository
     */
    @Autowired
    public ModeratorApiController(UserRepository userRepository,
                                  HelpRequestRepository helpRequestRepository,
                                  ReportRepository reportRepository) {
        this.userRepository = userRepository;
        this.helpRequestRepository = helpRequestRepository;
        this.reportRepository = reportRepository;
    }

    /**
     * Gets the help requests.
     *
     * @return the help requests
     */
    @GetMapping("/help-requests")
    public List<HelpRequest> getHelpRequests() {
        return StreamSupport
                .stream(helpRequestRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Process help request.
     *
     * @param id the id
     * @return the response entity
     */
    @PostMapping("/help-requests/{id}/process")
    public ResponseEntity<Response> processHelpRequest(@PathVariable Integer id) {
        Optional<HelpRequest> helpRequestOptional = helpRequestRepository.findById(id);

        if (helpRequestOptional.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        HelpRequest helpRequest = helpRequestOptional.get();

        if (helpRequest.getStatus() != 1) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Help request already processed"));
        }

        helpRequest.setModeratorId(getUserByContext().getId());
        helpRequest.setStatus(2);
        helpRequestRepository.save(helpRequest);
        return ResponseEntity
                .ok(new Response("Help request processed"));
    }

    /**
     * Gets the reports.
     *
     * @return the reports
     */
    @GetMapping("/reports")
    public List<Report> getReports() {
        return StreamSupport
                .stream(reportRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * Process report.
     *
     * @param id the id
     * @return the response entity
     */
    @PostMapping("/reports/{id}/process")
    public ResponseEntity<Response> processReport(@PathVariable Integer id) {
        Optional<Report> reportOptional = reportRepository.findById(id);

        if (reportOptional.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }

        Report report = reportOptional.get();

        if (report.getStatus() != 1) {
            return ResponseEntity
                    .badRequest()
                    .body(new Response("Report already processed"));
        }

        report.setStatus(2);
        report.setModeratorId(getUserByContext().getId());
        reportRepository.save(report);
        return ResponseEntity
                .ok(new Response("Report processed"));
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
