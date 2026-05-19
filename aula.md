
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



```
Teste Unitário:             Teste de Integração:

Service (REAL)              HTTP Request
    │                           │
    │ ← mock →                  ▼
Repository (FALSO)      Controller  (REAL)
                                │
                                ▼
                            Service     (REAL)
                                │
                                ▼
                            Repository  (REAL)
                                │
                                ▼
                            Banco H2    (REAL)
                                │
                                ▼
                            HTTP Response
```

> "No teste de integração, **tudo é real**. O único componente que substituímos é o banco de produção — e para isso usamos o H2, um banco em memória que se comporta como o PostgreSQL ou MySQL, mas sem instalação, sem porta, sem dado persistido entre execuções."


```
                    ┌─────────────────────┐
                    │   Testes E2E / UI   │  ← Poucos. Lentos. Caros.
                    └──────────┬──────────┘
                    ┌──────────┴──────────┐
                    │  Testes de          │  ← Alguns. Médios.
                    │  Integração         │
                    └──────────┬──────────┘
          ┌─────────────────────────────────────┐
          │        Testes Unitários              │  ← Muitos. Rápidos. Baratos.
          └─────────────────────────────────────┘
```

> "A base tem o maior volume porque é rápida e barata. Cada teste roda em milissegundos. Você pode ter centenas deles."

> "O meio — testes de integração — são mais lentos (sobem o contexto Spring, inicializam o H2), mas cobrem algo que os unitários não cobrem: a colaboração entre os componentes."

> "O topo — E2E — simula o usuário real no navegador. São os mais lentos e os mais frágeis. Poucos, para fluxos realmente críticos."

### Propriedades de cada camada

| | Unitário | Integração | E2E |
|---|---|---|---|
| Velocidade | ⚡ milissegundos | 🕐 segundos | 🕕 minutos |
| O que usa | Mocks | Banco H2 real | Sistema completo |
| O que cobre | Uma classe isolada | Fluxo de funcionalidade | Jornada do usuário |
| Custo de manutenção | Baixo | Médio | Alto |
| Quantidade ideal | Muitos | Alguns | Poucos |

> "O erro mais comum é a pirâmide invertida: poucos testes unitários e muitos testes de integração. Isso resulta numa suíte lenta, frágil e cara de manter."

### O que cada camada deve cobrir

```
Testes Unitários:
├─ Regras de negócio em isolamento (cálculos, validações, condicionais)
├─ Comportamentos de borda (zero, negativo, null, lista vazia)
├─ Cada branch de um if/else separadamente
└─ Cenários de exceção

Testes de Integração:
├─ Fluxo completo de uma funcionalidade (criar, buscar, atualizar, deletar)
├─ Interação real entre Service e banco de dados
├─ Status HTTP correto por endpoint
└─ Dados persistidos com valores corretos após a operação

Testes E2E:
├─ Jornada do usuário (login → ação → resultado visível)
└─ Fluxos críticos de negócio de ponta a ponta
```


```
@SpringBootTest(webEnvironment = RANDOM_PORT)
│
├─ Sobe o contexto COMPLETO do Spring
├─ Carrega todos os beans: Controllers, Services, Repositories
├─ RANDOM_PORT: usa porta aleatória → testes rodam em paralelo sem conflito
└─ Sem mocks — tudo é real

@AutoConfigureMockMvc
│
└─ Configura o MockMvc com o contexto completo automaticamente
   Diferente do standaloneSetup — aqui tudo está conectado de verdade

Resultado: MockMvc real + Spring real + H2 real
```

### Comparativo: standaloneSetup vs @SpringBootTest

| | `standaloneSetup` (aulas anteriores) | `@SpringBootTest` (hoje) |
|---|---|---|
| Tipo de teste | Unitário da Controller | Integração end-to-end |
| Contexto Spring | ❌ Não sobe | ✅ Sobe completo |
| Service | Mock | Real |
| Repository | Não existe | Real |
| Banco | Não existe | H2 em memória |
| Velocidade | ⚡ Milissegundos | 🕐 Segundos |
| Detecta erros de integração | ❌ Não | ✅ Sim |

