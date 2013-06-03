package de.bujan.core;

/**
 * Created with IntelliJ IDEA.
 * User: Niko
 * Date: 03.06.13
 * Time: 10:45
 */
public class Field {
    private float posX1;
    private float posX2;
    private float posY1;
    private float posY2;
    private FieldColor color;
    private Stone stone;

    public float getPosX1() {
        return posX1;
    }

    public void setPosX1(float posX1) {
        this.posX1 = posX1;
    }

    public float getPosX2() {
        return posX2;
    }

    public void setPosX2(float posX2) {
        this.posX2 = posX2;
    }

    public float getPosY1() {
        return posY1;
    }

    public void setPosY1(float posY1) {
        this.posY1 = posY1;
    }

    public float getPosY2() {
        return posY2;
    }

    public void setPosY2(float posY2) {
        this.posY2 = posY2;
    }

    public FieldColor getColor() {
        return color;
    }

    public void setColor(FieldColor color) {
        this.color = color;
    }

    public Stone getStone() {
        return stone;
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public boolean hasStone() {
        return null != stone;
    }

    @Override
    public String toString() {
        return "Field{" +
                "posX1=" + posX1 +
                ", posX2=" + posX2 +
                ", posY1=" + posY1 +
                ", posY2=" + posY2 +
                ", color=" + color +
                ", stone=" + stone +
                "}\n";
    }
}
