package com.slotmachine.model;

import java.util.List;

import com.slotmachine.model.cardspack.Card;
import com.slotmachine.model.machine.state.IState;

public class GameTable {
	
	private List<Card> cards;
	private IState state;

	public GameTable(List<Card> cards, IState state) {
		this.cards = cards;
		this.state = state;
	}

	public List<Card> getCards() {
		return cards;
	}

	public String getStateName() {
		return this.state.toString();
	}

	public IState getState() {
		return state;
	}

	
	

}
