package io.engine.util;

public class Color {
    public static final Color TRANSPARENT = new Color(0.0f, 0.0f, 0.0f, 0.0f);
    public static final Color BLACK = new Color(0.0f, 0.0f, 0.0f, 1.0f);
    public static final Color WHITE = new Color(1.0f, 1.0f, 1.0f, 1.0f);
    public static final Color RED = new Color(1.0f, 0.0f, 0.0f, 1.0f);
    public static final Color GREEN = new Color(0.0f, 1.0f, 0.0f, 1.0f);
    public static final Color BLUE = new Color(0.0f, 0.0f, 1.0f, 1.0f);

    private float red;
    private float green;
    private float blue;
    private float alpha;

    private Color(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        this.blue = blue;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }
}
