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

        curso.imprimir(1);
        System.out.println("   Carga total: " + curso.getCargaHoraria() + "h");
        System.out.println("   >> DESLIZE 3 resolvido: o cliente chama as operacoes direto no");
        System.out.println("      item, sem perguntar o tipo. Cada item sabe se imprimir e");
        System.out.println("      calcular a propria carga; o modulo so repassa aos filhos.");

        System.out.println();
        System.out.println("Sua tarefa: ver o README.md e a ficha de atividade pratica.");
    }

    /** Imprime o relatorio com um titulo e recuo, linha por linha. */
    private static void imprimirRelatorio(String titulo, Relatorio relatorio) {
        System.out.println("   --- " + titulo + " ---");
        for (String linha : relatorio.gerar().split("\n")) {
            System.out.println("   " + linha);
        }
    }
}
