package cs230.group29se.jewelthief.Persistence.Storage;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

/**
 * A utility class for file storage operations.
 * Provides methods to read, write, delete, and list files and directories.
 * Ensures that the base directory exists and handles file paths relative to it.
 * <p>
 * Author: Iyaad
 * @version 1.0
 */
public class FileStore {
    private final Path baseDir; // The base directory for file operations

    /**
     * Constructs a FileStore with the specified base directory.
     * Ensures that the base directory exists.
     *
     * @param baseDir The base directory for file operations.
     */
    public FileStore(Path baseDir) {
        this.baseDir = baseDir;
        ensureDir(baseDir);
    }

    /**
     * Reads the content of a file as a string.
     *
     * @param path The relative path to the file.
     * @return The content of the file as a string.
     * @throws RuntimeException if the file cannot be read.
     */
    public String read(String path) {
        try { return Files.readString(resolve(path)); }
        catch (IOException e) { throw new RuntimeException("read failed: " + path, e); }
    }

    /**
     * Writes a string to a file. Creates the file and its parent directories if they do not exist.
     *
     * @param path The relative path to the file.
     * @param json The content to write to the file.
     * @throws RuntimeException if the file cannot be written.
     */
    public void write(String path, String json) {
        try {
            var p = resolve(path);
            Files.createDirectories(p.getParent());
            Files.writeString(p, json, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) { throw new RuntimeException("write failed: " + path, e); }
    }

    /**
     * Checks if a file or directory exists.
     *
     * @param path The relative path to the file or directory.
     * @return true if the file or directory exists, false otherwise.
     */
    public boolean exists(String path) {
        return Files.exists(resolve(path));
    }

    /**
     * Lists the files in a directory.
     *
     * @param dir The relative path to the directory.
     * @return A list of relative paths to the files in the directory.
     * @throws RuntimeException if the directory cannot be listed.
     */
    public List<String> list(String dir) {
        try (var s = Files.list(resolve(dir))) {
            return s.map(p -> baseDir.relativize(p).toString()).toList();
        } catch (IOException e) { throw new RuntimeException("list failed: " + dir, e); }
    }

    /**
     * Deletes a file if it exists.
     *
     * @param path The relative path to the file.
     * @throws RuntimeException if the file cannot be deleted.
     */
    public void delete(String path) {
        try { Files.deleteIfExists(resolve(path)); }
        catch (IOException e) { throw new RuntimeException("delete failed: " + path, e); }
    }

    /**
     * Deletes a directory and all its contents.
     *
     * @param dir The relative path to the directory.
     * @throws RuntimeException if the directory or its contents cannot be deleted.
     */
    public void deleteDirectory(String dir) {
        Path dirPath = resolve(dir);
        try {
            if (!Files.exists(dirPath)) return;

            // Delete children first, then the directory itself
            // walkFileTree handles nested directories if they exist.
            Files.walkFileTree(dirPath, new java.nio.file.SimpleFileVisitor<>() {
                @Override
                public java.nio.file.FileVisitResult visitFile(Path file, java.nio.file.attribute.BasicFileAttributes attrs)
                        throws IOException {
                    Files.deleteIfExists(file);
                    return java.nio.file.FileVisitResult.CONTINUE;
                }

                @Override
                public java.nio.file.FileVisitResult postVisitDirectory(Path dir, IOException exc)
                        throws IOException {
                    Files.deleteIfExists(dir);
                    return java.nio.file.FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("deleteDirectory failed: " + dir, e);
        }
    }

    /**
     * Gets the absolute path of the base directory.
     *
     * @return The absolute path of the base directory as a string.
     */
    public String getBaseDir() {
        return baseDir.toAbsolutePath().toString();
    }

    /**
     * Resolves a relative path against the base directory.
     *
     * @param relative The relative path to resolve.
     * @return The resolved absolute path.
     */
    private Path resolve(String relative) {
        return baseDir.resolve(relative.replace("\\", "/"));
    }

    /**
     * Ensures that a directory exists. Creates it if it does not exist.
     *
     * @param d The path to the directory.
     * @throws RuntimeException if the directory cannot be created.
     */
    private static void ensureDir(Path d) {
        try { Files.createDirectories(d); }
        catch (IOException e) { throw new RuntimeException(e); }
    }
}
