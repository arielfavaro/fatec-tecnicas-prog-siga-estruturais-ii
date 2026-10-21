package siga;

/**
 * DESLIZE 1 — a quarta combinação, e o nome da classe já denuncia o problema.
 * DESLIZE 2 — terceira cópia do cabeçalho e segunda da numeração.
 *
 * Com três características opcionais seriam necessárias OITO classes como esta.
 * Com quatro, dezesseis.
 */
public class RelatorioComCabecalhoENumeracaoEMarcaDagua extends RelatorioDeAlunos {

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
             + numerado.toString().stripTrailing()
             + "\n*** RASCUNHO — NAO E DOCUMENTO OFICIAL ***";
    }
}
