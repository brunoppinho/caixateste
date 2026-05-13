package tech.pinho.caixateste.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.pinho.caixateste.domain.Conta;
import tech.pinho.caixateste.repository.ContaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

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

        when(contaRepository.save(any())).thenReturn(conta);

        Conta contaSalva = contaService.salvar(conta);

        assertNotNull(contaSalva);
        assertEquals("Marcelo", contaSalva.getTitular());
        assertEquals(new BigDecimal("100.00"), contaSalva.getSaldo());
    }

    @Test
    void deveListarContasSalvas() {
        Conta conta = new Conta();
        conta.setTitular("Marcelo");
        conta.setSaldo(new BigDecimal("100.00"));
        when(contaRepository.findAll()).thenReturn(List.of(conta));

        List<Conta> contas = contaService.listar();

        assertEquals(1, contas.size());
        assertEquals("Marcelo", contas.get(0).getTitular());
    }

    @Test
    void deveBuscarContaPorId() {
        Conta conta = new Conta();
        conta.setTitular("Marcelo");
        conta.setSaldo(new BigDecimal("100.00"));
        when(contaRepository.findById(any())).thenReturn(Optional.of(conta));

        Conta contaEncontrada = contaService.buscar(0);

        assertNotNull(contaEncontrada);
        assertEquals("Marcelo", contaEncontrada.getTitular());
        assertEquals(new BigDecimal("100.00"), contaEncontrada.getSaldo());
    }

    @Test
    void test() {
        assertThrows(RuntimeException.class, () -> contaService.salvar(null));
    }

    @Test
    void test2() {
        assertNull(contaService.buscar(10));
    }

    @Test
    void deveCriarContaComSaldoZero() {
        String nome = "Marcelo";
        Conta conta = new Conta();
        conta.setTitular(nome);
        conta.setSaldo(BigDecimal.ZERO);
        when(contaRepository.save(any())).thenReturn(conta);

        Conta novaConta = contaService.abrirConta(nome);

        assertEquals(nome, novaConta.getTitular());
        assertEquals(BigDecimal.ZERO, novaConta.getSaldo());
    }

    @Test
    void deveNaoCriarContaComNometitualarMaisqueDuasLetras() {
        String nome = "Ma";

        assertThrows(RuntimeException.class, () -> contaService.abrirConta(nome));
    }

    @Test
    void deveNaoCriarContaComNomeNull() {
        String nome = null;

        assertThrows(RuntimeException.class, () -> contaService.abrirConta(nome));
    }
}