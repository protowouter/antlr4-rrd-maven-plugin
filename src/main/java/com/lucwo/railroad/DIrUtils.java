package com.lucwo.railroad;

import org.apache.maven.plugin.logging.Log;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class DIrUtils {

    /**
     * Removes all files recursively and prints errors to the provided Log instance.
     *
     * @param path Directory from which every file will be deleted
     * @param log  Log instance for printing logs
     */
    public static void removeRecursive(Path path, Log log) {
        final Log logger = log;
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        logger.error(String.format("Failed to delete file %s", file.toString()));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    // try to delete the file anyway, even if its attributes
                    // could not be read, since delete-only access is
                    // theoretically possible
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        logger.error(String.format("Failed to delete file %s", file.toString()));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    if (exc == null) {
                        try {
                            Files.delete(dir);
                        } catch (IOException e) {
                            logger.error(String.format("Failed to delete directory %s", dir.toString()));
                        }
                        return FileVisitResult.CONTINUE;
                    } else {
                        // directory iteration failed; print error message
                        logger.error(String.format("Failed to process directory %s", dir.toString()));
                        return FileVisitResult.CONTINUE;
                    }
                }
            });
        } catch (IOException e) {
            log.error(String.format("Unexpected error occurred while deleting directory %s", path.toString()), e);
        }
    }
}
