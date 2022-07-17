package net.capbear.serverjarmanager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
    public static boolean downloadFile(String url, String output) throws Exception {
        URL fetchWebsite = new URL(url);
        ReadableByteChannel readableByteChannel = Channels.newChannel(fetchWebsite.openStream());

        try (FileOutputStream fos = new FileOutputStream(output)) {
            fos.getChannel().transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public static void deleteOldestFileinDir(String dir) throws Exception {
        Path path = Path.of(dir);
        List<Path> files = getSortedFilesByDateCreated(path, ".tar.gz", true);
        Files.delete(files.get(0));
    }

    public static List<Path> getSortedFilesByDateCreated(Path dir, String targetExtensionCouldBeNull,
                                                         boolean ascendingOrder) {
        try {
            Comparator<Path> pathComparator = Comparator.comparingLong(p -> getFileCreationEpoch((p).toFile()));
            return Files.list(dir)
                    .filter(Files::isRegularFile)
                    .filter(p -> targetExtensionCouldBeNull == null || p.getFileName().toString()
                            .endsWith(targetExtensionCouldBeNull))
                    .sorted(ascendingOrder? pathComparator :
                            pathComparator.reversed())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static long getFileCreationEpoch(File file) {
        try {
            BasicFileAttributes attr = Files.readAttributes(file.toPath(),
                    BasicFileAttributes.class);
            return attr.creationTime()
                    .toInstant().toEpochMilli();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
