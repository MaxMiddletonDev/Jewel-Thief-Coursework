package cs230.group29se.jewelthief.Persistence.Storage;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
// For getting data from JSON files

/**
 * raw text to IO.
 * @author Iyaad
 * @version 1.0
 */
public class FileStore {
    private final Path baseDir;

    public FileStore(Path baseDir) {
        this.baseDir = baseDir;
        ensureDir(baseDir);
    }

    public String read(String path) {
        try { return Files.readString(resolve(path)); }
        catch (IOException e) { throw new RuntimeException("read failed: " + path, e); }
    }

    public void write(String path, String json) {
        try {
            var p = resolve(path);
            Files.createDirectories(p.getParent());
            Files.writeString(p, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) { throw new RuntimeException("write failed: " + path, e); }
    }

    public boolean exists(String path) {
        return Files.exists(resolve(path));
    }

    public List<String> list(String dir) {
        try (var s = Files.list(resolve(dir))) {
            return s.map(p -> baseDir.relativize(p).toString()).toList();
        } catch (IOException e) { throw new RuntimeException("list failed: " + dir, e); }
    }

    public void delete(String path) {
        try { Files.deleteIfExists(resolve(path)); }
        catch (IOException e) { throw new RuntimeException("delete failed: " + path, e); }
    }

    private Path resolve(String relative) { return baseDir.resolve(relative.replace("\\", "/")); }
    private static void ensureDir(Path d) { try { Files.createDirectories(d); } catch (IOException e) { throw new RuntimeException(e); } }
}