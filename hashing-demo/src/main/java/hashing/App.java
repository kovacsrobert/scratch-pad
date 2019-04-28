package hashing;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.StringUtils.EMPTY;

public class App {

    private static final String FILE_NAME = "/tmp/kyro.example";

    public static void main(String[] args) {

        String originalText = random(1000, true, false);
        String decryptedText = EMPTY;

        System.out.println("originalText: " + originalText);

        Kryo kryo = new Kryo();
        kryo.register(String.class);

        try (Output output = new Output(new FileOutputStream(new File(FILE_NAME)))) {
            kryo.writeObject(output, originalText);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Input input = new Input(new FileInputStream(new File(FILE_NAME)))) {
            decryptedText = kryo.readObject(input, String.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("decryptedText: " + decryptedText);

        System.out.print("Equals? " + originalText.equals(decryptedText));
    }
}
