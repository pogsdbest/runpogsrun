package com.game.framework.display.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

public class DialogWindow extends Window {
	
	private TextButton button;

	public DialogWindow(Skin skin ,String title,String message,String buttonText) {
		// TODO Auto-generated constructor stub
		super(title,skin);
		
		button = new TextButton("QUIT",skin);
		Label label = new Label(message,skin);
		
		setWidth(label.getWidth()*1.5f);
		setHeight(button.getHeight() * 2.5f);
		
		Table table = new Table();
		table.add(label).uniform();
		table.row();
		table.add(button).center();
		add(table);
	}

	public TextButton getButton() {
		return button;
	}
}
