package de.bujan.core;

import de.bujan.core.constants.StoneColor;
import de.bujan.core.constants.StoneType;
import playn.core.Image;

/**
 * Created with IntelliJ IDEA.
 * User: Niko
 * Date: 09.02.13
 * Time: 11:29
 */
public class Stone {
    private Image image;
    private StoneType type = StoneType.NORMAL;
    private StoneColor color;

    public Stone(Image image, StoneColor color, StoneType type) {
        this.image = image;
        this.color = color;
        this.type = type;
    }

    public StoneType getType() {
        return type;
    }

    public void setType(StoneType type) {
        this.type = type;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Stone{" +
                "image=" + image +
                ", type=" + type +
                ", color=" + color +
                '}';
    }

    public StoneColor getColor() {
        return color;
    }
}

