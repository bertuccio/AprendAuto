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
public interface Evaluador {
   	
    /*Funcion generica que otorgara una puntuacion (estimacion de bueno/malo) expresado en numeros reales positivos*/
    public double puntua(Individuo objeto, ArrayList<MuestraGenetica> entornoEvolutivo);
}
