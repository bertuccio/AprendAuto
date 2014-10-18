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
    
    /*Auxiliar classes*/
    private class KNNelement{
        private Double clas;
        
        public KNNelement(Double clas, Double euclideanDistance) {
            this.clas = clas;
            this.euclideanDistance = euclideanDistance;
        }
       
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
    
    /*Element comparator*/
    private class KNNComparator implements Comparator<KNNelement>{
        @Override
        public int compare(KNNelement o1, KNNelement o2) {
            Double res =  o2.euclideanDistance - o1.euclideanDistance;
            return (res.intValue());
        }
    }
    
    /*Private Atributes*/
    private double datosEntrenamiento[][];
    private int kNN = 1;    
    private PriorityQueue<KNNelement> heapNeigh;
    private double[] mediasAtributos;
    private double[] desviacionesTipicasAtributos;  
    
    public void setkNN(int kNN) {
        this.kNN = kNN;
    }

    /*Private methods*/
    /*Normalize value*/
    private double normalizeValue(int index,double value){
        return (value - this.mediasAtributos[index])/this.desviacionesTipicasAtributos[index];
    }
    /*Get max class probability*/
    private Integer getMaxClassProbability(PriorityQueue<KNNelement> heapNeigh){
        HashMap<Integer,Integer> classes = new HashMap<>();
        Map.Entry<Integer,Integer> maxEntry = null;
        for(KNNelement node : heapNeigh){
            if(classes.containsKey(node.clas.intValue())){
                classes.put(node.clas.intValue(), classes.get(node.clas.intValue()) + 1);
            }else{
                classes.put(node.clas.intValue(), 1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : classes.entrySet()){
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0){
                maxEntry = entry;
            }
        }
        if (maxEntry != null)
            return maxEntry.getKey();
        return -1;
    }        
    
    /*Public methods*/
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

    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        ArrayList<Integer> res = new ArrayList<>();
        
        for(double instanciaTest[] : datosTest.getDatos()){
            this.heapNeigh = new PriorityQueue<>(this.kNN, new KNNComparator());
            
            for(double instanciaTrain[] : datosEntrenamiento){
                double distanciaEuclidea = 0;
                
                for(int i=0; i<instanciaTest.length; i++){
                    distanciaEuclidea += Math.pow((normalizeValue(i,instanciaTest[i]) - normalizeValue(i,instanciaTrain[i])),2);
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
            }
            
            //in this point the head of the heap is the maximun euclidean distance of K neighbors
            //Extratcion of the max probability class
            res.add(getMaxClassProbability(this.heapNeigh));
            /*Resources free*/
            this.heapNeigh.clear();
        }
        return res;
    }
}
