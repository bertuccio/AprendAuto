package optimizador.factory;

import static optimizador.genetico.UtilesGenetico.randomRangedNumber;
import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;

/**
 * Instanciacion de factoria para la creacion de un clasificador IBK aleatorio
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
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
