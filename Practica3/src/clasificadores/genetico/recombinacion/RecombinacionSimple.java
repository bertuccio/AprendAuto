/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package clasificadores.genetico.recombinacion;

import clasificadores.genetico.UtilesGenetico;
import clasificadores.genetico.poblacion.individuo.Individuo;
import clasificadores.genetico.poblacion.individuo.Regla;
import java.util.ArrayList;

/**
 *
 * @author temporal
 */
public class RecombinacionSimple implements Recombinacion {
    
        /*Funcion que recombina dos individuos,
    Salida: Una lista con los individuos nuevos.
    */
    @Override
    public ArrayList<Individuo> recombina(Individuo a, Individuo b){
        ArrayList<Individuo> res = new ArrayList<>();
        
        ArrayList<Regla> reglasA = new ArrayList<>();
        ArrayList<Regla> reglasB = new ArrayList<>();
        
        int indexCorteA = UtilesGenetico.randomNumber(a.getReglas().size()-1);
        int indexCorteB = UtilesGenetico.randomNumber(b.getReglas().size()-1);
        
        ArrayList<Regla> recombinacionReglas = this.recombinaReglas(a.getReglas().get(indexCorteA), b.getReglas().get(indexCorteB));
        
        /*Formacion de reglas del individuo A*/
        for(int i=0; i < indexCorteA;i++){
            reglasA.add(a.getReglas().get(i));
        }
        reglasA.add(recombinacionReglas.get(0));
        for(int i=indexCorteB; i < b.getReglas().size();i++){
            reglasA.add(b.getReglas().get(i));
        }
        
        /*Formacion de reglas del individuo B*/
        for(int i=0; i < indexCorteB;i++){
            reglasB.add(b.getReglas().get(i));
        }
        reglasB.add(recombinacionReglas.get(1));
        for(int i=indexCorteA; i < a.getReglas().size();i++){
            reglasB.add(a.getReglas().get(i));
        }
        
        Individuo a1 = new Individuo(a.getNumGensRegla(),reglasA);
        Individuo b1 = new Individuo(b.getNumGensRegla(),reglasB);
        
        res.add(0,a1);
        res.add(1,b1);
        
        return res;
    }
    
    /*A partir de dos reglas genera otras dos.
        La primera contiene la clase de a y la mayor parte de sus genes
        La segunda contiene la clase de b y la mayor parte de sus genes
    */
    public ArrayList<Regla> recombinaReglas(Regla a, Regla b){
        ArrayList<Regla> res = new ArrayList<>();
        int indexCorte = UtilesGenetico.randomNumber(a.getnAtributos()*a.getRangoAtributos() - 1);
    
        int tmp1[] = new int[a.getnAtributos()*a.getRangoAtributos()];
        int tmp2[] = new int[a.getnAtributos()*a.getRangoAtributos()];

        if (indexCorte > (a.getnAtributos()*a.getRangoAtributos())/2){
            /*Cromosoma de la primera regla*/
            System.arraycopy(a.getCromosoma(), 0, tmp1, 0, indexCorte);
            System.arraycopy(b.getCromosoma(), indexCorte, tmp1, indexCorte, (b.getnAtributos()*b.getRangoAtributos()) - indexCorte);
            /*Cromosoma de la segunda regla*/
            System.arraycopy(b.getCromosoma(), 0, tmp2, 0, indexCorte);
            System.arraycopy(a.getCromosoma(), indexCorte, tmp2, indexCorte, (a.getnAtributos()*a.getRangoAtributos()) - indexCorte);
        }else{
            /*Cromosoma de la segunda regla*/
            System.arraycopy(a.getCromosoma(), 0, tmp2, 0, indexCorte);
            System.arraycopy(b.getCromosoma(), indexCorte, tmp2, indexCorte, (b.getnAtributos()*b.getRangoAtributos()) - indexCorte);
            
            /*Cromosoma de la primera regla*/
            System.arraycopy(b.getCromosoma(), 0, tmp1, 0, indexCorte);
            System.arraycopy(a.getCromosoma(), indexCorte, tmp1, indexCorte, (a.getnAtributos()*a.getRangoAtributos()) - indexCorte);            
        }
        
        Regla a1 = new Regla(a.getnClases(), tmp1, a.getnAtributos(), a.getRangoAtributos(), a.getnClases());
        Regla b1 = new Regla(b.getnClases(), tmp2, b.getnAtributos(), b.getRangoAtributos(), b.getnClases());
        res.add(0,a1);
        res.add(1, b1);
        return res;
    }
    
}
