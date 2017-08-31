package com.xavier.commom;

import java.io.IOException;
import java.io.InputStream;

public class IOUtils {

    public static void closeQuietly(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
