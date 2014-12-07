/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package practica4;
import java.util.ArrayList;
import optimizador.data.Datos;
import optimizador.factory.Factory;
import optimizador.factory.FactoryPerceptron;
import optimizador.genetico.*;
import optimizador.genetico.mutacion.Mutacion;
import optimizador.genetico.mutacion.MutacionGen;
import optimizador.genetico.recombinacion.Recombinacion;
import optimizador.genetico.recombinacion.RecombinacionSimple;
import optimizador.genetico.seleccion.Seleccion;
import optimizador.genetico.seleccion.SeleccionRuleta;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;

/**
 *
 * @author e185826
 */
public class Practica4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        Datos data = new Datos("data/credit-g.arff");
        Factory factoryPerceptron = new FactoryPerceptron();
        
        Entorno e = new Entorno(10,0.01,0.6,new FitnessFunction(),
            data.getData(), factoryPerceptron);
        //Poblacion pob = new Poblacion(3, factoryPerceptron);
        ArrayList<Recombinacion> recombinaciones = new ArrayList<>();
        recombinaciones.add(new RecombinacionSimple());
        
        e.setCruces(recombinaciones);
        
        ArrayList<Mutacion> mutaciones = new ArrayList<>();
        mutaciones.add(new MutacionGen());
        
        e.setMutaciones(mutaciones);
        ArrayList<Seleccion> selecciones = new ArrayList<>();
        selecciones.add(new SeleccionRuleta());
        
        e.setSelecciones(selecciones);
//        pob.scoring(new FitnessFunction(), data.getData(), 3);
//        pob.sort();
//        
//        
//        System.out.println(pob.getIndividuos()[0].getScore());
//        System.out.println(pob.getIndividuos()[1].getScore());
//        System.out.println(pob.getIndividuos()[2].getScore());
//
        for(int i=0;i<3;i++){
             e.epoch();
            System.out.println(e.getKing());
        }
           
    }
    
}
