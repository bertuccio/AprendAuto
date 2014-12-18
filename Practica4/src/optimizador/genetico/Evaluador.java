package optimizador.genetico;

import weka.core.Instances;

/**
 * Interfaz que indica los metodos necesarios para implementar un Evaluador
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
*/ 
public interface Evaluador {
   	
    /**
    * Funcion generica que otorgara una puntuacion (estimacion de bueno/malo) expresado en numeros reales positivos
    * @author Adrian Lorenzo Mateo
    * @author Andres Ruiz Carrasco
     * @param individuo individuo a ser evaluado
     * @param data conjunto de datos usado en la evaluacion
     * @param nFolds numero de divisiones que se generan en el conjunto
     * @return Puntuacion obtenida
     * @throws java.lang.Exception Excepcion generica no controlada.
    */
    public double puntua(Individuo individuo, Instances data, int nFolds) throws Exception;
}
