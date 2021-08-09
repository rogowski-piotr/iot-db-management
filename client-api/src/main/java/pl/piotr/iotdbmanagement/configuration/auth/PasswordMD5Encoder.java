package pl.piotr.iotdbmanagement.configuration.auth;

import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordMD5Encoder implements PasswordEncoder {

    @SneakyThrows
    private String encodeMD5(CharSequence charSequence) {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
        byte[] array = md.digest(charSequence.toString().getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : array) {
            sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    @Override
    public String encode(CharSequence charSequence) {
        return encodeMD5(charSequence);
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        String hash = encodeMD5(charSequence);
        return hash.equals(s);
    }
}
