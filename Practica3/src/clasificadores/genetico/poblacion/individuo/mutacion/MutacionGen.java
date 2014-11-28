package clasificadores.genetico.poblacion.individuo.mutacion;

import clasificadores.genetico.UtilesGenetico;
import clasificadores.genetico.poblacion.individuo.Individuo;

/**
 *
 * @author Andres Ruiz
 */
public class MutacionGen implements Mutacion {

    @Override
    public Individuo muta(Individuo mutante) {
        if (mutante.getReglas().isEmpty())
            return mutante;
        mutante.getReglas().get(
                UtilesGenetico.randomNumber(
                        mutante.getReglas().size()-1)).changeGen(UtilesGenetico.randomNumber(mutante.getNumGensRegla() - 1));
        mutante.setMutated(1);
        return mutante;
    }
    
}
