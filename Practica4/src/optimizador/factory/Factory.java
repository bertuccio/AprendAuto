package optimizador.factory;

import weka.classifiers.Classifier;

/**
 * Clase abstracta factoria de clasificadores
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
*/ 
public abstract class Factory {
    
    public abstract Classifier makeProduct();
    
}
