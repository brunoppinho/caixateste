package tech.pinho.caixateste.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.pinho.caixateste.domain.Conta;
import tech.pinho.caixateste.service.ContaService;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaControllerTest {

    @Mock
    private ContaService contaService;

    @InjectMocks
    private ContaController contaController;

    @Test
    void test() {
        when(contaService.listar()).thenReturn(Collections.emptyList());

        List<Conta> contas = contaController.listar();

        assertTrue(contas.isEmpty());
        verify(contaService, times(1)).listar();
        verify(contaService, times(0)).salvar(any());

    }
}