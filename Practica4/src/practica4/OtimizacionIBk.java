/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica4;
import java.util.ArrayList;
import optimizador.data.Datos;
import optimizador.factory.Factory;
import optimizador.factory.FactoryIBk;
import optimizador.genetico.*;
import optimizador.genetico.mutacion.Mutacion;
import optimizador.genetico.mutacion.MutacionGen;
import optimizador.genetico.recombinacion.Recombinacion;
import optimizador.genetico.recombinacion.RecombinacionSimple;
import optimizador.genetico.seleccion.Seleccion;
import optimizador.genetico.seleccion.SeleccionRuleta;
import weka.classifiers.Evaluation;
import weka.core.Instances;

/**
 *
 * @author e185826
 */
public class OtimizacionIBk {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
 
        
        ArrayList<Mutacion> mutaciones = new ArrayList<>();
        ArrayList<Individuo> bestOnes = new ArrayList<>();
        mutaciones.add(new MutacionGen());
        ArrayList<Seleccion> selecciones = new ArrayList<>();
        selecciones.add(new SeleccionRuleta());
        ArrayList<Recombinacion> recombinaciones = new ArrayList<>();
        recombinaciones.add(new RecombinacionSimple());
        
        Evaluador fitness = new FitnessFunction();
        
        FactoryIBk factoryIBk = new FactoryIBk();
        Individuo bestOne;

        int nFolds = 10;
        String inputFile = "input";
           
            
        for (int i = 0; i < args.length; i++) {

            if (args[i].compareTo("-input") == 0) {

                inputFile = args[i + 1];
                i++;
            }
            if (args[i].compareTo("-nFolds") == 0) {

                nFolds = Integer.parseInt(args[i + 1]);
                i++;
            }
        }
        Datos data = new Datos(inputFile);
//        Entorno e1 = new Entorno(10,0.01,0.6,new FitnessFunction(),
//            data.getData(), factoryPerceptron);
//        //Poblacion pob = new Poblacion(3, factoryPerceptron);     
//        e1.setCruces(recombinaciones);
//        e1.setMutaciones(mutaciones);
//        e1.setSelecciones(selecciones);
//        pob.scoring(new FitnessFunction(), data.getData(), 3);
//        pob.sort();
//        
//        
//        System.out.println(pob.getIndividuos()[0].getScore());
//        System.out.println(pob.getIndividuos()[1].getScore());
//        System.out.println(pob.getIndividuos()[2].getScore());
//        double error = 0;
   
        double best = Integer.MAX_VALUE;
        for (int fold = 0; fold < nFolds; fold++) {
            
            Instances training = data.getData().trainCV(nFolds, fold);
            Evaluation eval = new Evaluation(training);
            
            for(int fold2 = 0; fold2 < (nFolds-1); fold2++){
                
                Entorno entorno = new Entorno(100, 0.01, 0.6, fitness,
                training, factoryIBk, nFolds-1);
                entorno.setCruces(recombinaciones);
                entorno.setMutaciones(mutaciones);
                entorno.setSelecciones(selecciones);
                for(int i=0;i<100;i++){
                    entorno.epoch();
                }
                bestOnes.add(entorno.getKing());
            }
            
            Instances test = data.getData().testCV(nFolds, fold);
            for(Individuo i : bestOnes){
                eval.evaluateModel(i.getClasificador(), test);
                System.out.println(eval.errorRate());
                if(best>eval.errorRate()){
                    best = eval.errorRate();
                    bestOne = i;
                }
                    
            }
            System.out.println("-----------------");
            bestOnes.clear();
            //error += 
            //error += eval.errorRate();
        }
        System.out.println("<"+best+">");

    }      
}

