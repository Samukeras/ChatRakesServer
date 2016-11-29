package br.udesc.rakes.chat.common.utils;

public class ByteUtils {

    public static boolean getBit(byte b, int position) {
        int mask    = 1 << position,
            retorno = b & mask;

        return retorno != 0;
    }

    public static byte[] encrypt(String text) {
        byte[] inverse = new byte[text.getBytes().length];

        for(int i = 0; i < text.getBytes().length; i++) {
            inverse[i] = (byte) ~(int) (text.getBytes()[i]);
        }

        return inverse;
    }

    public static String decrypt(byte[] encrypted) {
        byte[] message = new byte[encrypted.length];

        for(int i = 0; i < encrypted.length; i++) {
            message[i] = (byte) ~(int) encrypted[i];
        }

        return new String(message);
    }

}