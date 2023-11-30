import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Poblacion {

    private List<Individuo> individuos;

    public Poblacion(int longitudGenes, int tamanoPoblacion) {
        this.individuos = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < tamanoPoblacion; i++) {
            StringBuilder genes = new StringBuilder();
            for (int j = 0; j < longitudGenes; j++) {
                //Generar poblacion al azar
                genes.append(random.nextInt(2));
            }
            individuos.add(new Individuo(genes.toString()));
        }
    }

    public void mostrarPoblacion() {
        for (Individuo individuo : individuos) {
            System.out.println(individuo);
        }
    }

    public boolean haEncontradoSolucion() {
        return individuos.stream().anyMatch(individuo -> individuo.contarUnos() == AlgoritmoGenetico.NUMERO_DE_UNOS_A_BUSCAR);
    }

    public Poblacion generarNuevaGeneracion() {
        Random random = new Random();
        List<Individuo> nuevaGeneracion = new ArrayList<>();
        //Elegir al padre con mayor fitness
        Individuo padre1 = obtenerMejorFitness();
        for (int i = 0; i < individuos.size(); i++) {
            //Seleccionamos al segundo padre por el metodo de la ruleta, y descartamos el padre 1
            Individuo padre2 = seleccionarPadrePorRuleta(padre1);
            //Aplicamos crossover
            Individuo hijo = padre1.cruzar(padre2, random);
            //Mutacion
            if (random.nextDouble() < AlgoritmoGenetico.PROBABILIDAD_MUTACION) {
                hijo = hijo.mutar(random);
            }
            //Añadimos el hijo a la nueva generacion
            nuevaGeneracion.add(hijo);
        }

        this.individuos = nuevaGeneracion;

        return this;
    }

    private Individuo obtenerMejorFitness() {
        Individuo mejorIndividuo = null;
        int mejorFitness = Integer.MIN_VALUE;

        for (Individuo individuo : individuos) {
            int fitnessActual = individuo.contarUnos();
            if (fitnessActual > mejorFitness) {
                mejorFitness = fitnessActual;
                mejorIndividuo = individuo;
            }
        }

        return mejorIndividuo;
    }

    private Individuo seleccionarPadrePorRuleta(Individuo excluido) {
        // Crear una lista de individuos excluyendo al de mayor fitness
        List<Individuo> candidatos = new ArrayList<>(individuos);
        candidatos.remove(excluido);

        int totalFitness = candidatos.stream().mapToInt(Individuo::contarUnos).sum();
        double puntoRuleta = Math.random() * totalFitness;
        double acumuladoFitness = 0;

        for (Individuo individuo : candidatos) {
            acumuladoFitness += individuo.contarUnos();
            if (acumuladoFitness >= puntoRuleta) {
                return individuo;
            }
        }

        // En caso de que la ruleta no seleccione ningún individuo (esto puede ocurrir con baja probabilidad)
        return candidatos.get(candidatos.size() - 1);
    }
}
