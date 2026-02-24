package Interface;

import java.io.IOException;
import java.nio.file.Path;

public interface FileCopyStrategy {
public  void copy(Path source, Path destination) throws IOException;
}
