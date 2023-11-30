public class AlgoritmoGenetico {

    public static final double PROBABILIDAD_CRUCE = 0.5;
    public static final double PROBABILIDAD_MUTACION = 0.5;

    public static final int NUMERO_DE_UNOS_A_BUSCAR = 9;

    public static void main(String[] args) {
        int longitudGenes = 10;
        int tamanoPoblacion = 6;

        Poblacion poblacion = new Poblacion(longitudGenes, tamanoPoblacion);

        int generacion = 1;
        while (true) {
            System.out.println("Generación " + generacion + ":");
            poblacion.mostrarPoblacion();

            if (poblacion.haEncontradoSolucion()) {
                System.out.println("Individuo con fitness " + NUMERO_DE_UNOS_A_BUSCAR + " encontrado");
                return;
            }

            poblacion = poblacion.generarNuevaGeneracion();
            generacion++;
        }
    }
}
