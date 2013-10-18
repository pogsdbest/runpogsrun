package com.pogs.runpogsrun.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.brashmonkey.spriter.Spriter;
import com.brashmonkey.spriter.player.SpriterPlayer;
import com.brashmonkey.spriter.xml.FileHandleSCMLReader;
import com.game.framework.utils.L;

public class Assets {

	private static Assets instance;

	public static Music currentMusic;
	public AssetManager manager;

	public SpriterPlayer player;

	public SpriterLoader loader;

	// singleton
	public static Assets getInstance() {
		if (instance == null) {
			instance = new Assets();
		}
		return instance;
	}

	private Assets() {
		super();
	}

	public void initialize() {

		// pre load assets
		manager = new AssetManager();

		// load texture packs
		manager.load("gfx/assets.pack", TextureAtlas.class);
		
		//load spriter

		loader = new SpriterLoader(true);
		Spriter spriter = FileHandleSCMLReader.getSpriter(Gdx.files.internal("scml/pogs.scml"), loader);
		player = new SpriterPlayer(spriter,0,loader);
		player.setAnimation("idle", 1, 10);
		
	}

	public <T> T get(String name) {
		return manager.get(name);
	}

	public TextureRegion[] getAtlasAnimation(String atlas, String name,
			int start, int end) {
		TextureAtlas textureAtlas = get(atlas);
		TextureRegion[] regions = new TextureRegion[(end + 1) - start];
		int index = 0;
		for (int i = start; i < end + 1; i++) {
			regions[index] = textureAtlas.findRegion(name + i);
			index++;
		}
		return regions;
	}

	public void playMusic(Music music) {
		if (currentMusic != null) {
			currentMusic.stop();
			currentMusic = null;
		}
		if (Config.soundOn) {
			if (!music.isPlaying())
				music.play();
			currentMusic = music;
		}
	}

	public void playSound(Sound sound) {
		if (Config.soundOn) {
			sound.play();
		}
	}

	public void pauseMusic(Music music) {
		if (music.isPlaying())
			music.pause();
	}

	public void stopMusic(Music music) {
		if (music.isPlaying())
			music.stop();
	}

	public String[] getText(String fileName, String index) {
		String text = "";

		FileHandle handle = Gdx.files.external(fileName);
		if(!handle.exists()) {
			L.wtf(fileName+" doesn't exist.");
			return new String[0];
		}
		text = handle.readString();

		String part = "";
		ArrayList<String> stringArray = new ArrayList<String>();
		for (int i = 0; i < text.length(); i++) {

			if (text.charAt(i) != '\n') {
				part += text.charAt(i);
			} else {
				stringArray
						.add(new String(part.substring(0, part.length() - 1)));
				part = "";
			}
			if (i >= text.length() - 1) {
				stringArray.add(new String(part));
			}
		}
		boolean start = false;
		ArrayList<String> contents = new ArrayList<String>();
		for (int i = 0; i < stringArray.size(); i++) {
			if (start && stringArray.get(i).equals("#end")) {
				break;
			}
			if (start) {
				contents.add(stringArray.get(i));
			}
			if (stringArray.get(i).equals(index)) {
				start = true;
			}
			
		}
		return contents.toArray(new String[contents.size()]);
	}

	public void setText(String fileName, String index, String[] contents) {
		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new OutputStreamWriter(Gdx.files.external(fileName).write(false)));
			out.write(index);
			out.newLine();
			for (int i = 0; i < contents.length; i++) {
				out.write(contents[i]);
				out.newLine();
			}
			out.write("#end");
			out.newLine();
			out.close();
		} catch (Throwable e) {
			L.wtf("ERROR SAVING IN TEXT FILE");
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
			}
		}
	}

	public void dispose() {

		manager.dispose();
	}

}
