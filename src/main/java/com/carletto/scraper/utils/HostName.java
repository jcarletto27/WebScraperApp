package com.carletto.scraper.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by jcarlett on 3/6/2017.
 */
public class HostName {
    public static String getHostName() {
        String hostname = "Unknown";
        try {
            InetAddress add;
            add = InetAddress.getLocalHost();
            hostname = add.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return hostname;
    }

}
