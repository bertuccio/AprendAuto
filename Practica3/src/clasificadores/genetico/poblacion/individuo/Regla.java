/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificadores.genetico.poblacion.individuo;

import clasificadores.genetico.UtilesGenetico;
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
    
    public Regla(int clase, int[] cromosoma, int nAtributos,int rangoAtributos,int nClases){
        this.cromosoma = cromosoma;
        this.clase = clase;
        this.nAtributos = nAtributos;
        this.rangoAtributos = rangoAtributos;
        this.nClases = nClases;
    }
    
    public int evaluate(double dMuestra[]){
        int muestra[] = new int[this.nAtributos];
        int j = 0;
        for (double d : dMuestra){
            muestra[j] = new Double(d).intValue();
            j++;
        }
            
        ArrayList<Integer> punterosGen = new ArrayList<>();
        
        for (int i = 0; i < this.nAtributos; i++){
            punterosGen.add(i*rangoAtributos);
        }
        
        /*La variable Z cuenta el atributo que estamos mirando AND */
        int z = 0;
        /*Hay tantos punteros Gen como Atributos*/
        for (int i : punterosGen){
            if(this.cromosoma[muestra[z]+i] != 1){
                return 0;
            }
            z++;
        }
        return this.clase;
    }
    
    public ArrayList<int[]> divide(int rupturaIndex){
        
        if ((rupturaIndex < 0) || (rupturaIndex > this.nAtributos*this.rangoAtributos))
                return null;
        
        int[] a = new int[rupturaIndex];
        int[] b = new int[(this.nAtributos*this.rangoAtributos) - rupturaIndex];
        
        System.arraycopy(this.cromosoma, 0, a, 0, rupturaIndex);
        System.arraycopy(this.cromosoma, rupturaIndex, b, 0, (this.nAtributos*this.rangoAtributos) - rupturaIndex);
        
        ArrayList<int[]> res = new ArrayList<>();
        
        res.add(a);
        res.add(b);
        return res;
    }
    
    public void changeGen(int index){
        this.cromosoma[index] = ((this.cromosoma[index] == 1) ? 0 : 1);
    }
    
    public void changeClass(){
        this.setClase(UtilesGenetico.randomNumber(this.nClases));
    }
    
    public int getnClases() {
        return nClases;
    }

    public void setnClases(int nClases) {
        this.nClases = nClases;
    }

    public int getnAtributos() {
        return nAtributos;
    }

    public void setnAtributos(int nAtributos) {
        this.nAtributos = nAtributos;
    }

    public int getRangoAtributos() {
        return rangoAtributos;
    }

    public void setRangoAtributos(int rangoAtributos) {
        this.rangoAtributos = rangoAtributos;
    }

    public int[] getCromosoma() {
        return cromosoma;
    }

    public void setCromosoma(int[] cromosoma) {
        this.cromosoma = cromosoma;
    }

    public int getClase() {
        return clase;
    }

    public void setClase(int clase) {
        this.clase = clase;
    }
}
