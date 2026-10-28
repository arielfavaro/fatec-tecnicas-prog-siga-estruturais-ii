package siga;

/**
 * Folha do Composite: não contém outros itens.
 *
 * A carga horária é o próprio valor da disciplina, e imprimir mostra só
 * a si mesma.
 */
public class Disciplina implements ItemCurricular {

    private final String nome;
    private final int cargaHoraria;

    public Disciplina(String nome, int cargaHoraria) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int getCargaHoraria() {
        return cargaHoraria;
    }

    @Override
    public void imprimir(int nivel) {
        String recuo = "  ".repeat(nivel);
        System.out.println(recuo + nome + " (" + cargaHoraria + "h)");
    }
}
