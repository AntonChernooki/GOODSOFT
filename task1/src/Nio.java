import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Nio  implements Copy {

    @Override
    public void copy(Path source, Path destination) throws IOException {
        if (destination.getParent()!=null){
            Files.createDirectories(destination.getParent());
        }
        try (FileChannel sourceChannel=FileChannel.open(source, StandardOpenOption.READ);
             FileChannel destinationChannel = FileChannel.open(destination,StandardOpenOption.CREATE,StandardOpenOption.WRITE)) {

            long size = sourceChannel.size();
            long transfer = sourceChannel.transferTo(0, size, destinationChannel);

        }
        System.out.println("Копирование завершено");

    }

    }
