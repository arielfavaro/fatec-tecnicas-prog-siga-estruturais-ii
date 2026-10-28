package siga;

/**
 * Componente do padrão Composite: o tipo comum a Disciplina (folha) e
 * Modulo (composto).
 *
 * Com esse tipo, quem percorre a estrutura não precisa mais perguntar o que
 * cada elemento é: basta chamar a operação, e cada item sabe responder por si.
 *
 * adicionar e remover ficam só em Modulo, e não aqui: a folha não teria como
 * implementá-los a não ser lançando exceção.
 */
public interface ItemCurricular {

    String getNome();

    int getCargaHoraria();

    void imprimir(int nivel);
}
