package clasificadores;

import datos.Datos;
import genetico.Individuo;
import genetico.Poblacion;
import java.util.ArrayList;
import particionado.DivisionPorcentual;
import particionado.EstrategiaParticionado;
import particionado.ValidacionCruzada;


/**
 * Clasificador que utiliza un modelo KNN
 * 
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class ClasificadorGenetico extends Clasificador{
    /*Metodos privados*/
    private Poblacion poblacion;

    public Poblacion getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(Poblacion poblacion) {
        this.poblacion = poblacion;
    }

    public Individuo getBest() {
        return best;
    }

    public void setBest(Individuo best) {
        this.best = best;
    }

    public int getMaxGeneraciones() {
        return maxGeneraciones;
    }

    public void setMaxGeneraciones(int maxGeneraciones) {
        this.maxGeneraciones = maxGeneraciones;
    }
    private Individuo best;
    
    private int maxGeneraciones;

    /*Metodos publicos*/
    @Override
    public void entrenamiento(Datos datosTrain) {
        
    }

    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        
    }



    /**
     * 
     * Main de uso del clasificador Genetico:
     * USO:
     *  Parametros disponibles:
     *      -input file: entrada de datos
     *      -cruzada: particionado con validacion cruzada
     *      -partition: particionado porcentual
     *      -laplace: utilizar suavizado de laplace
     *      -debug: activar trazas de debug          
     * 
     * @param args cadena de opciones "-input file [-cruzada|-partition] [-debug]"
     */
    public static void main(String[] args) {

        String inputFile = "input";
        Integer particion = 5;

        EstrategiaParticionado part = new DivisionPorcentual();
        Clasificador clasificador = new ClasificadorGenetico();

        for (int i = 0; i < args.length; i++) {
            
            if (args[i].compareTo("-input") == 0) {
                
                inputFile = args[i + 1];
                i++;
            }
            if (args[i].compareTo("-cruzada") == 0) {

                part = new ValidacionCruzada();

            }
            if (args[i].compareTo("-particion") == 0) {
                
                particion = Integer.parseInt(args[i + 1]);
                i++;
            }
        }
        Datos d = Datos.cargaDeFichero(inputFile);

        double error = 0;
        ArrayList<Double> resultados = Clasificador.validacion(
                part, d, clasificador, particion);
        for (Double resultado : resultados) {
            error += resultado;
        }
        error /= resultados.size();
        
        System.out.println(error);
    }
}
