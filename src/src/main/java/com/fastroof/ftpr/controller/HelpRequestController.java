package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.HelpRequest;
import com.fastroof.ftpr.pojo.HelpRequestPojo;
import com.fastroof.ftpr.repository.HelpRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * The HelpRequestController Class.
 */
@RestController
@CrossOrigin(origins = "*")
public class HelpRequestController {

    /** The help request repository. */
    private final HelpRequestRepository helpRequestRepository;

    /**
     * Instantiates a new help request controller.
     *
     * @param helpRequestRepository the help request repository
     */
    @Autowired
    public HelpRequestController(HelpRequestRepository helpRequestRepository) {
        this.helpRequestRepository = helpRequestRepository;
    }

    /**
     * Post help request.
     *
     * @param helpRequestPojo the help request pojo
     * @return the response entity
     */
    @PostMapping("/help-request")
    public ResponseEntity<Response> postHelpRequest(@Valid HelpRequestPojo helpRequestPojo) {
        HelpRequest helpRequest = new HelpRequest();
        helpRequest.setEmail(helpRequestPojo.getEmail());
        helpRequest.setText(helpRequestPojo.getText());
        helpRequest.setStatus(1);
        helpRequest.setCreatedAt(LocalDate.now());

        helpRequestRepository.save(helpRequest);

        return ResponseEntity
                .ok(new Response("Help request sent"));
    }
}
