
package particionado;

import java.util.ArrayList;
import java.util.Random;

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
     * @param numParticiones
     * @return 
     */
    @Override
    public ArrayList<Particion> crearParticiones(int numDatos, 
            int numParticiones) {
    
        ArrayList<Particion> particiones = new ArrayList<>();
        
        ArrayList<Integer> indicesTest = new ArrayList<>();
        ArrayList<Integer> indicesTrain = new ArrayList<>();
        
        int tamanioParticion = (int) (numDatos*numParticiones*0.01);
        
        /* Opticional?: Obtiene los índices de cada partición de forma aleatoria
        para eliminar
            posibles sesgos.
          Generación de numeros aleatorios con rango [0:numDatos-1]. */
        //int randomNum = rand.nextInt(((numDatos-1) - 0) + 1) + 0;
        
        
        /*Con este metodo siempre van a ser los primeros datos train
        y los restantes test, PUEDEN EXISTIR SESGOS */
        
        /*INDICES VAN DE 0 a numDatos-1!!*/
        for(int i=0; i<tamanioParticion; i++)
            indicesTrain.add(i);
        
        for(int i= tamanioParticion; i<numDatos; i++)
            indicesTest.add(i);
        
        particiones.add(new Particion(indicesTrain,indicesTest));
        
        return particiones;
    }
    
}
