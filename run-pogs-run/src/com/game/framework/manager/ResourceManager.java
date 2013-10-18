package com.game.framework.manager;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.game.framework.utils.L;

public class ResourceManager implements Disposable {

	private ArrayList<Loader> loadList;
	private HashMap<String,AtlasRegion> textureRegions;
	private HashMap<String,Sound> sounds;
	private HashMap<String,Music> musics;
	private HashMap<String,BitmapFont> fonts;
	private ArrayList<Disposable> disposables;
	
	private int currentIndex;
	private boolean done;
	private int loadCount;
	public boolean isDebug;

	public ResourceManager() {
		// TODO Auto-generated constructor stub
		loadList = new ArrayList<ResourceManager.Loader>();
		textureRegions = new HashMap<String, TextureAtlas.AtlasRegion>();
		sounds = new HashMap<String, Sound>();
		musics = new HashMap<String, Music>();
		fonts = new HashMap<String, BitmapFont>();
		disposables = new ArrayList<Disposable>();
		loadCount = 0;
		done = false;
		isDebug = false;
	}

	public void loadAtlas(String file) {
		add(new TextureAtlasLoader(file));
	}

	public void loadSound(String file,String name) {
		add(new SoundLoader(file,name));
	}
	
	public void loadMusic(String file, String name) {
		add(new MusicLoader(file,name));
	}
	
	public void loadFont(String fntFile,String pngFile,String name) {
		add(new FontLoader(fntFile,pngFile,name));
	}
	
	public void add(Loader loader) {
		loadList.add(loader);
		loadCount++;
	}
	
	/*computes for the progress of loading the entire resources
	 * based on the currentIndex being loaded.
	 * returns 1 if fully loaded
	 */
	public float getProgress() {
		float progress = currentIndex / loadCount;
		return progress;
	}
	
	/*returns the AtlasRegion based on the specified name
	 * 
	 */
	public AtlasRegion getTextureRegion(String name) {
		AtlasRegion region = textureRegions.get(name);
		if(region == null) {
			L.e("Region "+name+" does'nt exist. pls check the image name");
		}
		return region;
	}
	
	public Sound getSound(String name) {
		Sound sound = sounds.get(name);
		if(sound == null ){
			if(sound == null) {
				L.e("Sound "+name+" does'nt exist. pls check the Sound name");
			}
		}
		return sound;
	}
	
	public Music getMusic(String name) {
		Music music = musics.get(name);
		if(music == null ){
			if(music == null) {
				L.e("Music "+name+" does'nt exist. pls check the Music name");
			}
		}
		return music;
	}
	
	public BitmapFont getFont(String name) {
		BitmapFont font = fonts.get(name);
		if(font == null ){
			if(font == null) {
				L.e("Font "+name+" does'nt exist. pls check the Font name");
			}
		}
		return font;
	}
	
	/*method for loading all resources one by one every update
	 * returns true if the load of the resource succeed, false otherwise.
	 */
	public boolean update() {
		if(isDone()) return true;
		Loader loader = loadList.get(currentIndex);
		currentIndex++;
		boolean isSucceed = loader.load();
		
		if(!isSucceed) {
			loader.error();
		}
		
		if(currentIndex >= loadList.size()) {
			//done release all Loader in Resources list
			loadList.clear();
			loadList = null;
			done = true;
		}
		return  isSucceed;
	}
	
	public boolean isDone() {
		return done;
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		textureRegions.clear();
		sounds.clear();
		musics.clear();
		fonts.clear();
		for(Disposable disposable : disposables) {
			disposable.dispose();
		}
	}
	
	/*interface for all Resources that can be Load by the ResourceManager
	 * 
	 */
	public interface Loader {
		/*method for loading the resources.
		 * returns true if the resource being loaded is not null
		 * false otherwise.
		 */
		public boolean load();
		/*prints the error message of the 
		 * Loader if something wrongs appear.
		 */
		public void error();
	}
	
	public class TextureAtlasLoader implements Loader {
		private String file;

		public TextureAtlasLoader(String file) {
			this.file = file;
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public boolean load() {
			// TODO Auto-generated method stub
			//load the texture packs.
			TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(file));
			//load the AtlasRegions inside of it and add it to the available resources
			if(atlas != null) {
				Array<AtlasRegion> regions = atlas.getRegions();
				for (int i = 0, n = regions.size; i < n; i++) {
					AtlasRegion region = regions.get(i);
					String key = region.name;
					textureRegions.put(key, region);
					if(isDebug) {
						L.wtf(key + " Succesfully Loaded..");
					}
				}
				disposables.add(atlas);
			}
			
			return atlas != null ? true : false;
		}

		@Override
		public void error() {
			// TODO Auto-generated method stub
			L.wtf("Error Loading Atlas File : "+file);
		}
	}
	
	public class SoundLoader implements Loader {
		private String file;
		private String name;

		public SoundLoader(String file,String name) {
			this.file = file;
			this.name = name;
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean load() {
			// TODO Auto-generated method stub
			Sound sound = Gdx.audio.newSound(Gdx.files.internal(file));
			if(sound!=null) {
				sounds.put(name, sound);
				disposables.add(sound);
			}
			return sound != null ? true : false;
		}
		
		@Override
		public void error() {
			// TODO Auto-generated method stub
			L.wtf("Error Loading Sound File : "+file);
		}
	}
	
	public class MusicLoader implements Loader {
		private String file;
		private String name;

		public MusicLoader(String file,String name) {
			this.file = file;
			// TODO Auto-generated constructor stub
			this.name = name;
		}
		
		@Override
		public boolean load() {
			// TODO Auto-generated method stub
			Music music = Gdx.audio.newMusic(Gdx.files.internal(file));
			if(music!=null){
				musics.put(name, music);
				disposables.add(music);
			}
			return music != null ? true : false;
		}
		
		@Override
		public void error() {
			// TODO Auto-generated method stub
			L.wtf("Error Loading Music File : "+file);
		}
	}
	
	public class FontLoader implements Loader {
		
		private String fntFile;
		private String pngFile;
		private String name;

		public FontLoader(String fntFile,String pngFile, String name) {
			this.fntFile = fntFile;
			this.pngFile = pngFile;
			// TODO Auto-generated constructor stub
			this.name = name;
		}
		
		public boolean load() {
			BitmapFont font = new BitmapFont(Gdx.files.internal(fntFile), Gdx.files.internal(pngFile), false);
			if(font!=null) {
				fonts.put(name, font);
				disposables.add(font);
				if(isDebug) {
					L.wtf(name + " Succesfully Loaded..");
				}
			}
			return font != null ? true : false;
		}
		
		@Override
		public void error() {
			// TODO Auto-generated method stub
			L.wtf("Error Loading Font File : "+fntFile+" "+pngFile);
		}
	}
	
	

}
