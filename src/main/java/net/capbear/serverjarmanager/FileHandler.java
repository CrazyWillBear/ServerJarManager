package net.capbear.serverjarmanager;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {
    public static boolean needsUpdate() throws Exception {
        if (!Util.downloadFile("https://serverjars.com/api/fetchLatest/" + ApplicationConfig.serverType, "test-latest.json")) {
            System.out.println("Failed to download test-latest.json");
            return false;
        }

        File file = new File("current-server.json");
        if (!file.exists()) {
            System.out.println("current-server.json does not exist, creating and updating...");
            replaceCurrentServer(false);
            return true;
        }


        if (!Files.readString(Paths.get("test-latest.json")).equals(Files.readString(Paths.get("current-server.json")))) {
            replaceCurrentServer(true);
            return true;
        }

        return false;
    }

    public static void replaceCurrentServer(boolean deleteCurrent) throws Exception {
        if (deleteCurrent) {
            deleteCurrentServer();
        }
        Files.move(Paths.get("test-latest.json"), Paths.get("current-server.json"));
        deleteTestLatest();
    }

    public static void deleteTestLatest() {
        File testLatestFile = new File("test-latest.json");
        testLatestFile.delete();
    }

    public static void deleteCurrentServer() {
        File currentServerFile = new File("current-server.json");
        currentServerFile.delete();
    }
}