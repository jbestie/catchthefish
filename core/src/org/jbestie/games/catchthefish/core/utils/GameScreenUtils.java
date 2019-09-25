package org.jbestie.games.catchthefish.core.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.Viewport;

public final class GameScreenUtils {
    private GameScreenUtils() {}

    private static Viewport screenViewPort;

    public static void setScreenViewPort(Viewport screenViewPort) {
        GameScreenUtils.screenViewPort = screenViewPort;
    }

    public static float getWorldWidth() {
        if ((screenViewPort == null) || (screenViewPort.getWorldWidth() < 1)) {
            return Gdx.graphics.getWidth();
        } else {
            return screenViewPort.getWorldWidth();
        }
    }

    public static float getWorldHeight() {
        if ((screenViewPort == null) || (screenViewPort.getWorldHeight() < 1)) {
            return Gdx.graphics.getHeight();
        } else {
            return screenViewPort.getWorldHeight();
        }
    }

}
