package com.medbro.game;

import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.Display;

public class NurseShiftMidlet extends MIDlet {
    private ShiftCanvas canvas;

    public void startApp() {
        if (canvas == null) {
            canvas = new ShiftCanvas(this);
        }
        Display.getDisplay(this).setCurrent(canvas);
        canvas.start();
    }

    public void pauseApp() {
        if (canvas != null) {
            canvas.pause();
        }
    }

    public void destroyApp(boolean unconditional) {
        if (canvas != null) {
            canvas.stop();
        }
    }

    public void exit() {
        destroyApp(true);
        notifyDestroyed();
    }
}
