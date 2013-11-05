package de.bujan.core;

import de.bujan.core.constants.FieldColor;
import de.bujan.core.constants.StoneColor;
import de.bujan.core.constants.StoneType;

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


    public void setWhiteStone(StoneType stoneType) {
        this.stone = new Stone(ImageCache.stoneWhite, StoneColor.WHITE, stoneType);
    }

    public void setBlackStone(StoneType stoneType) {
        this.stone = new Stone(ImageCache.stoneBlack, StoneColor.BLACK, stoneType);
    }

    public void setStone(Stone stone) {
        this.stone = stone;
    }

    public boolean isBlack(){
        return color.equals(FieldColor.BLACK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Field field = (Field) o;

        if (Float.compare(field.posX1, posX1) != 0) return false;
        if (Float.compare(field.posX2, posX2) != 0) return false;
        if (Float.compare(field.posY1, posY1) != 0) return false;
        if (Float.compare(field.posY2, posY2) != 0) return false;
        if (color != field.color) return false;
        if (stone != null ? !stone.equals(field.stone) : field.stone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (posX1 != +0.0f ? Float.floatToIntBits(posX1) : 0);
        result = 31 * result + (posX2 != +0.0f ? Float.floatToIntBits(posX2) : 0);
        result = 31 * result + (posY1 != +0.0f ? Float.floatToIntBits(posY1) : 0);
        result = 31 * result + (posY2 != +0.0f ? Float.floatToIntBits(posY2) : 0);
        result = 31 * result + color.hashCode();
        result = 31 * result + (stone != null ? stone.hashCode() : 0);
        return result;
    }
}
