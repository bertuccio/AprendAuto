package optimizador.genetico.recombinacion;

import java.util.ArrayList;
import optimizador.factory.Factory;
import optimizador.genetico.Individuo;

/**
 * Interfaz que indica los metodos necesarios para implementar una Recombinacion
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
*/ 
public interface Recombinacion {
    
    /**
     * Recombina dos individuos
     * @author Adrian Lorenzo Mateo
     * @author Andres Ruiz Carrasco
     * @param a Progenitor 
     * @param b OtherProgenitor
     * @param factory Factoria de clasificadores (MPL/IBK)
     * @return lista con los nuevos individuos
    */ 
    public ArrayList<Individuo> recombina(Individuo a, Individuo b, Factory factory);
}
