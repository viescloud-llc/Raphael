package com.vincent.inc.raphael.util;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

public class TTSServices {

    private TTSServices() {

    }

    public static byte[] getBlobAsBytes(SerialBlob wav) throws SerialException {
        int blobLength = (int) wav.length();  
        byte[] blobAsBytes = wav.getBytes(1, blobLength);
        return blobAsBytes;
    }

    public static String preProcessingText(String text) {
        text = text.trim();
        text = text.toLowerCase();
        text = text.replaceAll(" +", " ");
        text = text.replaceAll("<|>|:|\\\"|/|\\\\|\\?|\\*|\\||\\+", "");
        text = text.replaceAll("\\s+", " ");

        return text;
    }
}
