package br.com.blood.bank;

import br.com.blood.bank.dto.LoginInPutDto;
import br.com.blood.bank.dto.TokenDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.glassfish.jaxb.core.v2.TODO;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

    private static BloodBankDataBaseContainer bloodBankDataBaseContainer;
    @LocalServerPort
    protected int port;
    protected String urlBase;

    @BeforeAll
    public static void startContainer() {
        bloodBankDataBaseContainer = BloodBankDataBaseContainer.getInstance();
        bloodBankDataBaseContainer.start();

    }

    public String getToken(String username, String password) {
        this.urlBase = "http://localhost:" + port;
        LoginInPutDto tokenDto = new LoginInPutDto("fiap","fiap");
        String token = RestAssured.given().contentType(ContentType.JSON)
                .body(tokenDto)
                .when()
                .post(this.urlBase+"/auth")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract().jsonPath()
                .getObject("token", String.class);
        return token;
    }
}
