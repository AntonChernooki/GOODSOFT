package Models;

import Interface.FileCopyStrategy;

import java.io.*;
import java.nio.file.Path;

public class IoFileCopyStrategy implements FileCopyStrategy {
private final int BUFFER_SIZE=32768;

    @Override
    public void copy(Path source, Path destination) throws IOException {
        File dest = destination.toFile();
        if (dest.getParentFile() != null) {
            dest.getParentFile().mkdirs();
        }

        try (InputStream inputStream=new FileInputStream(source.toFile());
             OutputStream outputStream=new FileOutputStream(destination.toFile())){
byte[] buffer = new byte[BUFFER_SIZE];
int bytesRead;
while ((bytesRead = inputStream.read(buffer)) != -1){
    outputStream.write(buffer,0,bytesRead);
}

        }

        System.out.println("Копирование завершено");
    }
}