package siga;

/**
 * Decorador base do padrão Decorator.
 *
 * Implementa Relatorio e, ao mesmo tempo, contém um Relatorio. Por isso um
 * decorador pode envolver tanto o relatório concreto quanto outro decorador,
 * e as características opcionais passam a ser empilhadas em tempo de execução,
 * em vez de virar uma subclasse para cada combinação.
 *
 * Por padrão apenas repassa a chamada ao relatório envolvido; cada subclasse
 * acrescenta o seu comportamento antes ou depois dessa delegação.
 */
public abstract class DecoradorRelatorio implements Relatorio {

    protected final Relatorio relatorio;

    protected DecoradorRelatorio(Relatorio relatorio) {
        this.relatorio = relatorio;
    }

    @Override
    public String gerar() {
        return relatorio.gerar();
    }
}
