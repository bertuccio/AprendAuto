package clasificadores;

import datos.Datos;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import particionado.DivisionPorcentual;
import particionado.EstrategiaParticionado;
import particionado.ValidacionCruzada;


/**
 * Clasificador que utiliza un modelo KNN
 * 
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class ClasificadorKNN extends Clasificador{
    
    /*Auxiliar classes*/
    private class KNNelement{
        private Double clase;
        
        public KNNelement(Double clas, Double euclideanDistance) {
            this.clase = clas;
            this.euclideanDistance = euclideanDistance;
        }
       
        public Double getClase() {
            return clase;
        }

        public void setClase(Double clase) {
            this.clase = clase;
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
            
            return o2.euclideanDistance.compareTo(o1.euclideanDistance);
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
            if(classes.containsKey(node.clase.intValue())){
                classes.put(node.clase.intValue(), classes.get(node.clase.intValue()) + 1);
            }else{
                classes.put(node.clase.intValue(), 1);
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
            desviacionesTipicasAtributos[i] /= (datosTrain.getDatos().length -1);
            desviacionesTipicasAtributos[i] = Math.sqrt(desviacionesTipicasAtributos[i]);
        }
        
       

    }

    @Override
    public ArrayList<Integer> clasifica(Datos datosTest) {
        
        ArrayList<Integer> res = new ArrayList<>();
        
        for(double instanciaTest[] : datosTest.getDatos()){
                       
            heapNeigh = new PriorityQueue<>(this.kNN, new KNNComparator());
            
            for(double instanciaTrain[] : datosEntrenamiento){
                double distanciaEuclidea = 0;
                
                for(int i=0; i<instanciaTest.length-1; i++){
                    distanciaEuclidea += Math.pow((normalizeValue(i,instanciaTest[i]) - normalizeValue(i,instanciaTrain[i])),2);
                }
                
                distanciaEuclidea = Math.sqrt(distanciaEuclidea);
                if (heapNeigh.size() == this.kNN) {
                    if (heapNeigh.peek().euclideanDistance > distanciaEuclidea){
                        heapNeigh.poll();
                        heapNeigh.offer(new KNNelement(instanciaTrain[datosTest.getCategorias().size()-1],distanciaEuclidea));
                    }
                } else {
                    heapNeigh.offer(new KNNelement(instanciaTrain[datosTest.getCategorias().size()-1],distanciaEuclidea));
                }  
            }
            
            //in this point the head of the heap is the maximun euclidean distance of K neighbors
            //Extratcion of the max probability class
            res.add(getMaxClassProbability(heapNeigh));
            /*Resources free*/
            this.heapNeigh.clear();
        }
        return res;
    }



    /**
     * 
     * Main de uso del clasificador KNN:
     * USO:
     *  Parametros disponibles:
     *      -input file: entrada de datos
     *      -cruzada: particionado con validacion cruzada
     *      -partition: particionado porcentual
     *      -laplace: utilizar suavizado de laplace
     *      -debug: activar trazas de debug          
     * 
     * @param args cadena de opciones "-input file [-cruzada|-partition] -K KNN [-debug]"
     */
    public static void main(String[] args) {

        String inputFile = "input";
        Integer particion = 5;

        EstrategiaParticionado part = new DivisionPorcentual();
        Clasificador clasificador = new ClasificadorKNN();

        for (int i = 0; i < args.length; i++) {
            
            if (args[i].compareTo("-input") == 0) {
                
                inputFile = args[i + 1];
                i++;
            }
            if (args[i].compareTo("-cruzada") == 0) {

                part = new ValidacionCruzada();

            }
            if (args[i].compareTo("-particion") == 0) {
                
                particion = Integer.parseInt(args[i + 1]);
                i++;
            }
            if (args[i].compareTo("-K") == 0) {
                
                ((ClasificadorKNN) clasificador).setkNN(Integer.parseInt(args[i + 1]));
                i++;
            }
        }
        Datos d = Datos.cargaDeFichero(inputFile);

        double error = 0;
        ArrayList<Double> resultados = Clasificador.validacion(
                part, d, clasificador, particion);
        for (Double resultado : resultados) {
            error += resultado;
        }
        error /= resultados.size();
        
        System.out.println(error);
    }
}
