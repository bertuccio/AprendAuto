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

    /**
     *
     * @param datosTrain
     */
        abstract public void entrenamiento (Datos datosTrain);

    /**
     *
     * @param datosTest
     * @return
     */
    abstract public ArrayList<Integer> clasifica (Datos datosTest);

    /**
     * 
     * Obtiene el numero de aciertos y errores para calcular la tasa de fallo.
     * 
     * @param datos coleccion de datos a clasificar
     * @param clas clasificador a utilizar
     * @return porcentaje de acierto
     * @see datos.Datos
     * @see clasificadores.Clasificador
     * @see clasificadores.ClasificadorNaiveBayes
     */
    public double error (Datos datos, Clasificador clas) {
        ArrayList<Integer> prediccion = clas.clasifica(datos);
        ArrayList<Integer> real = new ArrayList<>();
       
        int count = 0;
       
        for(double[] sample : datos.getDatos()){
           real.add(Double.valueOf(sample[datos.getCategorias().indexOf("class")]).intValue());
        }

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
     * @param part particionador a utilizar
     * @param datos coleccion de datos para entrenar/clasificar
     * @param clas clasificacion a utilizar
     * @param nParticion Parametro de configuracion (En validacion cruzada numero de particiones) 
     *                                              (En division porcentual porcentaje de aprendizaje)
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

        /**
     * 
     * Main de para realizar las pruebas necesarias:
     * USO:
     *  Parametros disponibles:
     *      -input file: entrada de datos
     *      -cruzada: particionado con validacion cruzada
     *      -partition: particionado porcentual
     *      -laplace: utilizar suavizado de laplace
     *      -debug: activar trazas de debug          
     * 
     * @param args cadena de opciones "Clasificador.jar -input file -cruzada|-partition [-laplace] [-debug]"
     */
    public static void main(String[] args) {

        String inputFile = "input";
        Integer particion = 5;

        EstrategiaParticionado part = new DivisionPorcentual();
        Clasificador clasificador = new ClasificadorKNN();

        for (int i = 0; i < args.length; i++) {
            
            if (args[i].compareTo("-input") == 0) {
                
                inputFile = args[i + 1];
                i++;
            }
            if (args[i].compareTo("-cruzada") == 0) {

                part = new ValidacionCruzada();

            }
            if (args[i].compareTo("-particion") == 0) {
                
                particion = Integer.parseInt(args[i + 1]);
                i++;
            }
            if (args[i].compareTo("-K") == 0) {
                
                ((ClasificadorKNN) clasificador).setkNN(Integer.parseInt(args[i + 1]));
                i++;
            }
        }
        Datos d = Datos.cargaDeFichero(inputFile);

                
        double totalError = 0;

        //for (int i = 0; i < 100; i++) {

            double error = 0;
            ArrayList<Double> resultados = Clasificador.validacion(
                    part, d, clasificador, particion);

            for (Double resultado : resultados) {
                error += resultado;
            }

            error /= resultados.size();
            totalError += error;
        //}
        //totalError /= 100;

        System.out.println(totalError);
    }
}
