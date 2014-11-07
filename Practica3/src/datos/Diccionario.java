

package datos;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 *  Almacena una correspondencia entre una elemento nominal (tanto atributo,
 *  como clase) y un identificador mediante una tabla hash. 
 *  Diseñado como Singleton.
 * 
 * @author Adrián Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class Diccionario {
    
    private static Diccionario miDiccionario;
    private  HashMap<String,Integer> clases; //diccionario de clases
    private  HashMap<String,Integer> atributos; //diccionario de atributos
    
    private Diccionario() { 
    
            clases = new HashMap<>();
            atributos = new HashMap<>();
    }
    
    /**
     *
     * @return
     */
    public static Diccionario getInstance(){
        
        if(miDiccionario == null)
            miDiccionario = new Diccionario();
        
        return miDiccionario;
        
    }

    /**
     *
     * @return
     */
    public  HashMap<String,Integer> getDiccionarioClases() {
        return clases;
    }
    
    /**
     *
     * @return
     */
    public  HashMap<String,Integer> getDiccionarioAtributos() {
        return atributos;
    }
    
    /**
     * 
     * Obtiene una clave del diccionario dado un valor (correspondiencia 1:1).
     * 
     * @param <T>
     * @param <E>
     * @param map
     * @param value
     * @return 
     */
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    
}
