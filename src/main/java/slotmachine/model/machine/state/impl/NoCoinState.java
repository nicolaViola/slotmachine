package slotmachine.model.machine.state.impl;


import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import slotmachine.model.machine.IMachine;
import slotmachine.model.machine.state.IState;
import slotmachine.model.machine.state.STATE_TYPE;
import slotmachine.model.machine.state.annotations.NoCoin;
import slotmachine.model.machine.state.annotations.Ready;


@Component("noCoinState")
@Primary
@NoCoin
public class NoCoinState implements IState{
	
	private static final String name = "NO_COIN";
	
	@Autowired
	private IMachine machine;
	
	@Autowired
	@Ready
	private IState state;

	@Override
	public void insertCoin() {
		this.machine.setState(state);
		this.machine.setCards(Collections.EMPTY_LIST);
	}

	@Override
	public void shoot() {
		throw new UnsupportedOperationException();
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

	@Override
	public String toString() {
		return name;
	}

	@Override
	public STATE_TYPE getType() {
		return STATE_TYPE.NO_COIN;
	}
	

}
