package siga;

/**
 * DESLIZE 1 — a terceira combinação.
 * DESLIZE 2 — o cabeçalho está escrito de novo, palavra por palavra.
 *             Mude o nome da instituição e você terá de editar três classes.
 */
public class RelatorioComCabecalhoENumeracao extends RelatorioDeAlunos {

    @Override
    public String gerar() {
        String[] linhas = super.gerar().split("\n");
        StringBuilder numerado = new StringBuilder();
        for (int i = 0; i < linhas.length; i++) {
            numerado.append(i + 1).append(": ").append(linhas[i]).append("\n");
        }
        return "FATEC DE PORTO FERREIRA\n"
             + "Sistema de Gestao Academica\n"
             + "---\n"
             + numerado.toString().stripTrailing();
    }
}
