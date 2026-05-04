package tech.pinho.caixateste.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.pinho.caixateste.domain.Conta;
import tech.pinho.caixateste.service.ContaService;

import java.util.List;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @RequestMapping("")
    public List<Conta> listar() {
        return contaService.listar();
    }

    @RequestMapping("/{id}")
    public Conta listar(int id) {
        return contaService.buscar(id);
    }

    @PostMapping("")
    public Conta criar(@RequestBody Conta conta) {
        return contaService.salvar(conta);
    }
}
