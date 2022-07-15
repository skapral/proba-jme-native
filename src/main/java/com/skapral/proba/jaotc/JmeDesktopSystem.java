/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.skapral.proba.jaotc;

import com.jme3.audio.AudioRenderer;
import com.jme3.audio.openal.AL;
import com.jme3.audio.openal.ALAudioRenderer;
import com.jme3.audio.openal.ALC;
import com.jme3.audio.openal.EFX;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeContext;
import com.jme3.system.JmeSystemDelegate;
import com.jme3.system.NativeLibraryLoader;
import com.jme3.system.lwjgl.LwjglDisplay;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.ByteBuffer;

class JmeDesktopSystem extends JmeSystemDelegate {

    @Override
    public URL getPlatformAssetConfigURL() {
        return Thread.currentThread().getContextClassLoader().getResource("com/jme3/asset/Desktop.cfg");
    }


    @Override
    public void writeImageFile(OutputStream outStream, String format, ByteBuffer imageData, int width, int height) throws IOException {
    }

    @Override
    public void showErrorDialog(String message) {
        System.err.println("[JME ERROR] " + message);
    }

    @Override
    public boolean showSettingsDialog(AppSettings sourceSettings, final boolean loadFromRegistry) {
        return false;
    }

    @Override
    public JmeContext newContext(AppSettings settings, JmeContext.Type contextType) {
        initialize(settings);
        JmeContext ctx;
        ctx = new LwjglDisplay();
        ctx.setSettings(settings);
        return ctx;
    }

    private <T> T newObject(String className) {
        try {
            Class<T> clazz = (Class<T>) Class.forName(className);
            return clazz.newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public AudioRenderer newAudioRenderer(AppSettings settings) {
        initialize(settings);

        AL al;
        ALC alc;
        EFX efx;
        if (settings.getAudioRenderer().startsWith("LWJGL")) {
            al = new com.jme3.audio.lwjgl.LwjglAL();
            alc = new com.jme3.audio.lwjgl.LwjglALC();
            efx = new com.jme3.audio.lwjgl.LwjglEFX();
        } else {
            throw new UnsupportedOperationException(
                    "Unrecognizable audio renderer specified: "
                    + settings.getAudioRenderer());
        }

        if (al == null || alc == null || efx == null) {
            return null;
        }

        return new ALAudioRenderer(al, alc, efx);
    }

    @Override
    public void initialize(AppSettings settings) {
        if (initialized) {
            return;
        }
        initialized = true;
        if (!lowPermissions) {
            if (NativeLibraryLoader.isUsingNativeBullet()) {
                NativeLibraryLoader.loadNativeLibrary("bulletjme", true);
            }
        }
    }

    @Override
    public void showSoftKeyboard(boolean show) {
    }
}

