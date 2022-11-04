package com.example.socify;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

public class AESEncryption {

    private SecretKey  key;
    private final int KEY_SIZE  = 128;
    private final int T_LEN  = 128;
    Cipher EncyptionCipher;

    public void init() throws Exception {
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(KEY_SIZE);
        key = generator.generateKey();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String encrypt(String message) {
        byte[] messageInBytes = message.getBytes();
        EncyptionCipher = null;
        try {
            EncyptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        try {
            EncyptionCipher.init(Cipher.ENCRYPT_MODE, key);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] EncrpytedBytes = new byte[0];
        try {
            EncrpytedBytes = EncyptionCipher.doFinal(messageInBytes);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
        }
        return encode(EncrpytedBytes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String decrypt(String encryptedMsg) throws Exception {

        byte [] messageInBytes = decode(encryptedMsg);
        Cipher decryptionCipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec spec = new GCMParameterSpec(T_LEN, EncyptionCipher.getIV());
        decryptionCipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] decryptedBytes = decryptionCipher.doFinal(messageInBytes);
        return new String(decryptedBytes);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private byte[] decode(String data) {
        return Base64.getDecoder().decode(data);
    }
}
