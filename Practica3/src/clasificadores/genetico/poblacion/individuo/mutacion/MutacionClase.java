package clasificadores.genetico.poblacion.individuo.mutacion;

import clasificadores.genetico.UtilesGenetico;
import clasificadores.genetico.poblacion.individuo.Individuo;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public class MutacionClase implements Mutacion{

    @Override
    public Individuo muta(Individuo mutante) {
        if (mutante.getReglas().isEmpty()) return mutante;
        mutante.getReglas().get(UtilesGenetico.randomNumber(mutante.getReglas().size()-1)).changeClass();
        mutante.setMutated(1);
        return mutante;
    }
    
}
