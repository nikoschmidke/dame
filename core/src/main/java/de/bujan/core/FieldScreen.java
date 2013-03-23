package de.bujan.core;

import playn.core.*;
import tripleplay.game.UIScreen;

import java.util.ArrayList;
import java.util.List;

public class FieldScreen extends UIScreen {

    public static int width;
    public static int height;

    protected List<Stone> onScreenStones = new ArrayList<Stone>();

    @Override
    public void wasAdded() {
        width = (int) (graphics().width() * graphics().scaleFactor());
        height = graphics().height();

        SurfaceLayer backgroundSurfaceLayer =
                graphics().createSurfaceLayer(width, height);
        graphics().rootLayer().add(backgroundSurfaceLayer);
        float scale = getScale();
        backgroundSurfaceLayer.setScale(scale, scale);
        backgroundSurfaceLayer.surface().drawImage(ImageCache.background, 0, 0);


        ImmediateLayer stoneLayer =
                graphics().createImmediateLayer(
                        width,
                        height,
                        new ImmediateLayer.Renderer() {
                            @Override
                            public void render(Surface surface) {
                                surface.setFillColor(0x00000000);
                                surface.fillRect(0, 0, graphics().width(), graphics().height());

                                for (Stone stone : onScreenStones) {
                                    surface.drawImage(stone.getImage(),
                                            stone.getPosX(),
                                            stone.getPosY(),
                                            stone.getWidth(),
                                            stone.getHeight());

                                }
                            }
                        });
        graphics().rootLayer().add(stoneLayer);

    }

    private float getScale() {
        float scale;
        if (height >= width) {
            scale = width / ImageCache.background.width();
        } else {
            scale = height / ImageCache.background.height();
        }
        return scale;
    }

    protected Graphics graphics() {
        return PlayN.graphics();
    }

    @Override
    public void update(float delta) {
        width = graphics().width();
        height = graphics().height();
    }
}
