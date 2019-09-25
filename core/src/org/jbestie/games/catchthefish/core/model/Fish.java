package org.jbestie.games.catchthefish.core.model;

import com.badlogic.gdx.graphics.Texture;
import org.jbestie.games.catchthefish.core.utils.GameScreenUtils;

import java.util.Random;

public class Fish extends AbstractObject {
    private final static Texture BLUE_FISH = new Texture("fish/bluefish.png");
    private final static Texture GOLD_FISH = new Texture("fish/goldfish.png");
    private final static Texture GREEN_FISH = new Texture("fish/greenfish.png");
    private final static Texture GREY_FISH = new Texture("fish/greyfish.png");

    public Fish(Texture fishTexture, int x, int y, ObjectDirection direction) {
        super(fishTexture, x, y, direction);
        if (direction == ObjectDirection.RIGHT) {
            objectSprite.flip(true, false);
        }
    }

    public Fish(Texture fishTexture, float x, float y, ObjectDirection direction, float speed) {
        super(fishTexture, x, y, direction, speed);
        if (direction == ObjectDirection.RIGHT) {
            objectSprite.flip(true, false);
        }
    }

    @Override
    public boolean isShowOnHit() {
        return true;
    }

    @Override
    protected boolean isObjectOutOfScreen() {
        return (x <= -objectSprite.getWidth()) || (x > GameScreenUtils.getWorldWidth());
    }

    @Override
    protected void onObjectBecomesInvisible() {
        flipDirection();
    }


    //    FACTORY METHODS
    public static Fish getBlueFish() {
        return getFishWithRandomSpeedAndPosition(BLUE_FISH);
    }

    public static Fish getGoldFish() {
        return getFishWithRandomSpeedAndPosition(GOLD_FISH);
    }

    public static Fish getGreenFish() {
        return getFishWithRandomSpeedAndPosition(GREEN_FISH);
    }

    public static Fish getGreyFish() {
        return getFishWithRandomSpeedAndPosition(GREY_FISH);
    }

    private static Fish getFishWithRandomSpeedAndPosition(Texture goldFish) {
        ObjectDirection direction = getRandomHorizontalDirection();
        float xPosition = (direction == ObjectDirection.LEFT) ? GameScreenUtils.getWorldWidth() : -BLUE_FISH.getWidth();
        return new Fish(goldFish, xPosition, RANDOM_GENERATOR.nextInt((int) (GameScreenUtils.getWorldHeight() - goldFish.getHeight())), direction, new Random().nextInt(4) + 1);
    }

    public static Fish getRandomFish() {
        int random = RANDOM_GENERATOR.nextInt(4);
        switch (random) {
            case 0:
                return getBlueFish();
            case 1:
                return getGoldFish();
            case 2:
                return getGreenFish();
            case 3:
                return getGreyFish();
            default:
                throw new IllegalArgumentException("Unknown number " + random);
        }
    }


    @Override
    protected void performActionOnHit() {
        super.performActionOnHit();
        speed = 5;
    }
}
