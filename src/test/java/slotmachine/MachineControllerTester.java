package slotmachine;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import  slotmachine.configuration.AutomaticScanStateConfiguration;
import  slotmachine.controller.MachineController;
import  slotmachine.model.GameTable;
import  slotmachine.model.TransactionalError;
import  slotmachine.model.cardspack.Card;
import  slotmachine.model.croupier.impl.Croupier;
import  slotmachine.model.machine.IMachine;
import  slotmachine.model.machine.state.IState;
import  slotmachine.model.machine.state.annotations.Game;
import  slotmachine.model.machine.state.annotations.NoCoin;
import  slotmachine.model.machine.state.annotations.Ready;
import  slotmachine.model.machine.state.annotations.Winner;
import  slotmachine.model.machine.state.impl.GameState;
import  slotmachine.model.machine.state.impl.WinnerState;
import  slotmachine.model.template.DisponeMoneyTemplate;
import  slotmachine.model.template.GiveBackMoneyTemplate;
import  slotmachine.model.template.InsertCoinTemplate;
import  slotmachine.model.template.ShootTemplate;
import  slotmachine.model.template.StartToPlayTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes=AutomaticScanStateConfiguration.class) 
public class MachineControllerTester {
	
	@Autowired
	@NoCoin
	private IState noCoin;
	
	@Autowired
	@Ready
	private IState ready;
	
	@Autowired
	@Game
	private GameState game;
	
	@Autowired
	@Winner
	private WinnerState winner;
	
	@Autowired
	private IMachine machine;
	



	@Autowired
	private  InsertCoinTemplate insertCoinTemplate;
	@Test
	public void insertCoin_AMachineInNoCoinState_returnAnGameTableWithEmptyCardsAndReadyState() throws Exception {
		
		GameTable expectedTable = new GameTable(Collections.EMPTY_LIST, ready);

		machine.setState(noCoin);
		
		MachineController controller = new MachineController();
		controller.setMachine(machine);
		controller.setInsertCoinTemplate(insertCoinTemplate);
		
		MockMvc mockMvc = standaloneSetup(controller).build();
		mockMvc.perform(get("/insertCoin"))
			   .andExpect(status().isOk())
			   .andExpect(content().contentType(APPLICATION_JSON_UTF8))
			   .andExpect(content().string(getJsonGameTable(expectedTable)));
	}


	@Autowired
	private GiveBackMoneyTemplate giveBackMoneyTemplate;
	@Test
	public void giveBackMoney_AMachineInReadyState_returnAnGameTableWithEmptyCardsAndNoCoinState() throws Exception {
		
		GameTable expectedTable = new GameTable(Collections.EMPTY_LIST, noCoin);
		
		machine.setState(ready);
		
		MachineController controller = new MachineController();

		controller.setMachine(machine);
		controller.setGiveBackMoneyTemplate(giveBackMoneyTemplate);
		
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(get("/giveBackMoney"))
			   .andExpect(status().isOk())
			   .andExpect(content().contentType(APPLICATION_JSON_UTF8))
			   .andExpect(content().string(getJsonGameTable(expectedTable)));
	}


	@Autowired
	private StartToPlayTemplate startToPlayTemplate;
	@Test
	public void startToPlay_AMachineInReadyState_returnAnGameTableWithEmptyCardsAndGameState() throws Exception {
		
		GameTable expectedTable = new GameTable(Collections.EMPTY_LIST, game);
		
		machine.setState(ready);

		MachineController controller = new MachineController();
		controller.setMachine(machine);
		controller.setStartToPlayTemplate(startToPlayTemplate);
		
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(get("/startToPlay"))
			   .andExpect(status().isOk())
			   .andExpect(content().contentType(APPLICATION_JSON_UTF8))
			   .andExpect(content().string(getJsonGameTable(expectedTable)));
	}
	
	@Autowired
	private DisponeMoneyTemplate disponeMoneyTemplate;
	@Test
	public void disponeMoney_AMachineInWinState_returnANoCoinState() throws Exception {
		
		GameTable expectedTable = new GameTable(Collections.EMPTY_LIST, noCoin);
		
		machine.setState(winner);

		MachineController controller = new MachineController();
		controller.setMachine(machine);
		controller.setDisponeMoneyTemplate(disponeMoneyTemplate);
		
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(get("/disponeMoney"))
			   .andExpect(status().isOk())
			   .andExpect(content().contentType(APPLICATION_JSON_UTF8))
			   .andExpect(content().string(getJsonGameTable(expectedTable)));
	}
	
	@Autowired
	private ShootTemplate shootTemplate;
	@Test
	public void shoot_AMachineInGameState_returnAnGameTableWithThreeCardsAndGameState() throws Exception {
		
		Card card1 = new Card(10, "10", "HEART");
		Card card2 = new Card(10, "10", "SPADE");
		Card card3 = new Card(10, "10", "DIAMOND");
		
		List<Card> cards = new ArrayList<>();
		cards.add(card1);
		cards.add(card2);
		cards.add(card3);
		
		GameTable expectedTable = new GameTable(cards, game);
				
		Croupier mockCroupier = mock(Croupier.class);
		
		GameTable gameTable = new GameTable(cards, game);
		when(mockCroupier.play()).thenReturn(gameTable);
		
		game.setCroupier(mockCroupier);	
		
		machine.setState(game);

		MachineController controller = new MachineController();
		controller.setMachine(machine);
		controller.setShootTemplate(shootTemplate);
		
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(get("/shoot"))
			   .andExpect(status().isOk())
			   .andExpect(content().contentType(APPLICATION_JSON_UTF8))
			   .andExpect(content().string(getJsonGameTable(expectedTable)));
	}
	
	
	@Test
	public void disponeMoney_AMachineInReadyState_returnError() throws Exception {
		
		StringBuilder errorMessage = new StringBuilder("This operation is not supported for the state ");
		errorMessage.append(ready.toString());
		
		TransactionalError expectederror = new TransactionalError(errorMessage.toString());
		
		machine.setState(ready);

		MachineController controller = new MachineController();
		controller.setMachine(machine);
		
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(get("/disponeMoney"))
			   .andExpect(status().isInternalServerError())
			   .andExpect(content().contentType(APPLICATION_JSON_UTF8))
			   .andExpect(content().string(getJsonGameTable(expectederror)));
	}
	
	private String getJsonGameTable(Object expectedObject) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(expectedObject);
	}
	
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),                        
            Charset.forName("utf8")                     
           );
	

}
