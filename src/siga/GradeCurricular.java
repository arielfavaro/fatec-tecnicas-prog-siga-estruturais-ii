package siga;

/**
 * DESLIZE 3 (parte B) — o cliente pergunta o tipo de cada elemento.
 *
 * Repare que a travessia aparece DUAS vezes, uma em cada método, com a mesma
 * cadeia de instanceof. Uma terceira operação sobre a estrutura — contar
 * disciplinas, somar créditos — exigiria uma terceira cópia.
 *
 * Pior: acrescentar um novo tipo de item curricular (uma trilha, um estágio)
 * obriga a revisitar TODOS os pontos que fazem instanceof. É o princípio
 * aberto/fechado sendo violado.
 */
public class GradeCurricular {

    public int calcularCarga(Object item) {
        if (item instanceof Disciplina) {
            return ((Disciplina) item).getCargaHoraria();
        } else if (item instanceof Modulo) {
            int total = 0;
            for (Object filho : ((Modulo) item).getFilhos()) {
                total += calcularCarga(filho);
            }
            return total;
        }
        throw new IllegalArgumentException("Tipo desconhecido: " + item);
    }

    public void imprimir(Object item, int nivel) {
        String recuo = "  ".repeat(nivel);
        if (item instanceof Disciplina) {
            Disciplina d = (Disciplina) item;
            System.out.println(recuo + d.getNome() + " (" + d.getCargaHoraria() + "h)");
        } else if (item instanceof Modulo) {
            Modulo m = (Modulo) item;
            System.out.println(recuo + "[" + m.getNome() + "]");
            for (Object filho : m.getFilhos()) {
                imprimir(filho, nivel + 1);
            }
        } else {
            throw new IllegalArgumentException("Tipo desconhecido: " + item);
        }
    }
}
