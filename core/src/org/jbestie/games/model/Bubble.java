package org.jbestie.games.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Bubble extends AbstractObject {
    private final static Texture TEST_BUBBLE = new Texture("sea/bubble.png");

    public Bubble(Texture objectTexture, float x, float y, ObjectDirection direction) {
        super(objectTexture, x, y, direction);
    }

    public Bubble(Texture objectTexture, float x, float y, ObjectDirection direction, float speed) {
        super(objectTexture, x, y, direction, speed);
    }

    @Override
    protected boolean isObjectOutOfScreen() {
        return (y > Gdx.graphics.getHeight());
    }

    public static Bubble getDefaultBubbleWithRandomPositionAndSpeed() {
        return new Bubble(TEST_BUBBLE, RANDOM_GENERATOR.nextInt(Gdx.graphics.getWidth()), -TEST_BUBBLE.getHeight(), ObjectDirection.UP, RANDOM_GENERATOR.nextInt(3) + 1);
    }
}
