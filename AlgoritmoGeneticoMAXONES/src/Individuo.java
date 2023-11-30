import java.util.Random;

public class Individuo {

    private String genes;

    public Individuo(String genes) {
        this.genes = genes;
    }

    public int contarUnos() {
        return (int) genes.chars().filter(c -> c == '1').count();
    }

    public Individuo cruzar(Individuo otroIndividuo, Random random) {
        if (random.nextDouble() < AlgoritmoGenetico.PROBABILIDAD_CRUCE) {
            // Método de cruce en un punto
            int puntoCruce = random.nextInt(genes.length());
            String genesHijo = genes.substring(0, puntoCruce) + otroIndividuo.genes.substring(puntoCruce);
            return new Individuo(genesHijo);
        } else {
            // No cruzar, devolver padre
            return this;
        }
    }

    public Individuo mutar(Random random) {
        if (random.nextDouble() < AlgoritmoGenetico.PROBABILIDAD_MUTACION) {
            // Método de mutación cambiando aleatoriamente un bit
            int indiceMutacion = random.nextInt(genes.length());
            char[] genesMutados = genes.toCharArray();
            genesMutados[indiceMutacion] = (genesMutados[indiceMutacion] == '0') ? '1' : '0';
            return new Individuo(new String(genesMutados));
        } else {
            // No mutar, devolver el mismo individuo
            return this;
        }
    }

    @Override
    public String toString() {
        return "Individuo:" + genes + ", Fitness=" + contarUnos();
    }
}
