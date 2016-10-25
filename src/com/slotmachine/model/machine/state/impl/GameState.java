package com.slotmachine.model.machine.state.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.slotmachine.model.GameTable;
import com.slotmachine.model.croupier.ICroupier;
import com.slotmachine.model.machine.IMachine;
import com.slotmachine.model.machine.state.IState;
import com.slotmachine.model.machine.state.STATE_TYPE;
import com.slotmachine.model.machine.state.annotations.Game;



@Component("gameState")
@Game
public class GameState implements IState{
	
	private static final String name = "GAME";
	
	@Autowired
	private IMachine machine;
	
	
	private ICroupier croupier;

	@Override
	public void insertCoin() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void shoot() {
		
		GameTable gameTable = croupier.play();
		
		this.machine.setState(gameTable.getState());
		this.machine.setCards(gameTable.getCards());
	}

	@Override
	public void giveBackMoney() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void disponeMoney() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void startToPlay() {
		throw new UnsupportedOperationException();
	}
	
	@Autowired
	public void setCroupier(ICroupier croupier) {
		this.croupier = croupier;
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public STATE_TYPE getType() {
		return STATE_TYPE.GAME;
	}

}
