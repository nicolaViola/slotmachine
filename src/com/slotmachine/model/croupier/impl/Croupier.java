package com.slotmachine.model.croupier.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.slotmachine.model.GameTable;
import com.slotmachine.model.cardspack.Card;
import com.slotmachine.model.cardspack.ICardsPack;
import com.slotmachine.model.croupier.ICroupier;
import com.slotmachine.model.machine.state.IState;
import com.slotmachine.model.machine.state.annotations.Game;
import com.slotmachine.model.machine.state.annotations.NoCoin;
import com.slotmachine.model.machine.state.annotations.Winner;


@Component("croupier")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Croupier implements ICroupier{
	
	@Autowired
	@Winner
	private IState win;
	 
	@Autowired
	@Game
	private IState game;
	
	@Autowired
	@NoCoin
	private IState noCoin;
		
	private ICardsPack cardsPack;
	
	private static final int SHOOTS = 3;
	
	private int numberOfShoot = SHOOTS;
	
	@Override
	public GameTable play(){
		
		numberOfShoot--;
		List<Card> threeCards = getThreeCards(cardsPack);
		
		if(hasWin(threeCards)){
			resetShoot();
			return getGameTable(threeCards, win);
		}
		
		if(getNumberOfShoot() > 0)
			return getGameTable(threeCards, game);
		
		resetShoot();
		return getGameTable(threeCards, noCoin);
	}
	
	private GameTable getGameTable(List<Card> threeCards, IState state){
		return new GameTable(threeCards, state);
	}
	
	private boolean hasWin(List<Card> threeCards){
		Card firstCard = threeCards.get(0);
		Card secondCard = threeCards.get(1);
		Card thirdCard = threeCards.get(2);
		return ((firstCard.getValue() == secondCard.getValue()) && (secondCard.getValue() == thirdCard.getValue()));
	}
	
	private void resetShoot(){
		numberOfShoot = SHOOTS;
	}
	
	/**
	 * It takes three cards from the pack
	 * @param cardsPack
	 * @return
	 */
	private List<Card> getThreeCards(ICardsPack cardsPack){
		List<Card> cards = new ArrayList<>();
		cards.add(cardsPack.giveCards().get(0));
		cards.add(cardsPack.giveCards().get(1));
		cards.add(cardsPack.giveCards().get(2));
		return cards;
	}
	
	private int getNumberOfShoot() {
		return numberOfShoot;
	}

	@Autowired
	public void setCardsPack(ICardsPack cardsPack) {
		this.cardsPack = cardsPack; 
	}
	

	
}
