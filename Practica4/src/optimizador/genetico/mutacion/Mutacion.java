package optimizador.genetico.mutacion;

import optimizador.genetico.Individuo;

/**
 * Interfaz que indica los metodos necesarios para implementar una Mutacion
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
*/ 
public interface Mutacion {
    
    /**
     * Muta un cierto individuo
     * @author Adrian Lorenzo Mateo
     * @author Andres Ruiz Carrasco
     * @param mutante individuo que va ha mutar
    */ 
    public void muta(Individuo mutante);
    
}
