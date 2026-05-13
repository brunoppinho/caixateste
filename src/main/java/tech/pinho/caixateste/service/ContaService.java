package tech.pinho.caixateste.service;

import org.springframework.stereotype.Service;
import tech.pinho.caixateste.domain.Conta;
import tech.pinho.caixateste.repository.ContaRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public List<Conta> listar() {
        return contaRepository.findAll();
    }

    public Conta salvar(Conta conta) {
        if (conta == null) {
            throw new RuntimeException("Não podemos salvar elemento nulo");
        }
        return contaRepository.save(conta);
    }

    public Conta buscar(int id) {
        return contaRepository.findById(id)
                .orElse(null);
    }

    public Conta abrirConta(String nome) {
        if (nome == null || nome.length() < 3) {
            throw new RuntimeException("Nome inválido");
        }
        Conta conta = new Conta();
        conta.setTitular(nome);
        conta.setSaldo(BigDecimal.ZERO);

        return salvar(conta);
    }
}
