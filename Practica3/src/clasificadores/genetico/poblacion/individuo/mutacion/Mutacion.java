/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificadores.genetico.poblacion.individuo.mutacion;

import clasificadores.genetico.poblacion.individuo.Individuo;

/**
 *
 * @author Andres Ruiz
 */
public interface Mutacion {

    public Individuo muta(Individuo mutante);
    
}
