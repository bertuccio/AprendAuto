package clasificadores.genetico.evaluacion;

import clasificadores.genetico.MuestraGenetica;
import clasificadores.genetico.poblacion.individuo.Individuo;
import java.util.ArrayList;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public interface Evaluador {
   	
    /*Funcion generica que otorgara una puntuacion (estimacion de bueno/malo) expresado en numeros reales positivos*/
    public double puntua(Individuo objeto, ArrayList<MuestraGenetica> entornoEvolutivo);
}
