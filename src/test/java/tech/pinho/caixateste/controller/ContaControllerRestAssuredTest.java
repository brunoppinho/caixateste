package tech.pinho.caixateste.controller;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.pinho.caixateste.domain.Conta;
import tech.pinho.caixateste.service.ContaService;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

class ContaControllerRestAssuredTest {

    private ContaService contaService;
    private ContaController contaController;

    @BeforeEach
    public void setUp() {
        contaService = mock(ContaService.class);
        contaController = new ContaController(contaService);
        RestAssuredMockMvc.standaloneSetup(contaController);
    }

    @Test
    void test() {
        when(contaService.listar()).thenReturn(Collections.emptyList());

        given()
                .when()
                .get("/contas")
                .then()
                .statusCode(200)
                .body("$.size()", equalTo(0));

        verify(contaService, times(1)).listar();
        verify(contaService, times(0)).salvar(any());
    }

    @Test
    void test_mock_mvc() throws Exception {
        when(contaService.listar()).thenReturn(Collections.emptyList());

//        mvc.perform(
//                        get("/contas")
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(0));
//
//        verify(contaService, times(1)).listar();
//        verify(contaService, times(0)).salvar(any());
    }

    @Test
    void test_mock_mvc2() throws Exception {
        Conta conta = new Conta();
        conta.setId(1);
        conta.setTitular("Marcelo");
        conta.setSaldo(BigDecimal.TEN);
        when(contaService.listar()).thenReturn(List.of(conta));
//
//        mvc.perform(
//                        get("/contas")
//                                .contentType(MediaType.APPLICATION_JSON)
//                )
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].titular").value("Marcelo"))
//                .andExpect(jsonPath("$[0].saldo").value(10.0));

        verify(contaService, times(1)).listar();
        verify(contaService, times(0)).salvar(any());
    }
}