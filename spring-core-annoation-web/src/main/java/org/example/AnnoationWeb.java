package org.example;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class AnnoationWeb {

    public static void main(String[] args) throws LifecycleException {

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8090);
        tomcat.setHostname("localhost");

        tomcat.addWebapp("", new File(".").getAbsolutePath());
        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();

    }
}