package br.com.blood.bank.controller;

import br.com.blood.bank.BaseIntegrationTest;
import br.com.blood.bank.BloodBankDataBaseContainer;
import br.com.blood.bank.dto.ImcByAgeOutPutDto;
import br.com.blood.bank.dto.ObesityPercentageOutPutDto;
import br.com.blood.bank.repository.UserRepository;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StatisticsControllerIntegrationTest extends BaseIntegrationTest {

    private String token;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void startContainer() {
        BloodBankDataBaseContainer.getInstance().start();
    }

    @BeforeEach
    public void setUp() {
        insertTestData();

        token = getToken("fiap", "fiap");

    }

    private void insertTestData() {

    }

    @Test
    public void testGetCandidatesByState() {
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get(urlBase+"/v1/api/statistics/state")
                .then()
                .statusCode(200)
                .extract().jsonPath()
                .getObject("", HashMap.class);
    }

    @Test
    public void testGetAverageImcByAge() {
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get(urlBase+"/v1/api/statistics/age")
                .then()
                .statusCode(200)
                .extract().jsonPath()
                .getList("", ImcByAgeOutPutDto.class);
    }

    @Test
    public void testGetObesityPercentage() {
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get(urlBase+"/v1/api/statistics/obesity")
                .then()
                .extract().jsonPath()
                .getObject("", ObesityPercentageOutPutDto.class);
    }


    @Test
    public void testBloodType() {
        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .when()
                .get(urlBase+"/v1/api/statistics/obesity")
                .then()
                .extract().jsonPath()
                .getObject("", ObesityPercentageOutPutDto.class);
    }

}
