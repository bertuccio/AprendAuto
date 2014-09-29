/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import datos.Diccionario;
import java.util.ArrayList;

/**
 *
 * @author pinwi
 */
public class ClasificadorNaiveBayes extends Clasificador{

    private ArrayList<Double> apriori = new ArrayList<>();
    private double aposteriori[][];
    
    @Override
    public void entrenamiento(Datos datosTrain) {

        getApriori(datosTrain);
        getAPosteriori(datosTrain);
        
    }

    private void getApriori(Datos datosTrain) {
        
        int numCategorias = Diccionario.getInstance().getDiccionario().size();
        int numTuplas = datosTrain.getDatos().length;
        
        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("Class");
                
        
        for(int numClass=0; numClass<numCategorias; numClass++){
            
            double aprioriCounter= 0;
            
            for(int indexTuplas=0;indexTuplas<numTuplas;indexTuplas++){
                
                double classElement = datosTrain.getDatos()[indexTuplas][indexClass];
                
                if(numClass == classElement)
                    aprioriCounter++;
                
            }
            //LAPLACE
            if(aprioriCounter == 0)
                aprioriCounter++;
            
            aprioriCounter = aprioriCounter/numTuplas;
            
            apriori.add(aprioriCounter);
            
        }
    }
    
    private void getAPosteriori(Datos datosTrain){
        
        int numCategorias = Diccionario.getInstance().getDiccionario().size();
        int numInstancias = datosTrain.getDatos().length;
        
        /*obtiene el indice de la clase dentro de la matriz de datos*/
        int indexClass = datosTrain.getCategorias().indexOf("Class");
        
        aposteriori= new double[numCategorias][datosTrain.getCategorias().size()];
        
    }
    
    
    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        
        return null;
    }
    
}
