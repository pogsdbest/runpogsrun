package com.game.framework.display.tilemap;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

public class TileMapDisplay extends Group {
	
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private int[] drawLayers;
	private int tileMapWidth;
	private int tileMapHeight;
	private float tileWidth;
	private float tileHeight;
	
	public TileMapDisplay(TiledMap map ,OrthographicCamera camera) {
		// TODO Auto-generated constructor stub
		this(map ,camera , 1f);
	}

	public TileMapDisplay(TiledMap map ,OrthographicCamera camera ,float unitScale) {
		this.setMap(map);
		this.camera = camera;
		// TODO Auto-generated constructor stub
		renderer = new OrthogonalTiledMapRenderer(map, unitScale);
		TiledMapTileLayer layer0 =(TiledMapTileLayer) map.getLayers().get(0);
		tileMapWidth = layer0.getWidth();
		tileMapHeight = layer0.getHeight();
		drawLayers = new int[map.getLayers().getCount()];
		for(int i=0;i<drawLayers.length;i++) {
			drawLayers[i] = i;
		}
		tileWidth = layer0.getTileWidth();
		tileHeight = layer0.getTileHeight();
		
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		// TODO Auto-generated method stub
		batch.end();
		renderer.setView(camera);
		renderer.render(drawLayers);
		batch.begin();
		super.draw(batch, parentAlpha);
	}
	
	@Override
	public float getWidth() {
		// TODO Auto-generated method stub
		return tileMapWidth * tileWidth;
	}
	
	@Override
	public float getHeight() {
		// TODO Auto-generated method stub
		return tileMapHeight * tileHeight;
	}

	public TiledMap getMap() {
		return map;
	}

	public void setMap(TiledMap map) {
		this.map = map;
	}
	

}
