package clasificadores.genetico;

import java.util.Random;

/**
 *
 * @author Andres Ruiz Carrasco
 */
public class UtilesGenetico {
    
    public static int coinDrop() {
        Random rg = new Random();
        int bin = rg.nextInt(2);
        return bin;

    }
    
    public static int randomNumber(int n) {
        Random rg = new Random();
        int num = rg.nextInt(n);
        return num;
    } 
}
