package com.slotmachine.model.machine.state.impl;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.slotmachine.model.machine.IMachine;
import com.slotmachine.model.machine.state.IState;
import com.slotmachine.model.machine.state.STATE_TYPE;
import com.slotmachine.model.machine.state.annotations.Game;
import com.slotmachine.model.machine.state.annotations.NoCoin;
import com.slotmachine.model.machine.state.annotations.Ready;


@Component("readyState")
@Ready
public class ReadyState implements IState{
	
	private static final String name = "READY";
	
	@Autowired
	private IMachine machine;

	@Autowired
	@NoCoin
	private IState noCoin;
	
	@Autowired
	@Game
	private IState game;
		
	
	@Override
	public void shoot() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void giveBackMoney() {
		this.machine.setState(this.noCoin);
		this.machine.setCards(Collections.EMPTY_LIST);
	}
	
	@Override
	public void insertCoin() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void disponeMoney() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void startToPlay() {
		this.machine.setState(this.game);
		this.machine.setCards(Collections.EMPTY_LIST);
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public STATE_TYPE getType() {
		return STATE_TYPE.READY;
	}


}
