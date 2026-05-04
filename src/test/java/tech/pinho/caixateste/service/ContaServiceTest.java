package tech.pinho.caixateste.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.pinho.caixateste.domain.Conta;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContaServiceTest {

    private ContaService contaService;

    @BeforeEach
    void setUp() {
        contaService = new ContaService();
    }

    @Test
    void deveListarContasVaziasQuandoNenhumaContaFoiSalva() {
        List<Conta> contas = contaService.listar();
        assertTrue(contas.isEmpty());
    }

    @Test
    void deveSalvarConta() {
        Conta conta = new Conta();
        conta.setTitular("Marcelo");
        conta.setSaldo(new BigDecimal("100.00"));

        Conta contaSalva = contaService.salvar(conta);

        assertNotNull(contaSalva);
        assertEquals(0, contaSalva.getId());
        assertEquals("Marcelo", contaSalva.getTitular());
        assertEquals(new BigDecimal("100.00"), contaSalva.getSaldo());
    }

    @Test
    void deveListarContasSalvas() {
        Conta conta = new Conta();
        conta.setTitular("Marcelo");
        conta.setSaldo(new BigDecimal("100.00"));
        contaService.salvar(conta);

        List<Conta> contas = contaService.listar();

        assertEquals(1, contas.size());
        assertEquals("Marcelo", contas.get(0).getTitular());
    }

    @Test
    void deveBuscarContaPorId() {
        Conta conta = new Conta();
        conta.setTitular("Marcelo");
        conta.setSaldo(new BigDecimal("100.00"));
        contaService.salvar(conta);

        Conta contaEncontrada = contaService.buscar(0);

        assertNotNull(contaEncontrada);
        assertEquals(0, contaEncontrada.getId());
        assertEquals("Marcelo", contaEncontrada.getTitular());
        assertEquals(new BigDecimal("100.00"), contaEncontrada.getSaldo());
    }

    @Test
    void test() {
        assertThrows(RuntimeException.class, () -> contaService.salvar(null));
    }

    @Test
    void test2() {
        assertThrows(RuntimeException.class, () -> contaService.buscar(10));
    }

    @Test
    void test3() {
        assertThrows(RuntimeException.class, () -> contaService.buscar(-1));
    }
}