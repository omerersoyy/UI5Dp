package com.ag.ui5.util;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MoveUI5Resources {
    public static void unzip(InputStream inputStream, String target) throws IOException {
        File targetDir = new File(target);
        if (!targetDir.exists()) {
            targetDir.mkdir();
        }
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        ZipEntry zipEntry = zipInputStream.getNextEntry();
        while (zipEntry != null) {
            String filePath = target + File.separator + zipEntry.getName();
            if (!zipEntry.isDirectory()) {
                extract(zipInputStream, filePath);
            } else {
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipInputStream.closeEntry();
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }

    private static void extract(ZipInputStream zipInputStream,String filePath) throws IOException {
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesInput = new byte[4096];
        int read = 0;
        while ((read = zipInputStream.read(bytesInput)) != -1) {
            bufferedOutputStream.write(bytesInput, 0, read);
        }
        bufferedOutputStream.close();
    }
}
