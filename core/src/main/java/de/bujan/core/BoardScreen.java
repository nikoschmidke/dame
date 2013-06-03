package de.bujan.core;

import playn.core.Graphics;
import playn.core.Image;
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

import static java.lang.System.out;

public class BoardScreen extends UIScreen implements Listener {

    public static int screenWidth;
    public static int screenHeight;

    protected List<Stone> onScreenStones = new ArrayList<Stone>();
    private float scale;
    private float boardSize;
    private boolean firstTime = true;
    private Point touchPoint = null;
    private float dy;
    private float dx;
    private Boolean firstTouch = null;
    private Stone touchedStone;
    private float fieldSize;
    private float imgSize;
    private float diff;
    private int rowCount = 10;
    private Field[][] fields = new Field[rowCount][rowCount];

    @Override
    public void wasAdded() {
        if (firstTime) {
            getScale();
            prepareBackground();
            firstTime = false;
            calculateFields();
            drawStones();

        }
    }

    private void calculateFields() {
        imgSize = (fieldSize * 0.8f);
        diff = (fieldSize * 0.1f);
        boolean startsWithWhiteField = false;
        for (int y = 0; y < rowCount; y++) {
            for (int x = 0; x < rowCount; x++) {
                Field field = new Field();
                field.setPosX1(x * fieldSize + dx);
                field.setPosX2((x + 1) * fieldSize + dx);
                field.setPosY1(y * fieldSize + dy);
                field.setPosY2((y + 1) * fieldSize + dy);
                if (startsWithWhiteField) {
                    field.setColor(FieldColor.WHITE);
                } else {
                    field.setColor(FieldColor.BLACK);
                    if (y < 3) {
                        Stone newStone = createNewStone(
                                field.getPosX1(),
                                field.getPosY1(),
                                ImageCache.stoneWhite,
                                StoneColor.WHITE
                        );
                        field.setStone(newStone);
                        onScreenStones.add(newStone);
                    }
                    if (rowCount - 4 < y && y < rowCount) {
                        Stone newStone = createNewStone(
                                field.getPosX1(),
                                field.getPosY1(),
                                ImageCache.stoneBlack,
                                StoneColor.BLACK
                        );
                        field.setStone(newStone);
                        onScreenStones.add(newStone);
                    }
                }
                fields[x][y] = field;
                startsWithWhiteField = !startsWithWhiteField;
            }
            startsWithWhiteField = !startsWithWhiteField;
        }

    }

    private void drawStones() {
        ImmediateLayer stoneLayer =
                graphics().createImmediateLayer(
                        screenWidth,
                        screenHeight,
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

    private Stone createNewStone(float x, float y, Image image, StoneColor color) {
        Stone stone = new Stone();
        stone.setImage(image);
        stone.setPosX(x + diff);
        stone.setPosY(y + diff);
        stone.setWidth(imgSize);
        stone.setHeight(imgSize);
        stone.setColor(color);
        return stone;
    }

    private void prepareBackground() {
        SurfaceLayer backgroundSurfaceLayer = graphics().createSurfaceLayer(screenWidth, screenHeight);
        graphics().rootLayer().add(backgroundSurfaceLayer);
        backgroundSurfaceLayer.surface().drawImage(
                ImageCache.background,
                dx,
                dy,
                boardSize,
                boardSize
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
            out.println("touchPoint:" + touchPoint);
            if (firstTouch != null && firstTouch) {
                out.println("firstTouch");
                getTouchedStone();
                firstTouch = false;
                //secondTouch
            } else {
                out.println("secondTouch");
                if (allowedMove()) {
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
        out.println("touchedStone:" + touchedStone);
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
        out.println("dx" + dx);
        float posXOnField = touchPoint.x() - dx;
        out.println("posXOnField:" + posXOnField);
        int rowX = (int) (posXOnField / fieldSize);
        out.println("rowX:" + rowX);
        float newPosXOnField = rowX * fieldSize;
        touchedStone.setPosX(newPosXOnField + dx + diff);
        float posYOnField = touchPoint.y() - dy;
        int rowY = (int) (posYOnField / fieldSize);
        touchedStone.setPosY(rowY * fieldSize + dy + diff);
        out.println("New pos touchedStone:" + touchedStone);
    }

    private boolean allowedMove() {
        if (isSecondTouchOnSomeStone()) {
            return false;
        }
        if (isSecondTouchOnWhite()) {
            return false;
        }
        switch (touchedStone.getType()) {
            case NORMAL:
                if (isSecondTouchOnRightRow())
                    break;
            case DAME:
                ;
                break;
        }
        return true;
    }

    private boolean isSecondTouchOnRightRow() {
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    private boolean isSecondTouchOnWhite() {
        return false;  //To change body of created methods use File | Settings | File Templates.
    }

    private boolean touchPointOnStone(Stone stone) {
        if (stone.getPosX() <= touchPoint.x() && touchPoint.x() <= stone.getPosX() + fieldSize) {
            if (stone.getPosY() <= touchPoint.y() && touchPoint.y() <= stone.getPosY() + fieldSize) {
                return true;
            }
        }
        return false;
    }

    private boolean touchPointOnField() {
        return true;
    }

    private void getScale() {
        screenWidth = graphics().width();
        screenHeight = graphics().height();
        if (screenHeight >= screenWidth) {
            scale = screenWidth / ImageCache.background.width();
            boardSize = screenWidth;
            dx = 0;
            dy = (screenHeight - boardSize) / 2;
        } else {
            scale = screenHeight / ImageCache.background.height();
            boardSize = screenHeight;
            dx = (screenWidth - boardSize) / 2;
            dy = 0;
        }
        fieldSize = (boardSize / rowCount);
    }

    @Override
    public void onPointerStart(Pointer.Event event) {
        touchPoint = new Point((int) event.x(), (int) event.y());
        out.println("touchPoint " + touchPoint);
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
