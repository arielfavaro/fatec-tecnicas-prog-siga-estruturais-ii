# SIGA — Atividade de padrões estruturais II: Decorator e Composite

**Técnicas de Programação II (TP2) · Aula 11** — CST em Desenvolvimento de Software Multiplataforma · Fatec de Porto Ferreira

Repositório da atividade prática da Aula 11. O código inicial tinha três deslizes de estrutura: uma subclasse de relatório para cada combinação de características, o mesmo comportamento copiado em várias classes e uma estrutura curricular percorrida com `instanceof`. A refatoração aplica o padrão **Decorator** nos relatórios e o padrão **Composite** na estrutura curricular.

## Estrutura final do projeto

```
siga-estruturais-ii/
├── src/
│   └── siga/
│       ├── Relatorio.java            (interface do relatório; componente do Decorator)
│       ├── RelatorioDeAlunos.java    (relatório sem opcionais; componente concreto)
│       ├── DecoradorRelatorio.java   (decorador abstrato: implementa e contém um Relatorio)
│       ├── ComCabecalho.java         (decorador: cabeçalho institucional)
│       ├── ComNumeracao.java         (decorador: numeração das linhas)
│       ├── ComMarcaDagua.java        (decorador: marca d'água de rascunho)
│       ├── ItemCurricular.java       (interface comum; componente do Composite)
│       ├── Disciplina.java           (folha do Composite)
│       ├── Modulo.java               (composto do Composite, com coleção de ItemCurricular)
│       └── Main.java                 (cliente: monta as pilhas e a estrutura curricular)
├── Dockerfile
├── docker-compose.yml
└── README.md
```

## Como compilar e executar

Pré-requisito: JDK 17 ou superior (`java -version` para verificar).

```bash
# 1. Compilar (a saída vai para a pasta "bin")
javac -encoding UTF-8 -d bin src/siga/*.java

# 2. Executar
java -cp bin siga.Main
```

A saída mostra as pilhas de decoradores sobre o mesmo relatório e a estrutura curricular, primeiro com dois níveis e depois com a trilha de terceiro nível.

## Os três deslizes do código inicial

O código inicial compilava e executava, mas não aguentava crescer. Estes eram os três deslizes, todos resolvidos nas etapas abaixo.

