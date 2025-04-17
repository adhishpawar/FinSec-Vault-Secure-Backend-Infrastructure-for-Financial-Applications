package com.adhish.FinSec.DBCore.converter;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Converter
public class AseEncryptor implements AttributeConverter<String, String>{

    private static final String SECRET = "MySecretKey12345"; // 16-char key (AES-128)
    private static final String ALGO = "AES";

    @Override
    public String convertToDatabaseColumn(String attribute){
        if (attribute == null) {
            return null;  // <-- prevent null pointer exception
        }
        try{
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(SECRET.getBytes(),ALGO));
            return Base64.getEncoder().encodeToString(cipher.doFinal(attribute.getBytes()));
        } catch (Exception e) {
        System.out.println("Encryption failed: " + e.getMessage());
        throw new RuntimeException("Error encrypting attribute", e);
    }

}

    @Override
    public String convertToEntityAttribute(String dbData){
        if (dbData == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(SECRET.getBytes(), ALGO));
            return new String(cipher.doFinal(Base64.getDecoder().decode(dbData)));
        } catch (Exception e) {
            System.out.println("Encryption failed: " + e.getMessage());
            throw new RuntimeException("Error encrypting attribute", e);
        }

    }

}



