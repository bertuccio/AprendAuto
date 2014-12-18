package optimizador.genetico.seleccion;


import java.util.ArrayList;
import optimizador.genetico.Poblacion;
import optimizador.genetico.UtilesGenetico;

/**
 * Implementacion de la Seleccion por ruleta
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
*/ 
public class SeleccionRuleta implements Seleccion {
    
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
