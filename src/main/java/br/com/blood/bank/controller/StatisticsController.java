package br.com.blood.bank.controller;

import br.com.blood.bank.dto.ImcByAgeOutPutDto;
import br.com.blood.bank.dto.ObesityPercentageOutPutDto;
import br.com.blood.bank.enums.EnumBloodType;
import br.com.blood.bank.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/api/statistics")
public class StatisticsController {

    private final StatisticsService service;

    public StatisticsController(StatisticsService service) {
        this.service = service;
    }

    @GetMapping("/state")
    public ResponseEntity<Map<String, Long>> getCandidatesByState() {
        Map<String, Long> candidatesByState = service.getCandidatesByState();
        return ResponseEntity.ok(candidatesByState);
    }

    @GetMapping("/age")
    public ResponseEntity< List<ImcByAgeOutPutDto>> getAverageImcByAge() {
        List<ImcByAgeOutPutDto> averageBmiByAge = service.getAverageImcByAge();
        return ResponseEntity.ok(averageBmiByAge);
    }

    @GetMapping("/obesity")
    public ResponseEntity<ObesityPercentageOutPutDto> getObesityPercentage() {
        ObesityPercentageOutPutDto obesityPercentage = service.getObesityPercentage();
        return ResponseEntity.ok(obesityPercentage);
    }

    @GetMapping("/blood-type")
    public ResponseEntity<Map<EnumBloodType, BigDecimal>> getAverageAgeByBloodType() {
        Map<EnumBloodType, BigDecimal> averageAgeByBloodType = service.getAverageAgeByBloodType();
        return ResponseEntity.ok(averageAgeByBloodType);
    }

    @GetMapping("/donors")
    public ResponseEntity<Map<EnumBloodType, Long>> getPotentialDonors() {
        Map<EnumBloodType, Long> potentialDonors = service.getPotentialDonors();
        return ResponseEntity.ok(potentialDonors);
    }
}


