package tech.pinho.caixateste.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @RequestMapping("")
    public String listar() {
        return "Listando contas";
    }
}
