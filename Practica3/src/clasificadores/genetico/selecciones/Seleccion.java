package clasificadores.genetico.selecciones;

import clasificadores.genetico.poblacion.Poblacion;
import java.util.ArrayList;

public interface Seleccion {

    /**
     * Metodo necesario para seleccionar un conjunto de vencedores o candidatos
     * a evolucionar.
     * 
     * Este metodo obtiene una poblacion y genera una lista de individuos de 
     * tamaño maxIndividuos seleccionados segun como se comporte en un entorno dado
     * 
     * @param poblacion
     * @param nIndividuos
     * @return Lista con los individuos seleccionados
     */
    public ArrayList<Integer> selecciona(Poblacion poblacion, int nIndividuos);
}
