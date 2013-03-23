package de.bujan.core;

import playn.core.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Niko
 * Date: 09.02.13
 * Time: 11:29
 */
public class Stone {
    private Image img;
    private int x, y;
    private Image image;
    private float posX;
    private float posY;
    private float width;
    private float height;

    public Image getImage() {
        return image;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
