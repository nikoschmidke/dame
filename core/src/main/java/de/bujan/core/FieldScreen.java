package de.bujan.core;

import playn.core.Graphics;
import playn.core.ImmediateLayer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.Pointer.Listener;
import playn.core.Surface;
import playn.core.SurfaceLayer;
import pythagoras.i.Point;
import tripleplay.game.UIScreen;

import java.util.ArrayList;
import java.util.List;

public class FieldScreen extends UIScreen implements Listener {

    public static int width;
    public static int height;

    protected List<Stone> onScreenStones = new ArrayList<Stone>();
    private float scale;
    private float fieldSiteSize;
    private boolean firstTime = true;
    private Point touchPoint = null;
    private float dy;
    private float dx;

    @Override
    public void wasAdded() {

        width = graphics().width();//(int) (graphics().width() * graphics().scaleFactor());
        height = graphics().height();
        getScale();
        prepareBackground();
        if (firstTime) {
            initStones();
            firstTime = false;
        }

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
                                    surface.drawImage(
                                            stone.getImage(),
                                            stone.getPosX(),
                                            stone.getPosY(),
                                            stone.getWidth(),
                                            stone.getHeight()
                                    );
                                }
                            }
                        }
                );
        graphics().rootLayer().add(stoneLayer);
    }

    private void initStones() {
        float rowSize = (fieldSiteSize / 10);
        float imgSite = (rowSize * 0.8f);
        float diff = (rowSize * 0.1f);
        //white stones
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 5; x++) {
                Stone stone = new Stone();
                stone.setImage(ImageCache.stoneWhite);
                if (y % 2 == 0) {
                    stone.setPosX(dx + x * rowSize * 2 + diff);
                } else {
                    stone.setPosX(dx + rowSize + x * rowSize * 2 + diff);
                }
                stone.setPosY(dy + y * rowSize + diff);
                stone.setWidth(imgSite);
                stone.setHeight(imgSite);
                onScreenStones.add(stone);
            }
        }
        //black stones
        for (int y = 7; y < 10; y++) {
            for (int x = 0; x < 5; x++) {
                Stone stone = new Stone();
                stone.setImage(ImageCache.stoneBlack);
                if (y % 2 == 0) {
                    stone.setPosX(dx + x * rowSize * 2 + diff);
                } else {
                    stone.setPosX(dx + rowSize + x * rowSize * 2 + diff);
                }
                stone.setPosY(dy + y * rowSize + diff);
                stone.setWidth(imgSite);
                stone.setHeight(imgSite);
                onScreenStones.add(stone);
            }
        }
    }

    private void prepareBackground() {
        SurfaceLayer backgroundSurfaceLayer = graphics().createSurfaceLayer(width, height);
        graphics().rootLayer().add(backgroundSurfaceLayer);
        backgroundSurfaceLayer.surface().drawImage(
                ImageCache.background,
                dx,
                dy,
                fieldSiteSize,
                fieldSiteSize
        );
    }

    protected Graphics graphics() {
        return PlayN.graphics();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        width = graphics().width();
        height = graphics().height();
        getScale();
        if (touchPoint != null) {

        }

    }

    private void getScale() {
        if (height >= width) {
            scale = width / ImageCache.background.width();
            fieldSiteSize = width;
            dx = 0;
            dy = (height - fieldSiteSize) / 2;
        } else {
            scale = height / ImageCache.background.height();
            fieldSiteSize = height;
            dx = (width - fieldSiteSize) / 2;
            dy = 0;
        }
    }

    @Override
    public void onPointerStart(Pointer.Event event) {
        touchPoint = new Point((int) event.x(), (int) event.y());
    }

    @Override
    public void onPointerEnd(Pointer.Event event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onPointerDrag(Pointer.Event event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onPointerCancel(Pointer.Event event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