| # | Deslize | Onde | Por que é um problema |
|---|---|---|---|
| 1 | **Explosão combinatória de subclasses** | As quatro classes `RelatorioCom...` | Cada combinação de características opcionais virou uma classe. São três características (cabeçalho, numeração, marca d'água) e já há quatro classes — faltando as combinações que ninguém pediu ainda. Com **n** características independentes, as combinações possíveis são **2ⁿ**: três exigiriam 8 classes; quatro, 16. |
| 2 | **Duplicação do comportamento** | `RelatorioComCabecalho`, `...ENumeracao`, `...EMarcaDagua` | O texto do cabeçalho está escrito em **três** classes, e o laço de numeração em três. Mude o nome da instituição e serão três edições — com chance real de esquecer uma. |
| 3 | **Travessia com `instanceof`** | `Modulo` e `GradeCurricular` | Não existe um tipo comum a `Disciplina` e `Modulo`, então a coleção é de `Object` e o cliente precisa descobrir o tipo de cada elemento. A mesma cadeia de `instanceof` aparece em `calcularCarga` e em `imprimir`; uma terceira operação exigiria uma terceira cópia. E acrescentar um novo tipo de item obriga a revisitar todos esses pontos — o princípio aberto/fechado sendo violado. |

## O que foi feito em cada etapa

1. **Etapa 1 - A conta das subclasses:**
   - Registrada na seção [Etapa 1 — A conta das subclasses de relatório](#etapa-1--a-conta-das-subclasses-de-relatório): 4 subclasses existentes para 3 características, 7 necessárias pela conta de 2ⁿ − 1 e 15 com uma quarta característica.

2. **Etapa 2 - Decoradores:**
   - Criada a classe abstrata `DecoradorRelatorio`, que implementa `Relatorio` e contém um `Relatorio`, repassando a chamada por padrão.
   - Criados `ComCabecalho`, `ComNumeracao` e `ComMarcaDagua`, cada um com o seu comportamento escrito uma vez só. O texto do cabeçalho existe apenas em `ComCabecalho`.
   - Removidas as quatro subclasses `RelatorioCom...`. Antes de apagar, a saída de cada combinação montada com decoradores foi comparada com a da subclasse equivalente, e as quatro eram idênticas.

3. **Etapa 3 - Pilhas de decoradores:**
   - No `Main`, três pilhas sobre o mesmo `RelatorioDeAlunos`:

     | Pilha | Montagem | Saída |
     |---|---|---|
     | 1 | `ComCabecalho(ComNumeracao(base))` | cabeçalho sem número, alunos numerados de 1 a 3 |
     | 2 | `ComNumeracao(ComCabecalho(base))` | tudo numerado de 1 a 6, inclusive o cabeçalho |
     | 3 | `ComMarcaDagua(pilha 1)` | a pilha 1 com a marca d'água no final |

   - As pilhas 1 e 2 têm os mesmos decoradores em ordem invertida, e a saída muda: o decorador de fora recebe o resultado do de dentro, então na pilha 2 `ComNumeracao` recebe o relatório já com cabeçalho e numera tudo.

4. **Etapa 4 - Composite:**
   - Criada a interface `ItemCurricular` com `getNome()`, `getCargaHoraria()` e `imprimir(int nivel)`.
   - `Disciplina` passou a ser a folha e `Modulo` o composto, com a coleção do tipo `List<ItemCurricular>`. O módulo calcula a carga e se imprime repassando as chamadas aos filhos.
   - Removida a classe `GradeCurricular`: as operações passaram a ser dos próprios itens, sem `instanceof`.
   - `adicionar` e `remover` ficaram só em `Modulo`; a justificativa está em [Uma decisão que é sua](#uma-decisão-que-é-sua).

5. **Etapa 5 - Terceiro nível:**
   - O cliente da estrutura é o método `exibirEstrutura(ItemCurricular)` no `Main`, que só usa as operações da interface.
   - Ele é chamado com a estrutura de dois níveis, que dá 340h, e depois de acrescentar a "Trilha de especializacao: Mobile", um `Modulo` dentro do 2º semestre, que dá 460h. As duas saídas vêm da mesma chamada, sem alteração no cliente nem em `ItemCurricular`, `Disciplina` ou `Modulo`.

## Etapa 1 — A conta das subclasses de relatório

### 1. Quantas subclasses existem hoje

- **Onde está:** em `src/siga/`, todas as classes que estendem `RelatorioDeAlunos`.
- **Contagem:** são **4 subclasses** para **3 características opcionais**: cabeçalho, numeração e marca d'água.

| # | Subclasse | Cabeçalho | Numeração | Marca d'água |
|---|---|:---:|:---:|:---:|
| 1 | `RelatorioComCabecalho` | V | | |
| 2 | `RelatorioComNumeracao` | | V | |
| 3 | `RelatorioComCabecalhoENumeracao` | V | V | |
| 4 | `RelatorioComCabecalhoENumeracaoEMarcaDagua` | V | V | V |

A classe base `RelatorioDeAlunos` não entra nessa conta como subclasse: ela é o relatório sem nenhum opcional.

### 2. O raciocínio de 2ⁿ

Cada característica opcional é uma decisão independente de está no relatório ou não está, sim ou não. Para **n** características, cada uma com 2 possibilidades, o total de combinações é:

```
2 × 2 × ... × 2  (n vezes)  =  2ⁿ
```

Essa conta inclui a combinação "nenhum opcional", que é o próprio `RelatorioDeAlunos`. Portanto, o número de subclasses necessárias é **2ⁿ − 1**.

**Com as 3 características atuais (n = 3):**

```
2³ = 8 combinações -> 1 relatório base + 7 subclasses
```

Hoje existem só 4 das 7 subclasses. Faltam as combinações que ninguém pediu ainda:

- `RelatorioComMarcaDagua`
- `RelatorioComCabecalhoEMarcaDagua`
- `RelatorioComNumeracaoEMarcaDagua`

### 3. Com uma quarta característica (rodapé com a data de emissão)

**Com 4 características (n = 4):**

```
2⁴ = 16 combinações -> 1 relatório base + 15 subclasses
```

| Características (n) | Combinações (2ⁿ) | Subclasses necessárias (2ⁿ − 1) | Existem hoje | Faltariam |
|:---:|:---:|:---:|:---:|:---:|
| 3 | 8 | 7 | 4 | 3 |
| 4 | 16 | 15 | 4 | **11** |

Ou seja: pedir uma característica a mais, o rodapé, dobra o número de combinações, de 8 para 16. Seriam 11 classes novas só para cobrir as combinações, e o código do rodapé estaria copiado em 8 delas.

### 4. Conclusão

**O problema:** o número de classes cresce de forma exponencial (2ⁿ − 1), enquanto o número de comportamentos cresce de forma linear (n). Cada característica nova dobra o total, e cada comportamento fica duplicado em metade das classes (2ⁿ⁻¹), que é exatamente o deslize 2.

**O que o Decorator muda:** cada característica vira **uma** classe decoradora, combinável em tempo de execução. O número de classes passa a ser **n**: 3 decoradores hoje e 4 com o rodapé, em vez de 7 e 15 subclasses.

| Características (n) | Herança (2ⁿ − 1 subclasses) | Decorator (n decoradores) |
|:---:|:---:|:---:|
| 3 | 7 | 3 |
| 4 | 15 | 4 |
| 5 | 31 | 5 |

**Observação:** a conta 2ⁿ considera só quais características estão presentes. Como a ordem de aplicação também muda o resultado, numerar antes ou depois de colocar o cabeçalho gera saídas diferentes, a herança precisaria de ainda mais classes para cobrir todas as variações. Com o Decorator, a ordem é só a ordem do empilhamento.

## Critérios de sucesso atendidos

- [x] **(a)** O texto do cabeçalho existe em um único lugar do projeto: a constante `CABECALHO` em `ComCabecalho`.
- [x] **(b)** As três características opcionais são atendidas por três classes, `ComCabecalho`, `ComNumeracao` e `ComMarcaDagua`, e não por oito.
- [x] **(c)** A palavra `instanceof` não aparece em nenhum arquivo `.java`.
- [x] **(d)** A coleção de `Modulo` é `List<ItemCurricular>`, e não de `Object` nem de `Disciplina`.
- [x] **(e)** A carga horária total continua sendo 340h antes de acrescentar o terceiro nível; com a trilha, passa a 460h.

> O item (d) é o que decide se o Composite ficou de pé. Se a coleção fosse de `Disciplina`, um módulo não poderia conter outro módulo, e a estrutura ficaria limitada a dois níveis — a etapa 5 não passaria.

## Uma decisão que é sua

Os métodos `adicionar` e `remover` fazem sentido no composto, mas não na folha. Há duas saídas: deixá-los **só em `Modulo`** (preserva a segurança de tipos, mas o cliente precisa saber que tem um composto) ou colocá-los **na interface** (preserva a uniformidade, mas a folha precisa implementá-los lançando exceção, o que tensiona o princípio de Liskov).

A apostila adota a primeira. Você pode adotar a outra — mas **justifique a escolha no README**. O que não vale é copiar uma das duas formas sem perceber que havia uma decisão a tomar.

### Escolha adotada: `adicionar` e `remover` só em `Modulo`

Segui a mesma forma da apostila: a interface `ItemCurricular` tem só as operações que fazem sentido para todo item, `getNome()`, `getCargaHoraria()` e `imprimir(int nivel)`. Os métodos `adicionar` e `remover` ficam apenas em `Modulo`.

**Por que essa e não a outra:**

1. **O erro aparece na compilação, não na execução.** Com os métodos só em `Modulo`, escrever `disciplina.adicionar(...)` nem compila. Se eles estivessem na interface, o código compilaria e o problema só apareceria quando a `Disciplina` lançasse a exceção, já com o programa rodando.

2. **A folha cumpre todo o contrato da interface.** Qualquer `ItemCurricular`, seja `Disciplina` ou `Modulo`, responde a todos os métodos da interface de verdade. Não existe método que a folha "implementa" só para lançar `UnsupportedOperationException`, então trocar um item pelo outro não quebra quem usa a interface, que é o que o princípio de Liskov pede.

3. **O custo da escolha quase não aparece no SIGA.** A desvantagem é que o cliente precisa saber que tem um `Modulo` para adicionar filhos. Aqui isso acontece só na montagem da estrutura, no `Main`, onde cada módulo já é criado com `new Modulo(...)` e guardado numa variável do tipo `Modulo`. Depois de montada, a estrutura é percorrida só pelas operações da interface, e nesse momento o cliente trata tudo como `ItemCurricular`, sem saber nem perguntar o tipo.

**Quando a outra forma valeria mais:** se a estrutura fosse montada de forma genérica, por exemplo lida de um arquivo ou banco de dados, com o código recebendo itens como `ItemCurricular` e precisando adicionar filhos sem saber o tipo, a uniformidade de ter `adicionar` na interface passaria a compensar a perda de segurança de tipos. Não é o caso desta atividade.

## Rodar via Docker Compose

Pré-requisito: Docker e Docker Compose instalados.

```bash
# Buildar e executar o projeto
docker compose up --build

# Ou apenas executar (compilação automática via volume)
docker compose up
```

## Padrão de entrega

Conforme a ficha de atividade prática: identificadores em português, um arquivo `.java` por classe pública, código formatado, entrega no repositório Git com README e commits descritivos. O uso de IA para gerar o código é proibido nesta atividade (ver seção 5.3 da ficha).

> **Projeto Integrador:** a Etapa 2, com entrega na Aula 13, exige a aplicação justificada de ao menos um padrão estrutural. Com esta aula a frente estrutural fica completa — Adapter, Facade, Decorator e Composite —, e a equipe escolhe entre os quatro. Desconfie da tentação de usar o mais sofisticado: um Composite sobre uma estrutura que nunca passará de dois níveis é custo sem contrapartida.
