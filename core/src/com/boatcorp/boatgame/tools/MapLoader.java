package com.boatcorp.boatgame.tools;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

import static com.boatcorp.boatgame.screens.Constants.MAP_NAME;

public class MapLoader implements Disposable {

    private final TiledMap Map;
    private final OrthogonalTiledMapRenderer Renderer;

    public MapLoader() {
        Map = new TmxMapLoader().load(MAP_NAME);
        Renderer = new OrthogonalTiledMapRenderer(Map, 1f);
    }

    public TiledMap getMap() {
        return this.Map;
    }


    public void render(OrthographicCamera camera) {
        Renderer.render();
        Renderer.setView(camera);
    }



    @Override
    public void dispose() {
        Map.dispose();
    }
}
