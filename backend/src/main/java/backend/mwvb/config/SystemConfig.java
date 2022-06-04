package backend.mwvb.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.regex.Pattern;

@Configuration
public class SystemConfig {

    public static void main(String[] args) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        String privateData = "secret-data";
        encryptor.setPassword("some-random-passwprd");
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");

        String encryptedText = encryptor.encrypt(privateData);
        String plainText = encryptor.decrypt(encryptedText);
        System.out.println(encryptedText);
        System.out.println(plainText);
    }
}
