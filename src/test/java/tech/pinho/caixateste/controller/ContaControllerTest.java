package tech.pinho.caixateste.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.pinho.caixateste.domain.Conta;
import tech.pinho.caixateste.service.ContaService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ContaControllerTest {

    @Mock
    private ContaService contaService;

    @InjectMocks
    private ContaController contaController;

    private MockMvc mvc;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(contaController).build();
    }

    @Test
    void test() {
        when(contaService.listar()).thenReturn(Collections.emptyList());

        List<Conta> contas = contaController.listar();

        assertTrue(contas.isEmpty());
        verify(contaService, times(1)).listar();
        verify(contaService, times(0)).salvar(any());
    }

    @Test
    void test_mock_mvc() throws Exception {
        when(contaService.listar()).thenReturn(Collections.emptyList());

        String contas = mvc.perform(
                        get("/contas")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals("[]", contas);
        verify(contaService, times(1)).listar();
        verify(contaService, times(0)).salvar(any());
    }
}