package com.haoisou.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
    public SerializeUtil() {
    }

    public static byte[] serialize(Object object) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        byte[] bytes = null;

        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            bytes = baos.toByteArray();
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            try {
                oos.flush();
                oos.close();
                baos.flush();
                baos.close();
            } catch (Exception var12) {
                var12.printStackTrace();
            }

        }

        return bytes;
    }

    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = null;
        Object read = null;

        try {
            ois = new ObjectInputStream(bais);
            read = ois.readObject();
        } catch (Exception var13) {
            var13.printStackTrace();
        } finally {
            try {
                ois.close();
                bais.close();
            } catch (Exception var12) {
                var12.printStackTrace();
            }

        }

        return read;
    }
}
