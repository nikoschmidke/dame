package de.bujan.core;

import playn.core.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Niko
 * Date: 09.02.13
 * Time: 11:29
 */
public class Stone {
    private Image image;
    private float posX;
    private float posY;
    private float width;
    private float height;
    private StoneType type = StoneType.NORMAL;
    private StoneColor color;


    public StoneType getType() {
        return type;
    }

    public void setType(StoneType type) {
        this.type = type;
    }

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

    public void setImage(Image image) {
        this.image = image;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "image=" + image +
                ", posX=" + posX +
                ", posY=" + posY +
                ", width=" + width +
                ", height=" + height +
                ", type=" + type +
                ", color=" + color +
                '}';
    }

    public StoneColor getColor() {
        return color;
    }

    public void setColor(StoneColor color) {
        this.color = color;
    }


}

