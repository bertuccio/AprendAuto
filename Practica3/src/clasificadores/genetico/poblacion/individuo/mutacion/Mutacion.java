package clasificadores.genetico.poblacion.individuo.mutacion;

import clasificadores.genetico.poblacion.individuo.Individuo;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public interface Mutacion {

    public Individuo muta(Individuo mutante);
    
}
