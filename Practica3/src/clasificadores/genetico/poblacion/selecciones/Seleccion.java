package clasificadores.genetico.poblacion.selecciones;

import clasificadores.genetico.poblacion.individuo.Individuo;
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
     * @param maxIndividuos
     * @param entorno
     * @return Lista con los individuos seleccionados
     */
    public ArrayList<Individuo> selecciona(ArrayList<Individuo> poblacion, int maxIndividuos,int[][] entorno);
}