### Estrutura base de um teste de integração

```java
@SpringBootTest(
    classes = ProdutoApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class ProdutoIntegracaoTest {

    @Autowired
    private MockMvc mockMvc; // configurado pelo Spring — não pelo standaloneSetup

    @Autowired
    private ProdutoRepository produtoRepository; // repositório real, H2 real

    @BeforeEach
    public void limparBanco() {
        produtoRepository.deleteAll(); // garante banco limpo antes de cada teste
    }

    // Utilitário: converte objeto Java → JSON String
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
```

> "Reparem: aqui não tem `@Mock`, não tem `@InjectMocks`. O Spring injeta tudo real. O `@Autowired` nos dá o `MockMvc` já conectado a todos os beans reais."

> "O `@BeforeEach` com `deleteAll()` garante que cada teste começa com o banco vazio. Sem isso, dados de um teste podem afetar o próximo."


### Estratégia 3: `@Sql` para popular o banco com script

**Crie o arquivo `src/test/resources/dados-teste.sql`:**

```sql
INSERT INTO produto (id, nome, descricao, unidades)
VALUES (1, 'TV 55', 'TV LED 55 polegadas', 10);

INSERT INTO produto (id, nome, descricao, unidades)
VALUES (2, 'TV 48', 'TV LCD 48 polegadas', 5);
```

**Use no teste:**

```java
@Test
@Sql("/dados-teste.sql")                         // executa ANTES do teste
@Sql(scripts = "/limpar.sql",
     executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD) // executa DEPOIS
public void listarProdutosComDadosPreCarregados() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/produtos"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2));
}
```


### Comparativo das estratégias

| Estratégia | Quando usar | Vantagem | Cuidado |
|---|---|---|---|
| `deleteAll()` no `@BeforeEach` | Maioria dos casos | Simples, explícito, confiável | Pode ser lento com muitos dados |
| `@Transactional` | Testes sem contexto de porta HTTP | Automático, sem código extra | Não funciona bem com `RANDOM_PORT` |
| `@Sql` | Cenários com dados específicos pré-carregados | Dados de teste versionados em arquivo | Requer manutenção de scripts SQL |


```
┌─────────────────────────────────────────────┐
│                                             │
│   Seu código Java                           │
│   driver.get("https://google.com")          │
│   driver.findElement(By.name("q"))          │
│                  │                          │
│                  ▼                          │
│         WebDriver (API)                     │
│                  │                          │
│                  ▼                          │
│         ChromeDriver (binário)              │
│         chromedriver.exe / chromedriver     │
│                  │                          │
│                  ▼                          │
│         Chrome (navegador real)             │
│         — abre, clica, preenche —           │
│                                             │
└─────────────────────────────────────────────┘
```


### O que o Selenium consegue fazer

```
✅ Abrir URLs no navegador
✅ Clicar em botões, links e elementos
✅ Preencher campos de formulário
✅ Selecionar opções em dropdowns
✅ Verificar texto exibido na página
✅ Verificar se elementos existem ou estão visíveis
✅ Navegar entre páginas (voltar, avançar)
✅ Executar JavaScript na página
✅ Tirar screenshots
```

### O ciclo de um teste Selenium

```
1. Inicializar o driver (abrir o Chrome)
2. Navegar para a URL
3. Localizar elementos (By.name, By.id, By.css...)
4. Interagir com os elementos (click, sendKeys)
5. Verificar o resultado (assertEquals, assertTrue)
6. Fechar o driver (driver.quit())
```

> "O `driver.quit()` é obrigatório — ele fecha o navegador. Se você esquecer, o Chrome vai ficar aberto em segundo plano para sempre durante os testes."
