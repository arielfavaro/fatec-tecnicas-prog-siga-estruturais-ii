package siga;

import java.util.ArrayList;
import java.util.List;

/**
 * Composto da estrutura curricular — hoje sem interface em comum com Disciplina.
 *
 * DESLIZE 3 (parte A) — a coleção é de Object, porque não existe um tipo que
 * abranja tanto Disciplina quanto Modulo. O compilador deixa de ajudar, e a
 * responsabilidade de descobrir o tipo recai sobre quem percorre a estrutura.
 */
public class Modulo {

    private final String nome;
    private final List<Object> filhos = new ArrayList<>();

    public Modulo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void adicionar(Object filho) {
        filhos.add(filho);
    }

    public List<Object> getFilhos() {
        return filhos;
    }
}
