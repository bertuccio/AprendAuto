package optimizador.data;

import java.util.Random;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Clase que modela el conjunto de datos
 * @author Adrian Lorenzo Mateo
 * @author Andres Ruiz Carrasco
*/ 
public class Datos {

    /**
    *   Conjunto de datos
    */ 
    private Instances data;

    /**
     * Constructor de clase. Lee el conjunto de datos de un fichero y lo guarda
     * en memoria.
     * @author Adrian Lorenzo Mateo
     * @author Andres Ruiz Carrasco
     * @param path Ruta al fichero que contiene el conjunto de datos
     * @throws java.lang.Exception Excepcion generica no controlada
    */ 
    public Datos(String path) throws Exception {
        Random rnd = new Random(System.currentTimeMillis());
        data = ConverterUtils.DataSource.read(path);
        data.setClassIndex(data.numAttributes() - 1);
        data.randomize(rnd);
    }

    public Instances getData() {
        return data;
    }
}