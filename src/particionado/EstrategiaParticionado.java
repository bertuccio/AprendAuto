

package particionado;

import java.util.ArrayList;

/**
 * 
 * @author pinwi
 */
public interface EstrategiaParticionado {
    
    /**
     * 
     * Devuelve el nombre de la estrategia de particionado.
     * 
     * @return 
     */
    public String getNombreEstrategiaParticionado();
    
    
    /**
     * Crea particiones segun la estrategia de particionado. 
     * 
     * @param numDatos
     * @param numParticiones
     * @return 
     */
    public ArrayList<Particion> crearParticiones(int numDatos, int numParticiones);
}