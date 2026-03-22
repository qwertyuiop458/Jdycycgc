package com.medbro.game;

import java.util.Random;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;

public class ShiftCanvas extends Canvas implements Runnable {
    private final NurseShiftMidlet midlet;
    private final Random random = new Random();

    private Thread gameThread;
    private boolean running;
    private boolean paused;

    private int nurseX;
    private int nurseY;
    private int laneWidth;

    private int patientX;
    private int patientY;
    private int patientSpeed;

    private int medicineX;
    private int medicineY;
    private int medicineSpeed;

    private int shiftTime;
    private int score;
    private int misses;

    private static final int SHIFT_DURATION = 60; // seconds
    private static final int MAX_MISSES = 5;

    public ShiftCanvas(NurseShiftMidlet midlet) {
        this.midlet = midlet;
        setFullScreenMode(true);
        resetGame();
    }

    public void start() {
        paused = false;
        if (gameThread == null || !running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void pause() {
        paused = true;
    }

    public void stop() {
        running = false;
    }

    private void resetGame() {
        laneWidth = getWidth() / 3;
        nurseX = laneWidth + (laneWidth / 2);
        nurseY = getHeight() - 20;

        shiftTime = SHIFT_DURATION * 1000;
        score = 0;
        misses = 0;

        spawnPatient();
        spawnMedicine();
    }

    private void spawnPatient() {
        patientX = laneCenter(random.nextInt(3));
        patientY = 18;
        patientSpeed = 1 + random.nextInt(2);
    }

    private void spawnMedicine() {
        medicineX = laneCenter(random.nextInt(3));
        medicineY = 30;
        medicineSpeed = 1 + random.nextInt(2);
    }

    private int laneCenter(int lane) {
        return laneWidth * lane + laneWidth / 2;
    }

    public void run() {
        long last = System.currentTimeMillis();
        while (running) {
            long now = System.currentTimeMillis();
            int delta = (int) (now - last);
            last = now;

            if (!paused) {
                update(delta);
                repaint();
                serviceRepaints();
            }

            try {
                Thread.sleep(33);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void update(int deltaMs) {
        if (shiftTime <= 0 || misses >= MAX_MISSES) {
            return;
        }

        shiftTime -= deltaMs;
        if (shiftTime < 0) {
            shiftTime = 0;
        }

        patientY += patientSpeed;
        medicineY += medicineSpeed;

        if (Math.abs(nurseX - patientX) < 10 && Math.abs(nurseY - patientY) < 10) {
            score += 10;
            spawnPatient();
        } else if (patientY > getHeight()) {
            misses++;
            spawnPatient();
        }

        if (Math.abs(nurseX - medicineX) < 10 && Math.abs(nurseY - medicineY) < 10) {
            score += 5;
            spawnMedicine();
        } else if (medicineY > getHeight()) {
            spawnMedicine();
        }
    }

    protected void keyPressed(int keyCode) {
        int action = getGameAction(keyCode);

        if (keyCode == KEY_NUM0) {
            midlet.exit();
            return;
        }

        if (shiftTime <= 0 || misses >= MAX_MISSES) {
            if (keyCode == KEY_NUM5 || action == FIRE) {
                resetGame();
            }
            return;
        }

        if (action == LEFT || keyCode == KEY_NUM4) {
            nurseX -= laneWidth;
        } else if (action == RIGHT || keyCode == KEY_NUM6) {
            nurseX += laneWidth;
        } else if (keyCode == KEY_NUM5 || action == FIRE) {
            paused = !paused;
        }

        if (nurseX < laneCenter(0)) {
            nurseX = laneCenter(0);
        } else if (nurseX > laneCenter(2)) {
            nurseX = laneCenter(2);
        }
    }

    protected void paint(Graphics g) {
        g.setColor(238, 246, 255);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(180, 200, 220);
        g.drawLine(laneWidth, 0, laneWidth, getHeight());
        g.drawLine(laneWidth * 2, 0, laneWidth * 2, getHeight());

        drawHud(g);

        if (shiftTime <= 0 || misses >= MAX_MISSES) {
            drawGameOver(g);
            return;
        }

        drawNurse(g, nurseX, nurseY);
        drawPatient(g, patientX, patientY);
        drawMedicine(g, medicineX, medicineY);

        if (paused) {
            g.setColor(0, 0, 0);
            g.drawString("ПАУЗА", getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.BASELINE);
        }
    }

    private void drawHud(Graphics g) {
        g.setColor(10, 40, 90);
        g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL));
        g.drawString("Очки: " + score, 2, 2, Graphics.LEFT | Graphics.TOP);
        g.drawString("Ошибки: " + misses + "/" + MAX_MISSES, getWidth() - 2, 2, Graphics.RIGHT | Graphics.TOP);
        g.drawString("Смена: " + (shiftTime / 1000), getWidth() / 2, 2, Graphics.HCENTER | Graphics.TOP);
    }

    private void drawNurse(Graphics g, int x, int y) {
        g.setColor(30, 100, 180);
        g.fillRect(x - 6, y - 8, 12, 14);
        g.setColor(255, 224, 189);
        g.fillArc(x - 4, y - 14, 8, 8, 0, 360);
        g.setColor(255, 255, 255);
        g.drawRect(x - 7, y - 9, 13, 15);
    }

    private void drawPatient(Graphics g, int x, int y) {
        g.setColor(220, 80, 80);
        g.fillRect(x - 7, y - 4, 14, 8);
        g.setColor(90, 0, 0);
        g.drawString("!", x, y - 10, Graphics.HCENTER | Graphics.BASELINE);
    }

    private void drawMedicine(Graphics g, int x, int y) {
        g.setColor(60, 180, 80);
        g.fillRect(x - 5, y - 5, 10, 10);
        g.setColor(255, 255, 255);
        g.drawLine(x, y - 3, x, y + 3);
        g.drawLine(x - 3, y, x + 3, y);
    }

    private void drawGameOver(Graphics g) {
        g.setColor(0, 0, 0);
        String title = misses >= MAX_MISSES ? "Смена сорвана" : "Смена завершена";
        g.drawString(title, getWidth() / 2, getHeight() / 2 - 14, Graphics.HCENTER | Graphics.BASELINE);
        g.drawString("Итог: " + score, getWidth() / 2, getHeight() / 2, Graphics.HCENTER | Graphics.BASELINE);
        g.drawString("5 - новая смена", getWidth() / 2, getHeight() / 2 + 14, Graphics.HCENTER | Graphics.BASELINE);
        g.drawString("0 - выход", getWidth() / 2, getHeight() / 2 + 28, Graphics.HCENTER | Graphics.BASELINE);
    }
}
