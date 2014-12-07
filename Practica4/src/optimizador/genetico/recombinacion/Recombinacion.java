package optimizador.genetico.recombinacion;

import optimizador.genetico.Individuo;
import java.util.ArrayList;
import optimizador.factory.Factory;

/**
 *
 * @author Andres Ruiz Carrasco
 * @author Adrian Lorenzo Mateo
 */
public interface Recombinacion {

    /*Funcion que recombina dos individuos,
    Salida: Una lista con los individuos nuevos.
    */
    public ArrayList<Individuo> recombina(Individuo a, Individuo b, Factory factory);
}
