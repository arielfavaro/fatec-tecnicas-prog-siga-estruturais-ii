package siga;

/**
 * DESLIZE 1 — uma subclasse por combinação de características.
 * DESLIZE 2 — o texto do cabeçalho começa a se repetir aqui.
 */
public class RelatorioComCabecalho extends RelatorioDeAlunos {

    @Override
    public String gerar() {
        return "FATEC DE PORTO FERREIRA\n"
             + "Sistema de Gestao Academica\n"
             + "---\n"
             + super.gerar();
    }
}
