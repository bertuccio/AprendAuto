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
    public String getNombreEstrategiaParticionado() {
        return this.getClass().getName();
    }

    /**
     * Crea particiones segun el metodo de validación cruzada. El conjunto de
     * entrenamiento se crea con las numPart-1 particiones y el de test con la
     * partición restante.
     *
     * @param numDatos
     * @param numParticiones
     * @return
     */
    @Override
    public ArrayList<Particion> crearParticiones(int numDatos, int numParticiones) {

        ArrayList<Particion> particiones = new ArrayList<>();
        ArrayList<Integer> listDatos = new ArrayList<>();

        int tamanioParticion = numDatos / numParticiones;

        for (int i = 0; i < numDatos; i++)
            listDatos.add(i);
        

      //  int fromIndex = 0;
       // int toIndex = tamanioParticion;

        for (int i = 0; i < numParticiones; i++) {

            ArrayList<Integer> indicesTest =
                     (ArrayList<Integer>) listDatos.subList(i*tamanioParticion,
                            (tamanioParticion-1)*(i+1));

            /*for (Integer indx : listDatos) {

                if (!indicesTest.contains(indx)) 
                    indicesTrain.add(indx);
                

            }*/
            
            ArrayList<Integer> indicesTrain = new ArrayList<>(listDatos);
            
            indicesTrain.removeAll(indicesTest);

            Particion particion = new Particion(indicesTrain, indicesTest);
            particiones.add(particion);

          //  fromIndex += tamanioParticion;
          //  toIndex += tamanioParticion;

        }

        return particiones;

    }
}
