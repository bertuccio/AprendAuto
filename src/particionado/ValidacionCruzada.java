
package particionado;

import java.util.ArrayList;
import java.util.Scanner;

public class ValidacionCruzada implements EstrategiaParticionado {
    
    /**
     * Devuelve el nombre de la estrategia de particionado (Validación Cruzada).
     * 
     * @return 
     */
    @Override
    public String getNombreEstrategiaParticionado () {
        return this.getClass().getName();
    }
    
    
    /**
     * Crea particiones segun el metodo de validación cruzada. 
     * El conjunto de entrenamiento se crea con las numPart-1 particiones 
     * y el de test con la partición restante.
     * 
     * @param numDatos
     * @param numParticiones
     * @return 
     */
    @Override
    public ArrayList<Particion> crearParticiones(int numDatos, int numParticiones) {
        
        ArrayList<Particion> particiones = new ArrayList<Particion>();
        
        
 
        
        return particiones;
        
        
    }
}