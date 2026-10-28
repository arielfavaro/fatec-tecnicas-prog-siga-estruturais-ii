package siga;

/** Acrescenta a marca d'água de rascunho ao final do relatório. */
public class ComMarcaDagua extends DecoradorRelatorio {

    private static final String MARCA_DAGUA = "*** RASCUNHO — NAO E DOCUMENTO OFICIAL ***";

    public ComMarcaDagua(Relatorio relatorio) {
        super(relatorio);
    }

    @Override
    public String gerar() {
        return super.gerar() + "\n" + MARCA_DAGUA;
    }
}
