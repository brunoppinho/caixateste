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
        conta.setId(contas.size());
        contas.add(conta);
        return conta;
    }

    public Conta buscar(int id) {
        return contas.get(id);
    }

}
