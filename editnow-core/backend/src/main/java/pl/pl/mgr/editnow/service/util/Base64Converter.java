package pl.pl.mgr.editnow.service.util;

import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class Base64Converter implements ImageConverter<String> {


    @Override
    public byte[] encode(String data) {
        return Base64.getEncoder().encode(data.getBytes());
    }

    @Override
    public byte[] encode(byte[] dataBytes) {
        return Base64.getEncoder().encode(dataBytes);
    }

    @Override
    public String decode(byte[] encodedData) {
        byte[] decodedDataBytes = Base64.getDecoder().decode(encodedData);

        return new String(decodedDataBytes, StandardCharsets.UTF_8);
    }
}
