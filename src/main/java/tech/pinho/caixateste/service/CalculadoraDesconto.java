package tech.pinho.caixateste.service;

import org.springframework.stereotype.Service;

@Service
public class CalculadoraDesconto {

    private static final double TAXA_DESCONTO = 0.1;
    private static final double MINIMO_COMPRA = 100;

    public double calcular(double valor) {
        if (deveAplicarDesconto(valor)) {
            return aplicaDesconto(valor);
        }
        return valor;
    }

    private static double aplicaDesconto(double valor) {
        return valor * (1 - TAXA_DESCONTO);
    }

    private static boolean deveAplicarDesconto(double valor) {
        return valor >= MINIMO_COMPRA;
    }
}
