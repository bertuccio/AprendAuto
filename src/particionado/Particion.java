package particionado;

import java.util.ArrayList;
/**
 * Modela particiones a través de indices de entrenamiento y test.
 * 
 * @author Adrián Lorenzo Mateo
 * @author Andres Ruiz Carrasco
 */
public class Particion 
{
    private ArrayList<Integer> indicesTrain;
    private ArrayList<Integer> indicesTest;
    
    /**
     *
     * @param indTrain
     * @param indTest
     */
    public Particion(ArrayList<Integer> indTrain, ArrayList<Integer> indTest) 
    {
        indicesTrain = indTrain;
        indicesTest = indTest;
        
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getIndicesTrain() {
        return indicesTrain;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getIndicesTest() {
        return indicesTest;
    }
    
    
}