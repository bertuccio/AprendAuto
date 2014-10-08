
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
            if(prediccion.get(i).equals(real.get(i)))
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
     * @param nParticion
     * @return 
     */
    public static ArrayList<Double> validacion(EstrategiaParticionado part, Datos datos, 
        Clasificador clas, Integer nParticion) {
        ArrayList<Double> res = new ArrayList<>();
        ArrayList<Particion> particiones = part.crearParticiones(datos.getDatos().length, nParticion);
        for(Particion idx : particiones){                
            Datos train = datos.extraeDatosTrain(idx);
            Datos test = datos.extraeDatosTest(idx);
            clas.entrenamiento(train);
            res.add(clas.error(test, clas));
        }
        return res;
    }

    public static void main(String[] args) {

        String inputFile = "input";
        Integer particion = 5;

        EstrategiaParticionado part = new DivisionPorcentual();
        Clasificador clasificador = new ClasificadorNaiveBayes();

        for (int i = 0; i < args.length; i++) {
            
            if (args[i].compareTo("-input") == 0) {
                
                inputFile = args[i + 1];
                i++;
            }
            if (args[i].compareTo("-cruzada") == 0) {

                part = new ValidacionCruzada();

            }
            if (args[i].compareTo("-laplace") == 0) {
                
                ((ClasificadorNaiveBayes) clasificador).setLAPLACE_FLAG(true);
            }
            if (args[i].compareTo("-particion") == 0) {
                
                particion = Integer.parseInt(args[i + 1]);
                i++;
            }
            if (args[i].compareTo("-debug") == 0) {
                
                ((ClasificadorNaiveBayes) clasificador).setDEBUG_FLAG(true);
            }
        }
        Datos d = Datos.cargaDeFichero(inputFile);

        double totalError = 0;

        for (int i = 0; i < 100; i++) {

            double error = 0;
            ArrayList<Double> resultados = Clasificador.validacion(
                    part, d, clasificador, particion);

            for (Double resultado : resultados) {
                error += resultado;
            }

            error /= resultados.size();
            totalError += error;
        }
        totalError /= 100;

        System.out.println(totalError);
    }
}
