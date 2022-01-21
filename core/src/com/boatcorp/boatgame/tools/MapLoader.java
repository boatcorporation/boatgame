package com.boatcorp.boatgame.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;

import static com.boatcorp.boatgame.screens.Constants.MAP_NAME;

public class MapLoader implements Disposable {

    private final World mWorld;
    private final TiledMap mMap;
    private final OrthogonalTiledMapRenderer mRenderer;

    public MapLoader(World world) {
        this.mWorld = world;
        mMap = new TmxMapLoader().load(MAP_NAME);
        mRenderer = new OrthogonalTiledMapRenderer(mMap);
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
