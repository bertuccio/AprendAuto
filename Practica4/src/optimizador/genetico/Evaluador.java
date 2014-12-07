package optimizador.genetico;

import java.util.ArrayList;
import weka.core.Instances;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public interface Evaluador {
   	
    /*Funcion generica que otorgara una puntuacion (estimacion de bueno/malo) expresado en numeros reales positivos*/
    public double puntua(Individuo individuo, Instances data, int nFolds) throws Exception;
}
