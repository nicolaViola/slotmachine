package slotmachine.model.machine.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slotmachine.model.cardspack.Card;
import slotmachine.model.machine.IMachine;
import slotmachine.model.machine.state.IState;


@Component
public class Machine implements IMachine{
	
	private IState state;
	
	private List<Card> cards;
	

	public IState getActualMachineState() {
		return this.state;
	}

	public void insertCoin() {
		this.state.insertCoin();
	}

	public void shoot() {
		this.state.shoot();
	}

	@Override
	public void disposeMoney() {
		this.state.disponeMoney();
	}

	@Override
	public void giveBackMoney() {
		this.state.giveBackMoney();
	}

	@Autowired
	public void setState(IState state) {
		this.state = state;
	}

	@Override
	public void startToPlay() {
		this.state.startToPlay();
	}

	@Override
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	@Override
	public List<Card> getCards() {
		return this.cards;
	}


}
