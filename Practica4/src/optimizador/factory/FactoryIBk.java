/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimizador.factory;

import static optimizador.genetico.UtilesGenetico.randomRangedNumber;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;

/**
 *
 * @author pinwi
 */
public class FactoryIBk extends Factory{

    private final int k[] = {3, 5, 7, 9, 11, 13, 15, 17, 21, 25, 31, 35, };
    
    @Override
    public Classifier makeProduct() {
        
        IBk knn = new IBk();
        knn.setKNN(k[randomRangedNumber(0,k.length-1)]);
        
        return knn;
    }
    
}
