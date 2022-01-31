package com.boatcorp.boatgame;

import com.badlogic.gdx.Game;
import com.boatcorp.boatgame.screens.PlayScreen;


public class BoatGame extends Game {
	
	@Override
	public void create () {
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
	}
}
