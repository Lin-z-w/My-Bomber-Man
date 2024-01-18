package lzw.networkio;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ObjectRW {
    public static Object readObject(String objectString) {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectString.getBytes(StandardCharsets.ISO_8859_1));
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException ignore) {
                }
            }
            try {
                byteArrayInputStream.close();
            } catch (IOException ignore) {
            }
        }
    }

    public static String writeObject(Object object) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(object);
            return bos.toString(StandardCharsets.ISO_8859_1.name());
        } catch (IOException e) {
            return null;
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

}
