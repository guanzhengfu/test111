package com.example.test111.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

public class EncodeUtil {

  public static final String RSA_ALGORITHM = "RSA";

  @SneakyThrows
  public static String encrypt(String input, String encryptKey) {
    if (StringUtils.isBlank(input) || StringUtils.isBlank(encryptKey)) {
      return input;
    }

    Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
    Key publicKey = KeyFactory.getInstance(RSA_ALGORITHM)
        .generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(encryptKey)));
    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
    byte[] secret = cipher.doFinal(input.getBytes());

    return new String(Base64.getEncoder().encode(secret));
  }

  @SneakyThrows
  public static String decrypt(String input, String decryptKey) {
    if (StringUtils.isBlank(input) || StringUtils.isBlank(decryptKey)) {
      return input;
    }

    PrivateKey privateKey = KeyFactory.getInstance(RSA_ALGORITHM)
        .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(decryptKey)));

    Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
    cipher.init(Cipher.DECRYPT_MODE, privateKey);

    byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(input));
    return new String(plainText);
  }

}
