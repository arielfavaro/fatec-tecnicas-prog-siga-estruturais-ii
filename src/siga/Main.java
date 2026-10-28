package siga;

/**
 * Demonstra os três deslizes acontecendo. Execute e observe a saída:
 * é dela que partem as etapas da ficha de atividade prática.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIGA - Aula 11: Decorator e Composite (codigo inicial) ===");

        System.out.println();
        System.out.println("1) Relatorios: pilhas de decoradores sobre o mesmo RelatorioDeAlunos");
        Relatorio base = new RelatorioDeAlunos();

        // Pilha 1: numera primeiro, depois coloca o cabecalho por fora.
        Relatorio cabecalhoSobreNumeracao = new ComCabecalho(new ComNumeracao(base));

        // Pilha 2: mesma base e mesmos decoradores, com a ordem invertida.
        Relatorio numeracaoSobreCabecalho = new ComNumeracao(new ComCabecalho(base));

        // Pilha 3: a pilha 1 com a marca d'agua por fora de tudo.
        Relatorio rascunho = new ComMarcaDagua(cabecalhoSobreNumeracao);

        imprimirRelatorio("RelatorioDeAlunos (sem decoradores)", base);
        imprimirRelatorio("Pilha 1: ComCabecalho(ComNumeracao(base))", cabecalhoSobreNumeracao);
        imprimirRelatorio("Pilha 2: ComNumeracao(ComCabecalho(base))", numeracaoSobreCabecalho);
        imprimirRelatorio("Pilha 3: ComMarcaDagua(ComCabecalho(ComNumeracao(base)))", rascunho);

        System.out.println("   >> Pilhas 1 e 2 usam a mesma base e os mesmos decoradores, so");
        System.out.println("      muda a ordem: na 1 so os alunos sao numerados; na 2 o");
        System.out.println("      cabecalho tambem e numerado, porque ComNumeracao numera tudo");
        System.out.println("      o que recebe, e ali ele recebe o relatorio ja com cabecalho.");
        System.out.println("   >> A pilha 3 reaproveita a pilha 1 inteira: um decorador pode");
        System.out.println("      envolver outro decorador, porque todos sao um Relatorio.");

        System.out.println();
        System.out.println("2) Estrutura curricular com Composite");
        Modulo curso = new Modulo("DSM - Desenvolvimento de Software Multiplataforma");

        Modulo primeiro = new Modulo("1o semestre");
        primeiro.adicionar(new Disciplina("Algoritmos", 80));
        primeiro.adicionar(new Disciplina("Banco de Dados I", 60));

        Modulo segundo = new Modulo("2o semestre");
        segundo.adicionar(new Disciplina("Tecnicas de Programacao II", 80));
        segundo.adicionar(new Disciplina("Desenvolvimento Web II", 80));

        curso.adicionar(primeiro);
        curso.adicionar(segundo);
        curso.adicionar(new Disciplina("Projeto Integrador", 40));

        System.out.println("   --- Dois niveis: curso > semestre > disciplina ---");
        exibirEstrutura(curso);
        System.out.println("   >> DESLIZE 3 resolvido: o cliente chama as operacoes direto no");
        System.out.println("      item, sem perguntar o tipo. Cada item sabe se imprimir e");
        System.out.println("      calcular a propria carga; o modulo so repassa aos filhos.");

        System.out.println();
        System.out.println("3) Terceiro nivel: trilha de especializacao dentro de um semestre");
        // So a montagem muda: a trilha e um Modulo dentro do 2o semestre.
        Modulo trilha = new Modulo("Trilha de especializacao: Mobile");
        trilha.adicionar(new Disciplina("Desenvolvimento para Dispositivos Moveis", 80));
        trilha.adicionar(new Disciplina("Interface e Experiencia do Usuario", 40));
        segundo.adicionar(trilha);

        System.out.println("   --- Tres niveis: curso > semestre > trilha > disciplina ---");
        exibirEstrutura(curso);
        System.out.println("   >> O cliente, exibirEstrutura, e o mesmo metodo chamado antes,");
        System.out.println("      sem nenhuma alteracao. Modulo aceita outro Modulo porque a");
        System.out.println("      colecao e de ItemCurricular, entao a profundidade nao importa.");

        System.out.println();
        System.out.println("Sua tarefa: ver o README.md e a ficha de atividade pratica.");
    }

    /**
     * Cliente da estrutura curricular: imprime a arvore e a carga total.
     *
     * Recebe um ItemCurricular e so usa as operacoes da interface, sem saber
     * se e uma disciplina ou um modulo nem quantos niveis a estrutura tem.
     * Por isso funciona igual com dois ou tres niveis.
     */
    private static void exibirEstrutura(ItemCurricular item) {
        item.imprimir(1);
        System.out.println("   Carga total: " + item.getCargaHoraria() + "h");
    }

    /** Imprime o relatorio com um titulo e recuo, linha por linha. */
    private static void imprimirRelatorio(String titulo, Relatorio relatorio) {
        System.out.println("   --- " + titulo + " ---");
        for (String linha : relatorio.gerar().split("\n")) {
            System.out.println("   " + linha);
        }
    }
}
