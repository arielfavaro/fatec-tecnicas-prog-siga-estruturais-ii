package siga;

import java.util.ArrayList;
import java.util.List;

/**
 * Composto do Composite: contém outros itens curriculares.
 *
 * A coleção é de ItemCurricular, o tipo do componente. Por isso um módulo
 * pode conter tanto disciplinas quanto outros módulos, em quantos níveis
 * forem necessários, e as operações são repassadas aos filhos sem precisar
 * descobrir o tipo de cada um.
 */
public class Modulo implements ItemCurricular {

    private final String nome;
    private final List<ItemCurricular> filhos = new ArrayList<>();

    public Modulo(String nome) {
        this.nome = nome;
    }

    public void adicionar(ItemCurricular item) {
        filhos.add(item);
    }

    public void remover(ItemCurricular item) {
        filhos.remove(item);
    }

    @Override
    public String getNome() {
        return nome;
    }

    /** A carga do módulo é a soma da carga dos filhos, seja qual for o tipo deles. */
    @Override
    public int getCargaHoraria() {
        int total = 0;
        for (ItemCurricular filho : filhos) {
            total += filho.getCargaHoraria();
        }
        return total;
    }

    @Override
    public void imprimir(int nivel) {
        String recuo = "  ".repeat(nivel);
        System.out.println(recuo + "[" + nome + "]");
        for (ItemCurricular filho : filhos) {
            filho.imprimir(nivel + 1);
        }
    }
}
