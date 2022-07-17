// This Source Code Form is subject to the terms of the Mozilla Public
// License, v. 2.0. If a copy of the MPL was not distributed with this
// file, You can obtain one at http://mozilla.org/MPL/2.0/.

package net.capbear.serverjarmanager;

public class ServerJar {
    public static void updateJar() throws Exception {
        if (FileHandler.needsUpdate()) {
            System.out.println("::Updating server jar to latest...");
            Util.downloadFile("https://serverjars.com/api/fetchJar/" + ApplicationConfig.serverType, "server.jar");
            System.out.println("::Updated server jar to latest");
        }
        System.out.println("::Server jar on latest version");
    }
}
