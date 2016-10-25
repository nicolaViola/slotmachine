package slotmachine.model.machine.state.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slotmachine.model.machine.IMachine;
import slotmachine.model.machine.state.IState;
import slotmachine.model.machine.state.STATE_TYPE;
import slotmachine.model.machine.state.annotations.NoCoin;
import slotmachine.model.machine.state.annotations.Winner;


@Component("winnerState")
@Winner
public class WinnerState implements IState{
	
	private static final String name = "WINNER";
	
	@Autowired
	private IMachine machine;
	
	@Autowired
	@NoCoin
	private IState state;
	
	@Override
	public void disponeMoney() {
		this.machine.setState(state);
		this.machine.setCards(Collections.EMPTY_LIST);
	}
	
	@Override
	public void insertCoin() {
		throw new UnsupportedOperationException();
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
	public void startToPlay() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public STATE_TYPE getType() {
		return STATE_TYPE.WINNER;
	}

}
