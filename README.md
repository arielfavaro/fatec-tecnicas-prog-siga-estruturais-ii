# SIGA — Atividade de padrões estruturais II: Decorator e Composite (código inicial)

**Técnicas de Programação II (TP2) · Aula 11** — CST em Desenvolvimento de Software Multiplataforma · Fatec de Porto Ferreira

Este é o **código inicial** da atividade prática da Aula 11. Como no da aula anterior, ele compila e executa: o problema não é que esteja quebrado, é que não aguenta crescer. São **três deslizes de estrutura**, e os dois padrões da aula resolvem cada um deles.

## Estrutura do projeto

```
siga-estruturais-ii/
└── src/
    └── siga/
        ├── Relatorio.java                 (interface da Aula 10; pronta)
        ├── RelatorioDeAlunos.java         (relatório sem opcionais; pronto)
        ├── RelatorioComCabecalho.java                      (deslizes 1 e 2)
        ├── RelatorioComNumeracao.java                      (deslize 1)
        ├── RelatorioComCabecalhoENumeracao.java            (deslizes 1 e 2)
        ├── RelatorioComCabecalhoENumeracaoEMarcaDagua.java (deslizes 1 e 2)
        ├── Disciplina.java                (folha, sem interface comum)
        ├── Modulo.java                    (composto, sem interface comum — deslize 3)
        ├── GradeCurricular.java           (travessia com instanceof — deslize 3)
        └── Main.java                      (demonstra os três deslizes)
```

## Como compilar e executar

Pré-requisito: JDK 17 ou superior (`java -version` para verificar).

```bash
# 1. Compilar (a saída vai para a pasta "bin")
javac -encoding UTF-8 -d bin src/siga/*.java

# 2. Executar
java -cp bin siga.Main
```

Execute antes de ler o restante deste arquivo: a saída **mostra os três deslizes acontecendo**.

## Os três deslizes propositais

| # | Deslize | Onde | Por que é um problema |
|---|---|---|---|
| 1 | **Explosão combinatória de subclasses** | As quatro classes `RelatorioCom...` | Cada combinação de características opcionais virou uma classe. São três características (cabeçalho, numeração, marca d'água) e já há quatro classes — faltando as combinações que ninguém pediu ainda. Com **n** características independentes, as combinações possíveis são **2ⁿ**: três exigiriam 8 classes; quatro, 16. |
| 2 | **Duplicação do comportamento** | `RelatorioComCabecalho`, `...ENumeracao`, `...EMarcaDagua` | O texto do cabeçalho está escrito em **três** classes, e o laço de numeração em três. Mude o nome da instituição e serão três edições — com chance real de esquecer uma. |
| 3 | **Travessia com `instanceof`** | `Modulo` e `GradeCurricular` | Não existe um tipo comum a `Disciplina` e `Modulo`, então a coleção é de `Object` e o cliente precisa descobrir o tipo de cada elemento. A mesma cadeia de `instanceof` aparece em `calcularCarga` e em `imprimir`; uma terceira operação exigiria uma terceira cópia. E acrescentar um novo tipo de item obriga a revisitar todos esses pontos — o princípio aberto/fechado sendo violado. |

## Sua tarefa

Siga as etapas da ficha de atividade prática:

1. **Fazer a conta.** Registre neste README quantas subclasses de relatório existem hoje e quantas seriam necessárias se a secretaria pedisse uma quarta característica (um rodapé com a data de emissão). Mostre o raciocínio de 2ⁿ.
2. **Criar os decoradores.** Escreva a classe abstrata `DecoradorRelatorio`, que implementa `Relatorio` e contém um `Relatorio`, e as três subclasses `ComCabecalho`, `ComNumeracao` e `ComMarcaDagua`. Cada uma deve conter o seu comportamento **uma vez só**. Ao terminar, as quatro classes `RelatorioCom...` originais podem ser apagadas.
3. **Empilhar.** No `Main`, monte pelo menos **duas pilhas diferentes** de decoradores sobre o mesmo `RelatorioDeAlunos` e imprima as duas saídas. Experimente inverter a ordem de `ComCabecalho` e `ComNumeracao` e observe o que muda.
4. **Criar o Composite.** Escreva a interface `ItemCurricular` com `getNome()`, `getCargaHoraria()` e `imprimir(int nivel)`. Faça `Disciplina` e `Modulo` a implementarem, com a coleção de `Modulo` passando a ser de `ItemCurricular`. A classe `GradeCurricular` deixa de existir: as operações passam a ser dos próprios itens.
5. **Provar que funciona.** Acrescente um submódulo de **terceiro nível** (uma trilha de especialização dentro de um semestre) e verifique que nada no cliente precisou mudar.

## Critério de sucesso

Ao final, verifique que: (a) o texto do cabeçalho existe em **um único lugar** do projeto; (b) três características opcionais são atendidas por **três** classes, e não por oito; (c) a palavra `instanceof` **não aparece** em nenhum arquivo; (d) a coleção de `Modulo` é de `ItemCurricular`, e não de `Object` nem de `Disciplina`; e (e) a carga horária total continua sendo **340h** antes de você acrescentar o terceiro nível.

> O item (d) é o que decide se o Composite ficou de pé. Se a coleção fosse de `Disciplina`, um módulo não poderia conter outro módulo, e a estrutura ficaria limitada a dois níveis — a etapa 5 não passaria.

## Uma decisão que é sua

Os métodos `adicionar` e `remover` fazem sentido no composto, mas não na folha. Há duas saídas: deixá-los **só em `Modulo`** (preserva a segurança de tipos, mas o cliente precisa saber que tem um composto) ou colocá-los **na interface** (preserva a uniformidade, mas a folha precisa implementá-los lançando exceção, o que tensiona o princípio de Liskov).

A apostila adota a primeira. Você pode adotar a outra — mas **justifique a escolha no README**. O que não vale é copiar uma das duas formas sem perceber que havia uma decisão a tomar.

## Padrão de entrega

Conforme a ficha de atividade prática: identificadores em português, um arquivo `.java` por classe pública, código formatado, entrega no repositório Git com README e commits descritivos. O uso de IA para gerar o código é proibido nesta atividade (ver seção 5.3 da ficha).

> **Projeto Integrador:** a Etapa 2, com entrega na Aula 13, exige a aplicação justificada de ao menos um padrão estrutural. Com esta aula a frente estrutural fica completa — Adapter, Facade, Decorator e Composite —, e a equipe escolhe entre os quatro. Desconfie da tentação de usar o mais sofisticado: um Composite sobre uma estrutura que nunca passará de dois níveis é custo sem contrapartida.
