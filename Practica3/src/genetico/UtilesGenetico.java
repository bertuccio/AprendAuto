package genetico;

import java.util.Random;

/**
 *
 * @author temporal
 */
public class UtilesGenetico {
    
    public static int coinDrop() {
        Random rg = new Random();
        int bin = rg.nextInt(2);
        return bin;

    }
    
    public static int randomNumber(int n) {
        Random rg = new Random();
        int num = rg.nextInt(n+1);
        return num;
    }
    
}
