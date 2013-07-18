package de.bujan.core;

import playn.core.Game;
import playn.core.PlayN;
import playn.core.util.Clock;
import tripleplay.game.ScreenStack;

public class Dame extends Game.Default {
    protected final ScreenStack director = new ScreenStack() {
        @Override
        protected void handleError(RuntimeException error) {
            PlayN.log().warn("Screen failure", error);
        }

        @Override
        protected Transition defaultPushTransition() {
            return ScreenStack.NOOP;
        }

        @Override
        protected Transition defaultPopTransition() {
            return ScreenStack.NOOP;
        }
    };

    /**
     * Creates an instance of the default game implementation.
     *
     * @param updateRate the desired update rate of the main game loop, in ms.
     */
    public Dame(int updateRate) {
        super(updateRate);
    }

    @Override
    public void init() {
        director.push(new LoadingScreen(director));
    }

    @Override
    public void update (int delta) {
        director.update(delta);
    }

    @Override
    public void paint (float alpha) {
        director.paint(new Clock.Source(0));
    }

}
