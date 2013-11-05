package de.bujan.core;

import de.bujan.core.constants.FieldColor;
import de.bujan.core.constants.StoneColor;
import de.bujan.core.constants.StoneType;
import playn.core.Graphics;
import playn.core.ImageLayer;
import playn.core.ImmediateLayer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.Pointer.Listener;
import playn.core.Surface;
import pythagoras.i.Point;
import tripleplay.game.UIScreen;

import static java.lang.System.out;

public class BoardScreen extends UIScreen implements Listener {

    public static int screenWidth;
    public static int screenHeight;

    private int boardSize;
    private boolean firstTime = true;
    private Point touchPoint = null;
    private int dy;
    private int dx;
    private boolean firstTouch = true;
    private int fieldSize;
    private int rowCount = 10;
    private Field[][] fields = new Field[rowCount][rowCount];
    private Field touchedField;
    private boolean whiteTurn = true;

    @Override
    public void wasAdded() {
        if (firstTime) {
            firstTime = false;
            calculateSizes();
            prepareBackground();
            calculateFields();
            drawStones();
        }
    }

    private void calculateSizes() {
        screenWidth = graphics().width();
        screenHeight = graphics().height();
        if (screenHeight >= screenWidth) {
            boardSize = screenWidth;
            dx = 0;
            dy = (screenHeight - boardSize) / 2;
        } else {
            boardSize = screenHeight;
            dx = (screenWidth - boardSize) / 2;
            dy = 0;
        }
        fieldSize = (boardSize / rowCount);
    }

    private void calculateFields() {
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
                        field.setWhiteStone(StoneType.NORMAL);
                    }
                    if (rowCount - 4 < y && y < rowCount) {
                        field.setBlackStone(StoneType.NORMAL);
                    }
                }
                fields[x][y] = field;
                startsWithWhiteField = !startsWithWhiteField;
            }
            startsWithWhiteField = !startsWithWhiteField;
        }

    }

    private void drawStones() {
        ImmediateLayer.Renderer renderer = new ImmediateLayer.Renderer() {
            @Override
            public void render(Surface surface) {
                surface.setFillColor(0x00000000);
                surface.fillRect(0, 0, screenWidth, screenHeight);
                for (int y = 0; y < rowCount; y++) {
                    for (int x = 0; x < rowCount; x++) {
                        Field field = fields[x][y];
                        if (field.hasStone()) {
                            Stone stone = field.getStone();
                            surface.drawImage(
                                    stone.getImage(),
                                    field.getPosX1(),
                                    field.getPosY1(),
                                    fieldSize,
                                    fieldSize
                            );
                        }
                    }
                }
            }
        };
        ImmediateLayer stoneLayer =
                graphics().createImmediateLayer(
                        screenWidth,
                        screenHeight,
                        renderer
                );
        stoneLayer.addListener(this);
        graphics().rootLayer().add(stoneLayer);
    }

    private void prepareBackground() {
        ImageLayer imageLayer = graphics().createImageLayer(ImageCache.background);
        graphics().rootLayer().add(imageLayer);
        imageLayer.setTx(dx);
        imageLayer.setTy(dy);
        imageLayer.setSize(boardSize, boardSize);
    }

    protected Graphics graphics() {
        return PlayN.graphics();
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        //calculateSizes();
        if (touchPoint != null && touchPointOnBoard()) {
            out.println("touchPoint:" + touchPoint);
            Field touchedFieldTemp = getTouchedField();
            if (touchedFieldTemp.hasStone()) {
                if (firstTouch &&
                        !touchedFieldTemp.equals(touchedField) &&
                        touchedFieldStoneColorIsSameAsPlayerColor(touchedFieldTemp)
                        ) {
                    out.println("firstTouch");
                    touchedField = touchedFieldTemp;
                    firstTouch = false;
                }
            } else {
                //secondTouch
                if (!firstTouch && touchedFieldTemp.isBlack()) {
                    out.println("secondTouch");
                    moveStone(touchedFieldTemp);
                    firstTouch = true;
                    whiteTurn = !whiteTurn;
                    drawStones();
                }
            }
            out.println("whiteTurn=" + whiteTurn);
            //showAvailableMoves(stone);
        }
        touchPoint = null;
    }

    private boolean touchedFieldStoneColorIsSameAsPlayerColor(Field touchedField) {
        return (whiteTurn && touchedField.getStone().getColor().equals(StoneColor.WHITE)) ||
                (!whiteTurn && touchedField.getStone().getColor().equals(StoneColor.BLACK));
    }

    private Field getTouchedField() {
        int posX = touchPoint.x() - dx;
        int posY = touchPoint.y() - dy;
        int rowX = posX / fieldSize;
        int rowY = posY / fieldSize;
        return fields[rowX][rowY];
    }

    private void moveStone(Field touchedFieldTemp) {
        touchedFieldTemp.setStone(touchedField.getStone());
        touchedField.setStone(null);
    }

    private boolean touchPointOnBoard() {
        if (dx < touchPoint.x() && touchPoint.x() < dx + boardSize) {
            if (dy < touchPoint.y() && touchPoint.y() < dy + boardSize) {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onPointerStart(Pointer.Event event) {
        touchPoint = new Point((int) event.x(), (int) event.y());
        out.println("touchPoint " + touchPoint);
    }

    @Override
    public void onPointerEnd(Pointer.Event event) {

    }

    @Override
    public void onPointerDrag(Pointer.Event event) {

    }

    @Override
    public void onPointerCancel(Pointer.Event event) {

    }
}
