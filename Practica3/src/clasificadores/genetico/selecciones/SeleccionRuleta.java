package clasificadores.genetico.selecciones;

import clasificadores.genetico.UtilesGenetico;
import clasificadores.genetico.poblacion.Poblacion;
import java.util.ArrayList;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public class SeleccionRuleta implements Seleccion {
    
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
    
    @Override
    public ArrayList<Integer> selecciona(Poblacion poblacion, int nIndividuos){
        ArrayList<Integer> indexIndividuos = new ArrayList<>();
        double random,acumRuleta = 0;
        
        /*Ordenamos el conjuto de poblacion*/
        poblacion.sort();
        
        for (int i = 0; i<nIndividuos;){
            /*Tirada de 0-99*/
            random=UtilesGenetico.randomNumber(100);
            acumRuleta = 0;
            for (int j = 0; j<poblacion.getnIndividuos();j++){
                acumRuleta += poblacion.getIndividuos()[j].getScore() * 100 / poblacion.getSumScores();
                if (!indexIndividuos.contains(j) && acumRuleta > random){
                    indexIndividuos.add(j);
                    i++;
                    /*Aseguramos la salida del bucle*/
                    j = poblacion.getnIndividuos();
                }
            } 
        }
        
        return indexIndividuos;
    }
}
