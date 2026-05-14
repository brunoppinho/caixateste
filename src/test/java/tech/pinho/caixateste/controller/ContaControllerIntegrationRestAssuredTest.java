package tech.pinho.caixateste.controller;

import io.restassured.RestAssured;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.pinho.caixateste.repository.ContaRepository;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContaControllerIntegrationRestAssuredTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ContaRepository contaRepository;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @Transactional
    void deveCriarConta() throws Exception {
        given()
                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                .body("""
                        {
                            "titular": "Bruno"
                        }
                        """)
                .when()
                .post("/contas")
                .then()
                .statusCode(200)
                .body("titular", equalTo("Bruno"));

        assertTrue(contaRepository.findById(1).isPresent());
        assertEquals("Bruno", contaRepository.findById(1).get().getTitular());

    }

    @Test
    void deveListarContas() throws Exception {
        given()
                .when()
                .get("/contas")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(0));

    }

}
