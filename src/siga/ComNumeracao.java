package siga;

/**
 * Numera cada linha do relatório envolvido.
 *
 * Numera tudo o que recebe: se envolver um relatório que já tem cabeçalho,
 * o cabeçalho também é numerado. Por isso a ordem do empilhamento importa.
 */
public class ComNumeracao extends DecoradorRelatorio {

    public ComNumeracao(Relatorio relatorio) {
        super(relatorio);
    }

    @Override
    public String gerar() {
        String[] linhas = super.gerar().split("\n");
        StringBuilder numerado = new StringBuilder();
        for (int i = 0; i < linhas.length; i++) {
            if (i > 0) {
                numerado.append("\n");
            }
            numerado.append(i + 1).append(": ").append(linhas[i]);
        }
        return numerado.toString();
    }
}
