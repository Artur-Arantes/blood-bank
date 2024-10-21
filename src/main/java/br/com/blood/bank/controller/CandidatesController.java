package br.com.blood.bank.controller;

import br.com.blood.bank.dto.CandidateDto;
import br.com.blood.bank.service.CandidateService;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/candidate")
public class CandidatesController {

    private final CandidateService service;

    public CandidatesController(CandidateService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addCandidate(@NonNull @RequestBody final List<CandidateDto> listCandidateDto) {
        service.add(listCandidateDto);
        return ResponseEntity.ok().build();
    }
}
