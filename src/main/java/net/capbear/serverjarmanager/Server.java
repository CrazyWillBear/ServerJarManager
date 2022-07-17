// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at http://mozilla.org/MPL/2.0/.

package net.capbear.serverjarmanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Server {
    private static OutputStream os;
    private static Process p;

    public static void start() throws Exception {
        p = Runtime.getRuntime().exec(ApplicationConfig.startCmd);

        new Thread(new Runnable() {
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                String line = null;

                try {
                    while ((line = input.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        os = p.getOutputStream();
    }

    public static void closeProgramFollowingServer() {
        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (!Server.isUp()) {
                        System.exit(2);
                    }
                }
            }
        }).start();
    }

    public static Thread restartServerFollowingServerClose() {
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (!Server.isUp()) {
                        try {
                            Backups.backupServer();
                            start();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        thread.start();

        return thread;
    }

    public static void runCommand(String cmd) throws Exception {
        if (cmd.contains(System.lineSeparator())) {
            os.write(cmd.getBytes(StandardCharsets.UTF_8));
        }
        else {
            os.write((cmd + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
        }
        os.flush();
    }

    public static boolean isUp() {
        if (!p.isAlive()) {
            return false;
        }

        return true;
    }

    public static void stop() throws Exception {
        os.write(("stop" + System.lineSeparator()).getBytes(StandardCharsets.UTF_8));
        os.flush();
    }
}
