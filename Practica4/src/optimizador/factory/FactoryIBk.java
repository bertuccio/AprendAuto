/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimizador.factory;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;

/**
 *
 * @author pinwi
 */
public class FactoryIBk extends Factory{

    @Override
    public Classifier makeProduct() {
        return new IBk();
    }
    
}
