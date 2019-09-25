package org.jbestie.games.catchthefish.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.jbestie.games.catchthefish.core.model.AbstractObject;
import org.jbestie.games.catchthefish.core.model.Bubble;
import org.jbestie.games.catchthefish.core.model.Fish;
import org.jbestie.games.catchthefish.core.utils.GameScreenUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CatchTheFishMainClass extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background;
    private final List<AbstractObject> backFishList = new ArrayList<AbstractObject>();
    private final List<AbstractObject> frontFishList = new ArrayList<AbstractObject>();
    private final List<AbstractObject> backBubbleList = new ArrayList<AbstractObject>();
    private final List<AbstractObject> frontBubbleList = new ArrayList<AbstractObject>();

    private Viewport viewport;
    private OrthographicCamera camera;

    private final Vector3 touchPoint = new Vector3();
    private boolean justTouched = false;

    private long time = System.currentTimeMillis();

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.update();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        viewport = new ExtendViewport((float)Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight(), camera);
        viewport.apply();

        GameScreenUtils.setScreenViewPort(viewport);

        batch = new SpriteBatch();
        background = new Texture("background.png");
        backFishList.add(Fish.getRandomFish());
        backFishList.add(Fish.getRandomFish());
        frontFishList.add(Fish.getRandomFish());
        frontFishList.add(Fish.getRandomFish());

        backBubbleList.add(Bubble.getDefaultBubbleWithRandomPositionAndSpeed());
        backBubbleList.add(Bubble.getDefaultBubbleWithRandomPositionAndSpeed());
        backBubbleList.add(Bubble.getDefaultBubbleWithRandomPositionAndSpeed());

        frontBubbleList.add(Bubble.getDefaultBubbleWithRandomPositionAndSpeed());
        frontBubbleList.add(Bubble.getDefaultBubbleWithRandomPositionAndSpeed());
    }

    @Override
    public void render() {
        camera.update();
        camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

        justTouched = Gdx.input.justTouched();
        checkObjectsForHit(frontFishList);
        checkObjectsForHit(frontBubbleList);
        checkObjectsForHit(backFishList);
        checkObjectsForHit(backBubbleList);

        long currentTime = System.currentTimeMillis();
        long delta = currentTime - time;
        boolean positionChanged = false;
        if (delta >= 100) {
            positionChanged = true;
            time = currentTime;
        }

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // draw bubbles
        iterateOverBubbles(backBubbleList, 3);

        // draw fishes
        iterateOverFishes(backFishList,3);

        // draw bubbles
        iterateOverBubbles(frontBubbleList, 2);

        // draw fishes
        iterateOverFishes(frontFishList,2);

        batch.end();

    }

    private void checkObjectsForHit(List<AbstractObject> objects) {
        if (!justTouched) {
            return;
        }

        Iterator<AbstractObject> iterator = objects.iterator();
        while (iterator.hasNext()) {
            AbstractObject object = iterator.next();
            if (object.checkHit(touchPoint.x, touchPoint.y)) {
                if (!object.isShowOnHit()) {
                    iterator.remove();
                }
                justTouched = false;
                return;
            }
        }
    }

    private void iterateOverFishes(List<AbstractObject> fishList, int size) {
        iterateOverObjects(fishList);

        if (fishList.size() < size) {
            fishList.add(Fish.getRandomFish());
        }
    }

    private void iterateOverObjects(List<AbstractObject> abstractObjects) {
        Iterator<AbstractObject> objectIterator = abstractObjects.iterator();
        while(objectIterator.hasNext()){
        AbstractObject object = objectIterator.next();
            object.move();
            object.draw(batch);
            if (!object.isInViewPort()) {
                objectIterator.remove();
            }
        }
    }

    private void iterateOverBubbles(List<AbstractObject> objectsList, int maxBubbleSize) {
        iterateOverObjects(objectsList);

        if (objectsList.size() < maxBubbleSize) {
            objectsList.add(Bubble.getDefaultBubbleWithRandomPositionAndSpeed());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }
}
