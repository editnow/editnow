package pl.pl.mgr.editnow.service.util;

public interface ImageConverter<T> {

    byte[] encode(T data);
    byte[] encode(byte[] dataBytes);
    T decode(byte[] encodedData);

}
