import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {
    private static final String DELIMITER = ";";

    public static void keyGenerate() {
        Logger logger = Logger.getLogger(index.class.getName());
        Random rng = new Random();

        BigInteger p = BigInteger.probablePrime(16, rng);
        BigInteger q = BigInteger.probablePrime(16, rng);
        BigInteger n = p.multiply(q);
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = new BigInteger("65537");
        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.valueOf(2));
        }
        BigInteger d = e.modInverse(phi);

        logger.log(Level.INFO,"p: " + p + ", q: " + q);
        logger.log(Level.INFO,"n: " + n + ", phi: " + phi);
        logger.log(Level.INFO,"Chiave pubblica (e, n): (" + e + ", " + n + ")");
        logger.log(Level.INFO,"Chiave privata (d, n): (" + d + ", " + n + ")");
    }

    public static String encrypt(String message, BigInteger e, BigInteger n) {
        StringBuilder encrypted = new StringBuilder();
        for (char ch : message.toCharArray()) {
            BigInteger m = BigInteger.valueOf((int) ch);
            BigInteger c = m.modPow(e, n);
            encrypted.append(c).append(DELIMITER);
        }
        return encrypted.toString();
    }

    public static String decrypt(String encryptedMessage, BigInteger d, BigInteger n) {
        StringTokenizer tokenizer = new StringTokenizer(encryptedMessage, DELIMITER);
        StringBuilder decrypted = new StringBuilder();
        while (tokenizer.hasMoreTokens()) {
            BigInteger c = new BigInteger(tokenizer.nextToken());
            BigInteger m = c.modPow(d, n);
            decrypted.append((char) m.intValue());
        }
        return decrypted.toString();
    }
}
