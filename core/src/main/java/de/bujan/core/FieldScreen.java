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
    private Boolean firstTouch = null;
    private Stone touchedStone;
    private float rowSize;
    private float imgSite;
    private float diff;

    @Override
    public void wasAdded() {
        if (firstTime) {
            getScale();
            prepareBackground();
            firstTime = false;
            initStones();
            drawStones();
        }
    }

    private void drawStones() {
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
        stoneLayer.addListener(this);
        graphics().rootLayer().add(stoneLayer);
    }

    private void initStones() {

        imgSite = (rowSize * 0.8f);
        diff = (rowSize * 0.1f);
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
        getScale();
        if (touchPoint != null && touchPointOnField()) {
            System.out.println("touchPoint:" + touchPoint);
            if (firstTouch != null && firstTouch) {
                System.out.println("firstTouch");
                getTouchedStone();
                firstTouch = false;
            } else {
                System.out.println("firstTouch");
                if (!isSecondTouchOnSomeStone() && allowedMove()) {
                    moveTouchedStone();
                    firstTouch = null;
                    touchPoint = null;
                }
            }
            //showAvailableMoves(stone);
        }
        drawStones();
    }

    private void getTouchedStone() {
        for (Stone stone : onScreenStones) {
            if (touchPointOnStone(stone)) {
                touchedStone = stone;
                touchPoint = null;
                break;
            }
        }
        System.out.println("touchedStone:" + touchedStone);
    }

    private boolean isSecondTouchOnSomeStone() {
        boolean touchPointOnStone = false;
        for (Stone stone : onScreenStones) {
            if (touchPointOnStone(stone)) {
                touchPointOnStone = true;
                break;
            }
        }
        return touchPointOnStone;
    }

    private void moveTouchedStone() {
        System.out.println("dx" + dx);
        float posXOnField = touchPoint.x() - dx;
        System.out.println("posXOnField:" + posXOnField);
        int rowX = (int) (posXOnField / rowSize);
        System.out.println("rowX:" + rowX);
        float newPosXOnField = rowX * rowSize;
        touchedStone.setPosX(newPosXOnField + dx + diff);
        float posYOnField = touchPoint.y() - dy;
        int rowY = (int) (posYOnField / rowSize);
        touchedStone.setPosY(rowY * rowSize + dy + diff);
        System.out.println("New pos touchedStone:" + touchedStone);
    }

    private boolean allowedMove() {
        return true;
    }

    private boolean touchPointOnStone(Stone stone) {
        if (stone.getPosX() <= touchPoint.x() && touchPoint.x() <= stone.getPosX() + rowSize) {
            if (stone.getPosY() <= touchPoint.y() && touchPoint.y() <= stone.getPosY() + rowSize) {
                return true;
            }
        }
        return false;
    }

    private boolean touchPointOnField() {
        return true;
    }

    private void getScale() {
        width = graphics().width();
        height = graphics().height();
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
        rowSize = (fieldSiteSize / 10);
    }

    @Override
    public void onPointerStart(Pointer.Event event) {
        touchPoint = new Point((int) event.x(), (int) event.y());
        System.out.println("touchPoint " + touchPoint);
        if (firstTouch == null) firstTouch = true;
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
