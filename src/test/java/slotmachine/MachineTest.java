package slotmachine;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import slotmachine.configuration.AutomaticScanStateConfiguration;
import slotmachine.model.GameTable;
import slotmachine.model.cardspack.Card;
import slotmachine.model.croupier.ICroupier;
import slotmachine.model.croupier.impl.Croupier;
import slotmachine.model.machine.IMachine;
import slotmachine.model.machine.state.annotations.Game;
import slotmachine.model.machine.state.annotations.NoCoin;
import slotmachine.model.machine.state.annotations.Ready;
import slotmachine.model.machine.state.annotations.Winner;
import slotmachine.model.machine.state.impl.GameState;
import slotmachine.model.machine.state.impl.NoCoinState;
import slotmachine.model.machine.state.impl.ReadyState;
import slotmachine.model.machine.state.impl.WinnerState;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AutomaticScanStateConfiguration.class) 
public class MachineTest {
	
	@Autowired
	private IMachine machine;
	
	@Autowired
	@Ready
	private ReadyState ready;
		
	@Autowired
	@NoCoin
	private NoCoinState noCoin;
	
	@Autowired
	@Winner
	private WinnerState winner;
	
	@Autowired
	@Game
	private GameState game;
	
	
	@Test
	public void insertCoin_actualMachineState_ready() {
		machine.setState(noCoin);
		machine.insertCoin();
		assertEquals(machine.getActualMachineState(), ready);
	}
	
	@Test
	public void changeStatusFromReadyToNoCoin_actualMachinetateShouldBeANoCoin() {
		machine.setState(ready);
		machine.giveBackMoney();
		assertEquals(machine.getActualMachineState(), noCoin);
	}
	
	
	@Test
	public void changeStatusFromReadyToGame_actualMachinetateShouldBeAGame() {
		machine.setState(ready);
		machine.startToPlay();
		assertEquals(machine.getActualMachineState(), game);
	}
	
	@Test
	public void changeStatusToNoCoin_actualMachinetateShouldBeANoCoin() {
		machine.setState(winner);
		machine.disposeMoney();
		assertEquals(machine.getActualMachineState(), noCoin);
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasANocoinState_ExceuteDisponeMoney_UnsupportedOperationExceptionIsThrown() {
		machine.setState(noCoin);
		machine.disposeMoney();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasANocoinState_ExceuteGiveCoinBack_UnsupportedOperationExceptionIsThrown() {
		machine.setState(noCoin);
		machine.giveBackMoney();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasANocoinState_ExceuteShoot_UnsupportedOperationExceptionIsThrown() {
		machine.setState(noCoin);
		machine.shoot();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasAReadyState_ExceuteDisponeMoney_UnsupportedOperationExceptionIsThrown() {
		machine.setState(ready);
		machine.disposeMoney();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasAReadyState_ExceuteInsertCoin_UnsupportedOperationExceptionIsThrown() {
		machine.setState(ready);
		machine.insertCoin();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasAWinnewState_ExceuteInsertCoin_UnsupportedOperationExceptionIsThrown() {
		machine.setState(winner);
		machine.insertCoin();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasAWinnewState_ExceuteShoot_UnsupportedOperationExceptionIsThrown() {
		machine.setState(winner);
		machine.shoot();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasAWinnewState_ExceuteGiveMoneyBack_UnsupportedOperationExceptionIsThrown() {
		machine.setState(winner);
		machine.giveBackMoney();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasAGameState_ExceuteInsertCoin_UnsupportedOperationExceptionIsThrown() {
		machine.setState(game);
		machine.insertCoin();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasAGameState_ExceuteGiveBackMoney_UnsupportedOperationExceptionIsThrown() {
		machine.setState(game);
		machine.giveBackMoney();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasAGameState_ExceuteStartToPlay_UnsupportedOperationExceptionIsThrown() {
		machine.setState(game);
		machine.startToPlay();
	}
	
	@Test(expected = UnsupportedOperationException.class) 
	public void theMachineHasAGameState_ExceuteDisposeMoney_UnsupportedOperationExceptionIsThrown() {
		machine.setState(game);
		machine.disposeMoney();
	}
	
	@Test
	public void theMachineHasAGameState_ExecuteShootAndWin_actualMachinetateShouldBeAWin(){
		Croupier mockCroupier = mock(Croupier.class);
		
		GameTable winnerTable = new GameTable(new ArrayList<Card>(), winner);
		when(mockCroupier.play()).thenReturn(winnerTable);
		
		game.setCroupier(mockCroupier);
		machine.setState(game);
		machine.shoot();
		assertEquals(winner, machine.getActualMachineState());
	}
	
	@Test
	public void theMachineHasAGameState_ExecuteShootAndDontWinButYouHaveOtherShoots_actualMachinetateShouldBeAGame(){
		Croupier mockCroupier = mock(Croupier.class);
		
		GameTable gameTable = new GameTable(new ArrayList<Card>(), game);
		when(mockCroupier.play()).thenReturn(gameTable);
		
		game.setCroupier(mockCroupier);
		machine.setState(game);
		machine.shoot();
		assertEquals(game, machine.getActualMachineState());
	}
	
	@Test
	public void theMachineHasAGameState_ExecuteShootAndDontWinAndYouHaveGinishedTheShoot_actualMachinetateShouldBeANoCoin(){
		ICroupier mockCroupier = mock(Croupier.class);
		
		GameTable noCoinTable = new GameTable(new ArrayList<Card>(), noCoin);
		when(mockCroupier.play()).thenReturn(noCoinTable);
		
		game.setCroupier(mockCroupier);
		machine.setState(game);
		machine.shoot();
		assertEquals(noCoin, machine.getActualMachineState());
	}

}
