package cloud.Utils;

import java.security.*;
import java.util.Random;

public class RandomGenerator {

    public static byte[] randomBytes(byte[] bytes) {
        try {
            SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG");
            secureRandomGenerator.nextBytes(bytes);

            return bytes;
        }
        catch(NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    public static int randomInt (int min, int max) {
        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;
        
        return randomNum;
    }
}
