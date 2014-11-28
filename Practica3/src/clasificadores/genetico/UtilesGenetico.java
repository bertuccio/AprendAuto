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
        if (n <= 0) return 0;
        Random rg = new Random();
        int num = rg.nextInt(n+1);
        return num;
    } 
}
