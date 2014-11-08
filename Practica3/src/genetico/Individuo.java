/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package genetico;

import java.util.ArrayList;

/**
 *
 * @author temporal
 */
public class Individuo {
    
    private ArrayList<Regla> reglas = new ArrayList<Regla>();

    public ArrayList<Regla> getReglas() {
        return reglas;
    }

    public void setReglas(ArrayList<Regla> reglas) {
        this.reglas = reglas;
    }
    
    public Individuo(int nClases,int nAtributos,int rangoAtributos,int maxReglasIni){
        int nReglas = UtilesGenetico.randomNumber(maxReglasIni);
        for(int i = 0; i<nReglas;i++){
            this.reglas.add(new Regla(nClases,nAtributos,rangoAtributos));
        }
    }
    
    public int evaluate(int muestra[]){
        int claseActual;
        for (Regla reglaActual : this.reglas){
            claseActual = reglaActual.evaluate(muestra);
            if (claseActual != 0)
                return claseActual;
        }
        return 0;
    }

}
