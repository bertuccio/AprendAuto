package optimizador.genetico.seleccion;

import java.util.ArrayList;
import optimizador.genetico.Poblacion;

/**
 * Interfaz que indica los metodos necesarios para implementar una Seleccion
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
*/ 
public interface Seleccion {

    /**
     * Metodo necesario para seleccionar un conjunto de vencedores o candidatos
     * a evolucionar.
     * 
     * Este metodo obtiene una poblacion y genera una lista de individuos de 
     * tamaño maxIndividuos seleccionados segun como se comporte en un entorno dado
     * @author Adrian Lorenzo Mateo
     * @author Andres Ruiz Carrasco
     * @param poblacion
     * @param nIndividuos
     * @return Lista con los individuos seleccionados
     */
    public ArrayList<Integer> selecciona(Poblacion poblacion, int nIndividuos);
}
