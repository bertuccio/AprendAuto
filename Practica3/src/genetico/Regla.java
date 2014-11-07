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
public class Regla {
    
    private int nClases;
    private int nAtributos;
    private int rangoAtributos;
    private int[] cromosoma;
    private int clase = 0;
    
    public Regla(int nClases,int nAtributos,int rangoAtributos){
        this.cromosoma = new int[nAtributos*rangoAtributos];
        this.nAtributos = nAtributos;
        this.rangoAtributos = rangoAtributos;
        this.nClases = nClases;
        for (int i = 0; i < nAtributos*rangoAtributos; i++){
            this.cromosoma[i] = UtilesGenetico.coinDrop();
        }
        this.clase = UtilesGenetico.randomNumber(nClases);
    }
    
    public int evaluate(int muestra[]){
        ArrayList<Integer> punterosGen = new ArrayList<Integer>();
        
        for (int i = 0; i < this.nAtributos; i++){
            punterosGen.add(i*rangoAtributos);
        }
        
        int z = 0;
        for (int i : punterosGen){
            for (int j = i; (i - j) < rangoAtributos; i++){
                if(this.cromosoma[muestra[z]] == 1){
                    
                }
                    
            }
            z++;
        }
    }
    
}
