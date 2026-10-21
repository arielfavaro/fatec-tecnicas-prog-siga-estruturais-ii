package siga;

/**
 * Demonstra os três deslizes acontecendo. Execute e observe a saída:
 * é dela que partem as etapas da ficha de atividade prática.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIGA - Aula 11: Decorator e Composite (codigo inicial) ===");

        System.out.println();
        System.out.println("1) Relatorios: uma subclasse por combinacao");
        Relatorio[] variantes = {
            new RelatorioDeAlunos(),
            new RelatorioComCabecalho(),
            new RelatorioComNumeracao(),
            new RelatorioComCabecalhoENumeracao(),
            new RelatorioComCabecalhoENumeracaoEMarcaDagua()
        };
        for (Relatorio r : variantes) {
            System.out.println("   --- " + r.getClass().getSimpleName() + " ---");
            for (String linha : r.gerar().split("\n")) {
                System.out.println("   " + linha);
            }
        }
        System.out.println("   >> DESLIZE 1: sao 4 subclasses para 3 caracteristicas opcionais,");
        System.out.println("      e faltam as combinacoes que ninguem pediu ainda. Com 3 opcionais");
        System.out.println("      independentes seriam 8 classes; com 4, seriam 16.");
        System.out.println("   >> DESLIZE 2: o texto do cabecalho esta escrito em TRES classes.");

        System.out.println();
        System.out.println("2) Estrutura curricular percorrida com instanceof");
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

        GradeCurricular grade = new GradeCurricular();
        grade.imprimir(curso, 1);
        System.out.println("   Carga total: " + grade.calcularCarga(curso) + "h");
        System.out.println("   >> DESLIZE 3: a travessia com instanceof aparece DUAS vezes,");
        System.out.println("      uma em calcularCarga e outra em imprimir. Uma terceira");
        System.out.println("      operacao exigiria uma terceira copia.");

        System.out.println();
        System.out.println("Sua tarefa: ver o README.md e a ficha de atividade pratica.");
    }
}
