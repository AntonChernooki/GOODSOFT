import java.io.IOException;
import java.nio.file.Path;

public interface Copy {
public  void copy(Path source, Path destination) throws IOException;
}
