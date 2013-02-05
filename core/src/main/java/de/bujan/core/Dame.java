package de.bujan.core;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class Dame implements Game {
    @Override
    public void init() {
        // create and add background image layer
        Image bgImage = assets().getImage("images/schachbrett.png");

        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        //bgLayer.setHeight(bgImage.height());
        //bgLayer.setWidth(bgImage.width());
        //graphics().setSize((int)bgImage.height(), (int)bgImage.width());
        graphics().rootLayer().add(bgLayer);
    }

    @Override
    public void paint(float alpha) {
        // the background automatically paints itself, so no need to do anything here!
    }

    @Override
    public void update(float delta) {
    }

    @Override
    public int updateRate() {
        return 25;
    }
}
