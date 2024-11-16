import java.math.BigInteger;
import java.util.Random;
import java.util.StringTokenizer;

public class Test2 {

    // Messaggio di esempio da cifrare
    public static String message = "ciao";
    private static final String DELIMITER = ";";

    public static void main(String[] args) {
        rsaEncrypt();
    }

    private static void rsaEncrypt() {
        Random rng = new Random();

        // Generazione di due numeri primi casuali
        BigInteger p = BigInteger.probablePrime(16, rng); // Numero primo 'p' di 16 bit
        BigInteger q = BigInteger.probablePrime(16, rng); // Numero primo 'q' di 16 bit

        // Calcolo di n = p * q (modulo pubblico)
        BigInteger n = p.multiply(q);

        // Calcolo di φ(n) = (p-1) * (q-1) (totiente di Eulero)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Generazione di 'e' coprimo con φ(n)
        BigInteger e = new BigInteger("65537"); // valore comunemente usato per 'e'
        while (!phi.gcd(e).equals(BigInteger.ONE)) { // Assicurarsi che e e phi siano coprimi
            e = e.add(BigInteger.TWO); // prova con il prossimo numero dispari
        }

        // Calcolo della chiave privata d, dove d * e ≡ 1 (mod φ(n))
        BigInteger d = e.modInverse(phi);

        // Stampa delle chiavi generate
        System.out.println("p: " + p + ", q: " + q);
        System.out.println("n: " + n + ", phi: " + phi);
        System.out.println("Chiave pubblica (e, n): (" + e + ", " + n + ")");
        System.out.println("Chiave privata (d, n): (" + d + ", " + n + ")");

        // Crittografia del messaggio
        String encryptedMessage = encrypt(message, e, n);
        System.out.println("Messaggio cifrato: " + encryptedMessage);

        // Decrittografia del messaggio
        String decryptedMessage = decrypt(encryptedMessage, d, n);
        System.out.println("Messaggio decifrato: " + decryptedMessage);
    }

    // Metodo di crittografia
    private static String encrypt(String message, BigInteger e, BigInteger n) {
        StringBuilder encrypted = new StringBuilder();

        for (char ch : message.toCharArray()) {
            BigInteger m = BigInteger.valueOf((int) ch);
            BigInteger c = m.modPow(e, n); // Cifra m con c = m^e mod n
            encrypted.append(c).append(DELIMITER);
        }

        return encrypted.toString();
    }

    // Metodo di decrittografia
    private static String decrypt(String encryptedMessage, BigInteger d, BigInteger n) {
        StringTokenizer tokenizer = new StringTokenizer(encryptedMessage, DELIMITER);
        StringBuilder decrypted = new StringBuilder();

        while (tokenizer.hasMoreTokens()) {
            BigInteger c = new BigInteger(tokenizer.nextToken());
            BigInteger m = c.modPow(d, n); // Decifra c con m = c^d mod n
            decrypted.append((char) m.intValue());
        }

        return decrypted.toString();
    }
}
