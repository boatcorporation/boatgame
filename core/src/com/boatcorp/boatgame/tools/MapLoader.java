package com.boatcorp.boatgame.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import static com.boatcorp.boatgame.screens.Constants.*;

public class MapLoader implements Disposable {

    private final World mWorld;
    private final TiledMap mMap;
    private final OrthogonalTiledMapRenderer mRenderer;

    public MapLoader(World world) {
        this.mWorld = world;
        mMap = new TmxMapLoader().load(MAP_NAME);
        mRenderer = new OrthogonalTiledMapRenderer(mMap, 1f);
    }

    public Body getPlayer() {
        final Rectangle player = mMap.getLayers().get(PLAYER).getObjects().getByType(RectangleMapObject.class).get(0).getRectangle();
        return ShapeMaker.createRectangle(
                new Vector2(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2),
                new Vector2(player.getWidth() / 2, player.getHeight() / 2),
                BodyDef.BodyType.DynamicBody, mWorld, 1f);
    }


    public void render(OrthographicCamera camera) {
        mRenderer.render();
        mRenderer.setView(camera);
    }



    @Override
    public void dispose() {
        mMap.dispose();
    }
}
