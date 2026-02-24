package Models;

import Interface.FileCopyStrategy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class Nio2FileCopyStrategy implements FileCopyStrategy {


    @Override
    public void copy(Path source, Path destination) throws IOException {
if (destination.getParent()!=null){
    Files.createDirectories(destination.getParent());
    }
        Files.copy(source,destination, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Копирование завершено");

    }
}
