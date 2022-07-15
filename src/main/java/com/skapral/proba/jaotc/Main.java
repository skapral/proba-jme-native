package com.skapral.proba.jaotc;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeSystem;
import java.util.zip.ZipException;

public class Main extends SimpleApplication {

    public static void main(String... args) {
        try {
            JmeSystem.setSystemDelegate(new JmeDesktopSystem());
            Main app = new Main();
            AppSettings settings = new AppSettings(true);
            settings.setTitle("proba");
            app.setShowSettings(false);
            app.setSettings(settings);
            app.start();
        } catch(Exception ex) {
            if(ex instanceof ZipException) {
                ZipException exx = (ZipException) ex;
                exx.printStackTrace();
            }
        }
    }

    @Override
    public void simpleInitApp() {

    }
}
