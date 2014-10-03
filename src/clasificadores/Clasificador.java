
package clasificadores;

import datos.Datos;
import java.util.ArrayList;
import particionado.DivisionPorcentual;
import particionado.EstrategiaParticionado;
import particionado.Particion;
import particionado.ValidacionCruzada;

/**
 * 
 * Utiliza  una  estrategia  de  particionado  determinada para  llevar  a 
 * cabo  su  algoritmo  de  clasificación,  empleando  el  conjunto  de  
 * entrenamiento  para construir  el  modelo  y  el  conjunto  de  prueba  
 * para  evaluarlo.
 * 
 * @author Adrián Lorenzo Mateo
 */
abstract public class Clasificador {
    
    //Métodos abstractos que se implementan en cada clasificador concreto
    abstract public void entrenamiento (Datos datosTrain);
    abstract public ArrayList<Integer> clasifica (Datos datosTest);

    /**
     * 
     * Obtiene el numero de aciertos y errores para calcular la tasa de fallo.
     * 
     * @param datos
     * @param clas
     * @return 
     */
    public double error (Datos datos, Clasificador clas) {
        ArrayList<Integer> prediccion = clas.clasifica(datos);
        ArrayList<Integer> real = new ArrayList<>();
       
        int count = 0;
       
        for(double[] sample : datos.getDatos()){
           real.add(Double.valueOf(sample[datos.getCategorias().indexOf("Class")]).intValue());
        }
        System.out.println(prediccion);
        System.out.println(real);
        for (int i = 0; i < prediccion.size(); i++){
           if (prediccion.get(i).equals(real.get(i)))
               count++;
        }
        /*Aciertos / totales*/
        return ((double)count/(double)prediccion.size())*100;
    }

    /**
     * 
     * Realiza una clasificacion utilizando una estrategia de particionado determinada.
     * 
     * @param part
     * @param datos
     * @param clas
     * @return 
     */
    public static ArrayList<Double> validacion(EstrategiaParticionado part, Datos datos, 
        Clasificador clas) {

        //Creamos las particiones siguiendo la estrategia llamando a datos.creaParticiones
        //Para validación cruzada: En un bucle hasta nv entrenamos el clasf con la particion
        // de train i (extraerDatosTrain)
        // y obtenemos el error en la particion test de i (extraerDatosTest)
        //Para validación porcentual entrenamos el clasf con la partición de train 
        //(extraerDatosTrain) y 
        // obtenemos el error en la particion test (extraerDatosTest)
        return null;
    }

    public static void main(String []args) {
        
        //Datos d = Datos.cargaDeFichero(args[0]);
        /*Para no tener que configurar el netBeans BORRRAR!!!!!*/
        Datos d = Datos.cargaDeFichero("iris.data");
        
        System.out.print(d.toString());
        System.out.print("\n\n");
        
        EstrategiaParticionado part = new ValidacionCruzada();
        
        for(Particion idx : part.crearParticiones(150, 5)){
            Datos train = d.extraeDatosTrain(idx);
            Datos test = d.extraeDatosTest(idx);
            //System.out.println("TRAIN\n");
            //System.out.print(train.toString());
            //System.out.print("\n\nTEST\n" + test.toString());
            Clasificador c = new ClasificadorNaiveBayes();
            c.entrenamiento(train);
            //System.out.print(c.clasifica(test));
            System.out.print(c.error(test, c));
        }
    } 
}
