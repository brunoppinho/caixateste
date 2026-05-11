
### A diferença de mentalidade

**Cenário Code First (abordagem tradicional):**

```
1. Entendo o requisito
2. Desenvolvo a funcionalidade
3. Manualmente testo no Postman ou na UI
4. (Talvez) escrevo testes depois
5. Os testes cobrem os casos que lembrei de testar
6. Os casos que esqueci ficam sem cobertura
7. Bugs aparecem em produção
```

**Cenário TDD (test-first):**

```
1. Entendo o requisito
2. Traduzo o requisito em cenários de teste (comportamentos esperados)
3. Escrevo o teste — ele falha (RED)
4. Escrevo o mínimo de código para o teste passar (GREEN)
5. Melhoro o código sem quebrar o teste (REFACTOR)
6. Repito para o próximo comportamento
7. Ao final, toda funcionalidade tem cobertura por design
```

> "Percebam a diferença: no TDD os testes não são uma etapa separada — eles são o **motor** do desenvolvimento."

### Por que essa inversão importa?

> "Quando você escreve o teste primeiro, você é forçado a pensar:"

```
"O que esse código deve FAZER?" antes de pensar em "Como ele vai FAZER?"
```

> "Essa separação parece simples, mas tem um efeito profundo: você projeta interfaces mais limpas, classes com responsabilidades menores e código mais testável — porque você já está testando antes de escrever."

### Benefícios concretos do TDD

**Desenhe no quadro:**

```
┌────────────────────────────────────────────────────┐
│               BENEFÍCIOS DO TDD                    │
│                                                    │
│  🐛 Previne bugs antes de chegarem em produção     │
│  ⚡ Identifica falhas no momento em que surgem     │
│  📊 Alta cobertura de código de forma natural      │
│  🏗️  Código mais testável e com design mais limpo  │
│  📖 Testes funcionam como documentação viva        │
│  🔒 Reduz o medo de refatorar código existente     │
│  ✅ Aumenta a confiança no produto entregue        │
└────────────────────────────────────────────────────┘
```

> "O ponto da documentação viva é especialmente importante. Um novo desenvolvedor que entra no projeto pode ler os testes e entender exatamente o que cada funcionalidade deve fazer — sem precisar ler o código de produção ou perguntar para alguém."



### O ciclo

**Desenhe no quadro:**

```
         ┌──────────┐
    ┌───▶│  🔴 RED  │
    │    └────┬─────┘
    │         │ Escreve o teste que FALHA
    │         │ (o código ainda não existe)
    │         ▼
    │    ┌──────────┐
    │    │ 🟢 GREEN │
    │    └────┬─────┘
    │         │ Implementa o MÍNIMO
    │         │ para o teste passar
    │         ▼
    │    ┌──────────────┐
    └────│ 🔵 REFACTOR  │
         └──────────────┘
           Melhora o código
           SEM quebrar os testes
           Roda os testes novamente
```

### Cada etapa em detalhe

**🔴 RED:**

> "Escreva um teste para uma funcionalidade que ainda não existe. O teste vai falhar — e isso é intencional."

> "Esse passo tem um propósito importante: força você a pensar no comportamento esperado antes de pensar na implementação."

> "Uma dica: se o teste passar sem você escrever nenhum código novo, é porque ele está errado — não está testando nada de fato."

**🟢 GREEN:**

> "Escreva o código mais simples possível para fazer o teste passar."

> "Não se preocupe com elegância, performance ou boas práticas agora. O objetivo único do GREEN é sair do RED."

> "Inclusive, é aceitável hardcodar um valor de retorno só para ver o teste passar — e depois melhorar no REFACTOR."

**🔵 REFACTOR:**

> "Agora que o teste está verde, melhore o código: extraia métodos, melhore nomes, elimine duplicações, use constantes."

> "Os testes são a rede de segurança — se você quebrar algo durante o refactor, eles vão apontar imediatamente."

> "**Regra de ouro do REFACTOR:** ao terminar, execute os testes novamente. Se algum falhar, você foi longe demais. Desfaça e tente novamente em passos menores."



## Regras de negócio que devem ser implementadas

### Regra 1 — Contas de origem e destino não podem ser iguais
Se o `id` das duas contas for o mesmo, a transferência deve ser recusada imediatamente, antes de qualquer outra verificação.

- Exceção esperada: `IllegalArgumentException`

---

### Regra 2 — O valor da transferência deve ser positivo
Valores zero, negativos ou nulos devem ser rejeitados. Nenhuma alteração de saldo pode ocorrer nesses casos.

- Exceção esperada: `IllegalArgumentException`

---

### Regra 3 — O valor não pode exceder o limite máximo por transferência
O limite é de **R$ 5.000,00** por operação. Esse valor deve ser definido como uma constante nomeada no service — não como número mágico no código.

- Exceção esperada: `IllegalArgumentException`

---

### Regra 4 — A conta de origem deve ter saldo suficiente
O saldo da conta de origem deve ser maior ou igual ao valor da transferência. Se não houver saldo, nenhuma conta pode ser alterada.

- Exceção esperada: `IllegalStateException`

> A distinção entre `IllegalArgumentException` (argumento inválido) e `IllegalStateException` (estado inválido do sistema) é intencional — entenda a diferença semântica entre as duas.

---

### Regra 5 — Transferência válida deve debitar e creditar corretamente
Quando todas as validações passam, o valor é subtraído da origem e somado ao destino. As duas contas devem ser salvas via `ContaRepository`.

- Sem exceção — método `void`

---