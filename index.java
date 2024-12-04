import java.math.BigInteger;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class index {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Logger logger = Logger.getLogger(index.class.getName());

        logger.log(Level.INFO,"Generazione chiavi:");
        Test.keyGenerate();

        logger.log(Level.INFO,"Cripta il messaggio::");
        logger.log(Level.INFO,"Dammi il messaggio:");
        String message = scanner.nextLine();
        logger.log(Level.INFO,"Dammi e:");
        int e = scanner.nextInt();
        logger.log(Level.INFO,"Dammi n:");
        int n = scanner.nextInt();
        logger.log(Level.INFO,"Criptaggio del messaggio:");
        String encryptedMessage = Test.encrypt(message, BigInteger.valueOf(e), BigInteger.valueOf(n));
        logger.log(Level.INFO,"Messaggio cifrato: " + encryptedMessage);

        logger.log(Level.INFO,"Decript del messaggio:");
        logger.log(Level.INFO,"Dammi d:");
        int d = scanner.nextInt();
        String decryptedMessage = Test.decrypt(encryptedMessage, BigInteger.valueOf(d), BigInteger.valueOf(n));
        logger.log(Level.INFO,"Messaggio decifrato: " + decryptedMessage);

        scanner.close();
    }
}
