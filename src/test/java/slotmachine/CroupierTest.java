package slotmachine;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import  slotmachine.configuration.AutomaticScanStateConfiguration;
import slotmachine.configuration.SwaggerConfig;
import  slotmachine.model.cardspack.Card;
import  slotmachine.model.cardspack.ICardsPack;
import  slotmachine.model.croupier.impl.Croupier;
import  slotmachine.model.machine.state.IState;
import  slotmachine.model.machine.state.annotations.Game;
import  slotmachine.model.machine.state.annotations.NoCoin;
import  slotmachine.model.machine.state.annotations.Winner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AutomaticScanStateConfiguration.class) 
public class CroupierTest {
	
	@Autowired
	private Croupier croupier;
	
	@Autowired
	@Winner
	private IState winner;
	
	@Autowired
	@Game
	private IState game;
	
	@Autowired
	@NoCoin
	private IState noCoin;
	

	@Test
	public void play_ThePackGivesThreeEqualCards_theStateIsWinner() {
		
		Card card1 = new Card(10, "10", "HEART");
		Card card2 = new Card(10, "10", "SPADE");
		Card card3 = new Card(10, "10", "DIAMOND");
		
		List<Card> cards = new ArrayList<>();
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		
		ICardsPack cardsPack = Mockito.mock(ICardsPack.class);
		
		when(cardsPack.giveCards()).thenReturn(cards);
		
		croupier.setCardsPack(cardsPack);
		
		assertEquals(croupier.play().getState(), winner);
		
		
	}
	
	@Test
	public void play_ThePackGivesThreeDifferentCardsButYouHaveMoreShoots_theStateIsGame() {
		
		Card card1 = new Card(10, "10", "HEART");
		Card card2 = new Card(11, "J", "SPADE");
		Card card3 = new Card(12, "Q", "DIAMOND");
		
		List<Card> cards = new ArrayList<>();
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		
		ICardsPack cardsPack = Mockito.mock(ICardsPack.class);
		
		when(cardsPack.giveCards()).thenReturn(cards);
		
		croupier.setCardsPack(cardsPack);
		
		assertEquals(croupier.play().getState(), game);
	}
	
	@Test
	public void play_ThePackGivesThreeDifferentCardsButYouDontHaveAnyShootsMore_theStateIsNoCoin() {
		
		Card card1 = new Card(10, "10", "HEART");
		Card card2 = new Card(11, "J", "SPADE");
		Card card3 = new Card(12, "Q", "DIAMOND");
		
		List<Card> cards = new ArrayList<>();
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		
		ICardsPack cardsPack = Mockito.mock(ICardsPack.class);
		
		when(cardsPack.giveCards()).thenReturn(cards);
		
		croupier.setCardsPack(cardsPack);
		
		croupier.play();
		croupier.play();
		
		assertEquals(croupier.play().getState(), noCoin);
	}

}
