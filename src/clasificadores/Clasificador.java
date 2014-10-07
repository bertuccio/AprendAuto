
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
 * @author Andres Ruiz Carraco
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
           real.add(Double.valueOf(sample[datos.getCategorias().indexOf("class")]).intValue());
        }
        //System.out.println(prediccion);
        //System.out.println(real);
        for (int i = 0; i < prediccion.size(); i++){
            if(/*prediccion.get(i)!= null &&*/ prediccion.get(i).equals(real.get(i)))
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

//    public static void main(String []args) {
//          TU MAIN !!! QUE PONGO EL MIO PRARA PROBAR TODO DE GOLPE
//        //Datos d = Datos.cargaDeFichero("car.data");
////        
//        ////System.out.print(d.toString());
//        ////System.out.print("\n\n");
////        
//        //EstrategiaParticionado part = new ValidacionCruzada();
////        
////        
//        //double totalError = 0;
//        //ArrayList<Particion> particiones =
//         //part.crearParticiones(d.getDatos().length, 3);        
//        //for(int i=0; i<100; i++){            
//            //double error=0;
//            //for(Particion idx : particiones){                
//                //Datos train = d.extraeDatosTrain(idx);
//                //Datos test = d.extraeDatosTest(idx);
//                ////System.out.println("TRAIN\n");
//                ////System.out.print(train.toString());
//                ////System.out.print("\n\nTEST\n" + test.toString());
//                //Clasificador c = new ClasificadorNaiveBayes();
//                //c.entrenamiento(train);
//                ////System.out.print(c.clasifica(test));
//                ////System.out.println(c.error(test, c));
//                //error += c.error(test, c);
//            //}
//            //error /= particiones.size();
//            //totalError += error;
//        //}
//        //totalError /= 100;
////        
//        //System.out.println(totalError);
//    //} 
   public static void main(String []args) {
        
        //Datos d = Datos.cargaDeFichero(args[0]);
        /*Para no tener que configurar el netBeans BORRRAR!!!!!*/
        
        String inputFile = "input";
        Integer particion = 5;
        
        EstrategiaParticionado part = new DivisionPorcentual();
        Clasificador c = new ClasificadorNaiveBayes();
        
        for (int i = 0; i < args.length; i++) {
            if (args[i].compareTo("-input") == 0) {
                inputFile = args[i + 1];
                i++;
            }
            if (args[i].compareTo("-cruzada") == 0) {
                
                part = new ValidacionCruzada();

            }
            if (args[i].compareTo("-laplace") == 0) {
                System.out.println("OJETE");
                ((ClasificadorNaiveBayes) c).setLAPLACE_FLAG(true);
            }
            if (args[i].compareTo("-particion") == 0) {
                particion = Integer.parseInt(args[i + 1]);
                i++;
            }
        }
        Datos d = Datos.cargaDeFichero(inputFile);
        
        //System.out.print(d.toString());
        //System.out.print("\n\n");
        
        double totalError = 0;
        ArrayList<Particion> particiones = part.crearParticiones(d.getDatos().length, particion);
        
       //for(int i=0; i<100; i++){
            
            double error=0;
            for(Particion idx : particiones){
                
                Datos train = d.extraeDatosTrain(idx);
                Datos test = d.extraeDatosTest(idx);
                System.out.println("TRAIN\n");
                System.out.print(train.toString());
                //System.out.print("\n\nTEST\n" + test.toString());
                
                c.entrenamiento(train);
                //System.out.print(c.clasifica(test));
                System.out.println(c.error(test, c));
                error += c.error(test, c);
            }
            error /= particiones.size();
            //System.out.println(error);
            totalError += error;
        //}
        totalError /= 100;
        
        System.out.println(totalError);
    } 
}
