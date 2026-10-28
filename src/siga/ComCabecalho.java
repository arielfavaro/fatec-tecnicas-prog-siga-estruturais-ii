package siga;

/**
 * Acrescenta o cabeçalho institucional no topo do relatório.
 *
 * Este é o ÚNICO lugar do projeto onde o texto do cabeçalho está escrito.
 * Se o nome da instituição mudar, a edição é feita aqui e vale para todas as
 * combinações.
 */
public class ComCabecalho extends DecoradorRelatorio {

    private static final String CABECALHO = "FATEC DE PORTO FERREIRA\n"
                                          + "Sistema de Gestao Academica\n"
                                          + "---\n";

    public ComCabecalho(Relatorio relatorio) {
        super(relatorio);
    }

    @Override
    public String gerar() {
        return CABECALHO + super.gerar();
    }
}
