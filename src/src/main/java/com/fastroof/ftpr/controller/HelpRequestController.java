package com.fastroof.ftpr.controller;

import com.fastroof.ftpr.entity.HelpRequest;
import com.fastroof.ftpr.repository.HelpRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Response postHelpRequest(@Valid @RequestBody com.fastroof.ftpr.request.HelpRequest sentHelpRequest) {
        HelpRequest helpRequest = new HelpRequest();
        helpRequest.setEmail(sentHelpRequest.getEmail());
        helpRequest.setText(sentHelpRequest.getText());
        helpRequest.setStatus(1);
        helpRequest.setCreatedAt(LocalDate.now());

        helpRequestRepository.save(helpRequest);

        return new Response(200, "Successful");
    }
}
