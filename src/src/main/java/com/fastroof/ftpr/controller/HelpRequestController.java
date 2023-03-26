package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.HelpRequest;
import com.fastroof.ftpr.pojo.HelpRequestPojo;
import com.fastroof.ftpr.repository.HelpRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
public class HelpRequestController {
    @Autowired
    HelpRequestRepository helpRequestRepository;

    @PostMapping("/help-request")
    public ResponseEntity<Response> postHelpRequest(@Valid @RequestBody HelpRequestPojo sentHelpRequestPojo) {
        HelpRequest helpRequest = new HelpRequest();
        helpRequest.setEmail(sentHelpRequestPojo.getEmail());
        helpRequest.setText(sentHelpRequestPojo.getText());
        helpRequest.setStatus(1);
        helpRequest.setCreatedAt(LocalDate.now());

        helpRequestRepository.save(helpRequest);

        return ResponseEntity
                .ok(new Response("Help request sent"));
    }
}
