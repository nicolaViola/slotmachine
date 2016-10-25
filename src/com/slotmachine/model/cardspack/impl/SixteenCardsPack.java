package com.slotmachine.model.cardspack.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.slotmachine.model.cardspack.Card;
import com.slotmachine.model.cardspack.ICardsPack;


@Component("sixteenCardsPack")
public class SixteenCardsPack implements ICardsPack{
	
	private List<Card> pack = new ArrayList<>();
	
	public SixteenCardsPack(){
		init();
	}

	@Override
	public List<Card> giveCards() {
		Collections.shuffle(pack);
		return pack;
	}
	
	private void init() {
		pack.add(new Card(11, "Jack", "HEART"));
		pack.add(new Card(12, "Queen", "HEART"));
		pack.add(new Card(13, "King", "HEART"));
		pack.add(new Card(14, "Ace", "HEART"));
		
		pack.add(new Card(11, "Jack", "SPADE"));
		pack.add(new Card(12, "Queen", "SPADE"));
		pack.add(new Card(13, "King", "SPADE"));
		pack.add(new Card(14, "Ace", "SPADE"));
		
		pack.add(new Card(11, "Jack", "DIAMOND"));
		pack.add(new Card(12, "Queen", "DIAMOND"));
		pack.add(new Card(13, "King", "DIAMOND"));
		pack.add(new Card(14, "Ace", "DIAMOND"));
		
		pack.add(new Card(11, "Jack", "CLUB"));
		pack.add(new Card(12, "Queen", "CLUB"));
		pack.add(new Card(13, "King", "CLUB"));
		pack.add(new Card(14, "Ace", "CLUB"));
		
		Collections.shuffle(pack);
	}

}
