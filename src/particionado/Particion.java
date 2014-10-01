/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package particionado;

import java.util.ArrayList;
/**
 * Modela particiones a través de indices de entrenamiento y test.
 * 
 * @author Adrián Lorenzo Mateo
 */
public class Particion 
{
    private ArrayList<Integer> indicesTrain;
    private ArrayList<Integer> indicesTest;
    
    
    public Particion(ArrayList<Integer> indTrain, ArrayList<Integer> indTest) 
    {
        indicesTrain = indTrain;
        indicesTest = indTest;
        
    }

    public ArrayList<Integer> getIndicesTrain() {
        return indicesTrain;
    }

    public ArrayList<Integer> getIndicesTest() {
        return indicesTest;
    }
    
    
}