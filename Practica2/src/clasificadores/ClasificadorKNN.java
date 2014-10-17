/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clasificadores;

import datos.Datos;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;


/**
 * 
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class ClasificadorKNN extends Clasificador{
    
    private double[] mediasAtributos;
    private double[] desviacionesTipicasAtributos;
    
    
    private class KNNelement{

        public KNNelement(Double clas, Double euclideanDistance) {
            this.clas = clas;
            this.euclideanDistance = euclideanDistance;
        }
        
        private Double clas;

        public Double getClas() {
            return clas;
        }

        public void setClas(Double clas) {
            this.clas = clas;
        }

        public Double getEuclideanDistance() {
            return euclideanDistance;
        }

        public void setEuclideanDistance(Double euclideanDistance) {
            this.euclideanDistance = euclideanDistance;
        }
        private Double euclideanDistance;
    }
    
    /*Comparador de elementos*/
    private class KNNComparator implements Comparator<KNNelement>{
        @Override
        public int compare(KNNelement o1, KNNelement o2) {
            Double res =  o2.euclideanDistance - o1.euclideanDistance;
            return (res.intValue());
        }
    }

    
    private double datosEntrenamiento[][];
    private int kNN = 1;    
    private PriorityQueue<KNNelement> heapNeigh = new PriorityQueue<>(this.kNN, new KNNComparator());
    
    
    public void setkNN(int kNN) {
        this.kNN = kNN;
    }

    /**
     * 
     * @param datosTrain 
     */
    @Override
    public void entrenamiento(Datos datosTrain) {
    
        int tamanioAtributos = datosTrain.getCategorias().size()-1;
        
        datosEntrenamiento = datosTrain.getDatos();
        mediasAtributos = new double[tamanioAtributos];
        desviacionesTipicasAtributos = new double[tamanioAtributos];
        
        for(int i=0 ;i<tamanioAtributos; i++){
            
            for(double[] dato : datosEntrenamiento){

                mediasAtributos[i] += dato[i];  
            }
        }
        for(int i=0 ;i<tamanioAtributos; i++)
            mediasAtributos[i] /= datosTrain.getDatos().length;
        
        for (int i = 0; i < tamanioAtributos; i++) {

            for (double[] dato : datosEntrenamiento) {
                    
                desviacionesTipicasAtributos[i] += Math.pow((dato[i] - mediasAtributos[i]),2); 
            }
        }
        for (int i = 0; i < tamanioAtributos; i++) {
            desviacionesTipicasAtributos[i] /= datosTrain.getDatos().length;
        }
       

    }

    /**
     * 
     * @param datosTest
     * @return 
     */
    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        ArrayList<Integer> res = new ArrayList<>();
        
        for(double instanciaTest[] : datosTest.getDatos()){
            
            for(double instanciaTrain[] : datosEntrenamiento){
                double distanciaEuclidea = 0;
                
                for(int i=0; i<instanciaTest.length; i++){
                    distanciaEuclidea += Math.pow((instanciaTest[i] - instanciaTrain[i]),2);
                }
                
                distanciaEuclidea = Math.sqrt(distanciaEuclidea);
                if (heapNeigh.size() == this.kNN) {
                    if (heapNeigh.peek().euclideanDistance > distanciaEuclidea){
                        heapNeigh.poll();
                        heapNeigh.offer(new KNNelement(distanciaEuclidea,instanciaTrain[datosTest.getCategorias().size()-1]));
                    }
                } else {
                    heapNeigh.offer(new KNNelement(distanciaEuclidea,instanciaTrain[datosTest.getCategorias().size()-1]));
                }                
                //TOD
                //distancias.add(distanciaEuclidea);
            }
            
            //TODO
            ArrayList<Double> distanciasAuxiliar = new ArrayList<>(distancias);
            Collections.sort(distanciasAuxiliar);
            
            HashMap<Double,Double> mapaKNN = new HashMap<>();
            
            for(int i=0; i<kNN; i++){
                
                /*obtiene el indice en la matrix de los knn con menor distancia*/
                int indice = distancias.indexOf(distanciasAuxiliar.get(i));
                double clase = datosEntrenamiento[indice][datosTest.getCategorias().indexOf("class")];
                
                if(mapaKNN.containsKey(clase))
                    mapaKNN.put(clase, mapaKNN.get(clase)+1);
              
                else
                    mapaKNN.put(clase, 1.0);

            }
            for (Double clase : mapaKNN.keySet()) {
                
                mapaKNN.put(clase, mapaKNN.get(clase)/(double)kNN);
                
            }
            
             /**
             * Obtiene la mayor probabilidad de todas. Devuelve la key
             * que corresponde al identificador de la clase con mayor prob.
             */           
            Double maxKey = null; 
            Double maxValue = -1.0; 
            
            if (!mapaKNN.entrySet().isEmpty()) {
                for (Map.Entry<Double, Double> entry : mapaKNN.entrySet()) {
                    if (entry.getValue().isNaN()) {
                        entry.setValue(0.0);
                    }
                    if (entry.getValue().isInfinite()) {
                        entry.setValue(Double.MAX_VALUE);
                    }
                    if (entry.getValue() >= maxValue) {
                        maxValue = entry.getValue();
                        maxKey = entry.getKey();
                    }
                }
                res.add(maxKey.intValue());

            }
        }
        
        return res;
    
    }
    
}
