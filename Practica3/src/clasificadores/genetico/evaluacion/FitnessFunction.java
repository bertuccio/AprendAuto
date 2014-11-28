/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores.genetico.evaluacion;

import clasificadores.genetico.MuestraGenetica;
import clasificadores.genetico.poblacion.individuo.Individuo;
import java.util.ArrayList;

/**
 *
 * @author Andres
 */
public class FitnessFunction implements Evaluador {
    
    /*Funcion que puntua al individuo segun el porcentajes de acierto en un conjunto dado*/
    @Override
    public double puntua(Individuo objeto, ArrayList<MuestraGenetica> entornoEvolutivo) {
    	int aciertos=0,totales=0;
        int res[];
    	for (MuestraGenetica muestraGenetica : entornoEvolutivo) {
    		totales++;
    		res = objeto.evaluate(muestraGenetica.getArgs());
                if(res[0] == muestraGenetica.getClase()) 
                    aciertos++;
    	}
        return aciertos*100.0/totales;
    }
}
