package Models;

import Interface.FileCopyStrategy;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class NioFileCopyStrategy implements FileCopyStrategy {

    @Override
    public void copy(Path source, Path destination) throws IOException {
        if (destination.getParent()!=null){
            Files.createDirectories(destination.getParent());
        }
        try (FileChannel sourceChannel=FileChannel.open(source, StandardOpenOption.READ);
             FileChannel destinationChannel = FileChannel.open(destination,StandardOpenOption.CREATE,StandardOpenOption.WRITE)) {

            long transfer=0;
            long size = sourceChannel.size();
            while (transfer<size) {
                 transfer += sourceChannel.transferTo(transfer, size-transfer, destinationChannel);
            }
        }
        System.out.println("Копирование завершено");

    }

    }
