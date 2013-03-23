package de.bujan.core;

import playn.core.Image;

import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: Niko
 * Date: 10.02.13
 * Time: 14:31
 */
public class GameObject {
    public Image img;
    public double oldx, oldy, oldz;
    public double x, y, z;


    Stack stack;
    public double x(double alpha) {
        return x * alpha + oldx * (1.0f - alpha);
    }

    public double y(double alpha) {
        return y * alpha + oldy * (1.0f - alpha);
    }

    public double z(double alpha) {
        return z * alpha + oldz * (1.0f - alpha);
    }
}
