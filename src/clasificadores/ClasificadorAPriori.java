


package clasificadores;

import clasificadores.Clasificador;
import datos.Datos;
import java.util.ArrayList;

/**
 * 
 * Clasificador mediante el método de a priori.
 * 
 * @author Adrián Lorenzo Mateo
 */
public class ClasificadorAPriori extends Clasificador {
    
    /**
     * 
     * Busco la clase mayoritaria de los datos y la guardo.
     * 
     * @param datostrain 
     */
    @Override
    public void entrenamiento(Datos datostrain) 
    {
        
    }
    /**
     * 
     * 
     * @param datos
     * @return 
     */
    @Override
    public ArrayList<Integer> clasifica(Datos datos) 
    {
        // Asigno la clase mayoritaria a todos los datos
        return null;
    }
}