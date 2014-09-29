
package clasificadores;

import datos.Datos;
import java.util.ArrayList;
import particionado.DivisionPorcentual;
import particionado.EstrategiaParticionado;
import particionado.Particion;
import particionado.ValidacionCruzada;

abstract public class Clasificador {
    
    //Métodos abstractos que se implementan en cada clasificador concreto
    abstract public void entrenamiento (Datos datosTrain);
    abstract public ArrayList<Integer> clasifica (Datos datosTest);

    // Obtiene el numero de aciertos y errores para calcular la tasa de fallo
    public double error (Datos datos, Clasificador clas) 
    {
       ArrayList<Integer> clases = this.clasifica(datos);
       //Aqui se compara con clases reales y se calcula el error
       return 1;
    }

    // Realiza una clasificacion utilizando una estrategia de particionado determinada
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
        
        Datos d = Datos.cargaDeFichero(args[0]);
        System.out.print(d.toString());
        System.out.print("\n\n");
        EstrategiaParticionado part = new DivisionPorcentual();
        ArrayList<Particion> particion = part.crearParticiones(150, 50);
        Datos train = d.extraeDatosTrain(particion.get(0));
        Datos test = d.extraeDatosTest(particion.get(0));
        System.out.print(train.toString());
        System.out.print("\n\n" + test.toString());
        Clasificador c = new ClasificadorNaiveBayes();
        c.entrenamiento(train);
        //c.clasifica(d);
        //ArrayList<Double> errores = Clasificador.validacion(part, d, c);
        // Se imprimen
    } 
}
