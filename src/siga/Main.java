package siga;

/**
 * Demonstra os três deslizes acontecendo. Execute e observe a saída:
 * é dela que partem as etapas da ficha de atividade prática.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== SIGA - Aula 11: Decorator e Composite (codigo inicial) ===");

        System.out.println();
        System.out.println("1) Relatorios: decoradores empilhados sobre o mesmo relatorio");
        Relatorio base = new RelatorioDeAlunos();
        Relatorio[] variantes = {
            base,
            new ComCabecalho(base),
            new ComNumeracao(base),
            new ComCabecalho(new ComNumeracao(base)),
            new ComMarcaDagua(new ComCabecalho(new ComNumeracao(base)))
        };
        String[] descricoes = {
            "RelatorioDeAlunos",
            "ComCabecalho",
            "ComNumeracao",
            "ComCabecalho(ComNumeracao)",
            "ComMarcaDagua(ComCabecalho(ComNumeracao))"
        };
        for (int i = 0; i < variantes.length; i++) {
            Relatorio r = variantes[i];
            System.out.println("   --- " + descricoes[i] + " ---");
            for (String linha : r.gerar().split("\n")) {
                System.out.println("   " + linha);
            }
        }
        System.out.println("   >> DESLIZES 1 e 2 resolvidos: 3 decoradores atendem as 3 caracteristicas,");
        System.out.println("      e cada comportamento esta escrito uma vez so.");

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
