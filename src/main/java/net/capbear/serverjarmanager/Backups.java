// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at http://mozilla.org/MPL/2.0/.

package net.capbear.serverjarmanager;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Backups {
    public static void backupServer() throws Exception {
        System.out.print("::Backing up server...");
        if (atLimit()) {
            Util.deleteOldestFileinDir("backups");
        }

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm-ss");
        LocalDateTime now = LocalDateTime.now();
        Process p = Runtime.getRuntime().exec("tar -czvf ./backups/" + dtf.format(now) + ".tar.gz ./");
        System.out.println("\r::Backed up server      ");
    }

    public static boolean atLimit() {
        File file= new File("backups");
        String[] ls = file.list();
        if (ls != null && ls.length >= ApplicationConfig.maxBackups) {
            return true;
        }
        return false;
    }
}
