/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package optimizador.data;

import java.util.Random;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 *
 * @author pinwi
 */
public class Datos {
    
    private Instances data;

    public Datos(String path) throws Exception {
        
        Random rnd = new Random(System.currentTimeMillis());
        data = ConverterUtils.DataSource.read(path);
        data.setClassIndex(data.numAttributes() - 1);      // The class is the last datum of each line
        data.randomize(rnd);
    }

    public Instances getData() {
        return data;
    }
    
    
    
    
    
    

    
    
}
