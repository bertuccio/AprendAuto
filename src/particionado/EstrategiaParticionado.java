

package particionado;

import java.util.ArrayList;

/**
 * 
 * A través de esta interfaz se permite separar la estrategia 
 * de  particionado  del  particionado  en  sí  mismo.
 * 
 * @author Adrián Lorenzo Mateo
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