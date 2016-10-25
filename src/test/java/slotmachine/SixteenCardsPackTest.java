package slotmachine;

import java.util.List;

import org.junit.Test;

import slotmachine.model.cardspack.Card;
import slotmachine.model.cardspack.impl.SixteenCardsPack;



public class SixteenCardsPackTest {

	@Test
	public void test() {
		
		SixteenCardsPack pack = new SixteenCardsPack();
		
		for(int i = 0; i <= 100; i++){
		
			List<Card> cards = pack.giveCards();
			for(Card card : cards)
				System.out.println(card);
			System.out.println("---------------------------------------------");
			
		}
		
	}

}
