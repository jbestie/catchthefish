package org.jbestie.games.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public abstract class AbstractObject {
    public enum ObjectDirection {
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    public static final Random RANDOM_GENERATOR = new Random();

    protected boolean inViewPort = true;
    protected Sprite objectSprite;
    protected float x;
    protected float y;
    protected ObjectDirection direction;
    protected float speed;

    public AbstractObject(Texture objectTexture, float x, float y, ObjectDirection direction) {
        this(objectTexture, x, y, direction, 1.0f);
    }

    public AbstractObject(Texture objectTexture, float x, float y, ObjectDirection direction, float speed) {
        objectSprite = new Sprite(objectTexture);
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }

    public void move() {
        performMove();

        if (isObjectOutOfScreen()) {
            inViewPort = false;
            onObjectBecomesInvisible();
        } else {
            inViewPort = true;
            onObjectBecomesVisible();
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(objectSprite, x, y);
    }

    protected void flipDirection() {
        switch (direction) {
            case RIGHT:
                direction = ObjectDirection.LEFT;
                break;
            case LEFT:
                direction = ObjectDirection.RIGHT;
                break;
            case UP:
                direction = ObjectDirection.DOWN;
                break;
            case DOWN:
                direction = ObjectDirection.UP;
                break;
            default:
                break;
        }

        objectSprite.flip(true, false);
    }

    public boolean isInViewPort() {
        return inViewPort;
    }

    protected void performMove() {
        switch (direction) {
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UP:
                y += speed;
                break;
            case DOWN:
                y -= speed;
            default:
                break;
        }
    }

    protected abstract boolean isObjectOutOfScreen();

    protected void onObjectBecomesInvisible() {
    }

    protected void onObjectBecomesVisible() {
    }

    public static ObjectDirection getRandomHorizontalDirection() {
        int direction = RANDOM_GENERATOR.nextInt(2);
        return (direction == 0) ? ObjectDirection.LEFT : ObjectDirection.RIGHT;
    }
}
