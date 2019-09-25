package org.jbestie.games.catchthefish.core.model;

import com.badlogic.gdx.graphics.Texture;
import org.jbestie.games.catchthefish.core.utils.GameScreenUtils;

public class Bubble extends AbstractObject {
    private final static Texture TEST_BUBBLE = new Texture("sea/bubble.png");

    public Bubble(Texture objectTexture, float x, float y, ObjectDirection direction) {
        super(objectTexture, x, y, direction);
    }

    public Bubble(Texture objectTexture, float x, float y, ObjectDirection direction, float speed) {
        super(objectTexture, x, y, direction, speed);
    }

    @Override
    public boolean isShowOnHit() {
        return false;
    }

    @Override
    protected boolean isObjectOutOfScreen() {
        return (y > GameScreenUtils.getWorldHeight());
    }

    public static Bubble getDefaultBubbleWithRandomPositionAndSpeed() {
        return new Bubble(TEST_BUBBLE, RANDOM_GENERATOR.nextInt((int) GameScreenUtils.getWorldWidth()), -TEST_BUBBLE.getHeight(), ObjectDirection.UP, RANDOM_GENERATOR.nextInt(3) + 1);
    }
}
