package tech.pinho.caixateste.service;

import org.springframework.stereotype.Service;
import tech.pinho.caixateste.domain.Conta;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContaService {

    private List<Conta> contas = new ArrayList<>();

    public List<Conta> listar() {
        return contas;
    }

    public Conta salvar(Conta conta) {
        if (conta == null) {
            throw new RuntimeException("Não podemos salvar elemento nulo");
        }
        conta.setId(contas.size());
        contas.add(conta);
        return conta;
    }

    public Conta buscar(int id) {
        if (id < 0 || id > contas.size()) {
            throw new RuntimeException("ID não encontrado");
        }
        return contas.get(id);
    }

}
