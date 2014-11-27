/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificadores.genetico.poblacion.individuo.mutacion;

import clasificadores.genetico.UtilesGenetico;
import clasificadores.genetico.poblacion.individuo.Individuo;

/**
 *
 * @author Andres Ruiz
 */
public class MutacionClase implements Mutacion{

    @Override
    public Individuo muta(Individuo mutante) {
        mutante.getReglas().get(UtilesGenetico.randomNumber(mutante.getReglas().size()-1)).changeClass();
        mutante.setMutated(1);
        return mutante;
    }
    
}
