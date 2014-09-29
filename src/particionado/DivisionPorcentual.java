
package particionado;

import java.util.ArrayList;

/**
 * s
 * @author pinwi
 */
public class DivisionPorcentual implements EstrategiaParticionado{
    
    
    //private static final Random rand = new Random();
    
    /**
     * Devuelve el nombre de la estrategia de particionado 
     * (División Porcentual).
     * 
     * @return 
     */
    @Override
    public String getNombreEstrategiaParticionado() {
        return this.getClass().getName();
    }
    
    /**
     * 
     *  Crea particiones segun el metodo tradicional. 
     *  El conjunto de entrenamiento se crea con un porcentaje y el de test con 
     *  el restante.
     * 
     * @param numDatos
     * @param porcentaje
     * @return 
     */
    @Override
    public ArrayList<Particion> crearParticiones(int numDatos,
                int porcentaje) {
        
        ArrayList<Particion> particiones = new ArrayList<>();
        
        ArrayList<Integer> indicesTest = new ArrayList<>();
        ArrayList<Integer> indicesTrain = new ArrayList<>();
        
        int tamanioParticion = (int) (numDatos*porcentaje*0.01);
        
        /*INDICES VAN DE 0 a numDatos-1!!*/
        for(int i=0; i<tamanioParticion; i++)
            indicesTrain.add(i);
        
        for(int i= tamanioParticion; i<numDatos; i++)
            indicesTest.add(i);
        
        particiones.add(new Particion(indicesTrain,indicesTest));
        
        return particiones;
    }
    
}
