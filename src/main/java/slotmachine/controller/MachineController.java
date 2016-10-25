package slotmachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import slotmachine.model.GameTable;
import slotmachine.model.TransactionalError;
import slotmachine.model.machine.IMachine;
import slotmachine.model.template.DisponeMoneyTemplate;
import slotmachine.model.template.GiveBackMoneyTemplate;
import slotmachine.model.template.InsertCoinTemplate;
import slotmachine.model.template.ShootTemplate;
import slotmachine.model.template.StartToPlayTemplate;

@RestController
public class MachineController{ 
	
	private IMachine machine;
	
	private InsertCoinTemplate insertCoinTemplate;
	
	private GiveBackMoneyTemplate giveBackMoneyTemplate;
	
	private StartToPlayTemplate startToPlayTemplate;
	
	private ShootTemplate shootTemplate;
	
	private DisponeMoneyTemplate disponeMoneyTemplate;
	
	
	@RequestMapping(value = "/insertCoin")
	public ResponseEntity<GameTable> insertCoin(){
		return insertCoinTemplate.progress();
	}
	
	@RequestMapping(value = "/giveBackMoney")
	public ResponseEntity<GameTable> giveBackMoney(){
		return giveBackMoneyTemplate.progress();
	}
	
	@RequestMapping(value = "/startToPlay")
	public ResponseEntity<GameTable> startToPlay(){
		return startToPlayTemplate.progress();
	}
	
	@RequestMapping(value = "/shoot")
	public ResponseEntity<GameTable> shoot(){
		return shootTemplate.progress();
	}
	
	@RequestMapping(value = "/disponeMoney")
	public ResponseEntity<GameTable> disponeMoney(){
		return disponeMoneyTemplate.progress();
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<TransactionalError> unsupportedOperation(){
		StringBuilder errorMessage = new StringBuilder("This operation is not supported for the state ");
		errorMessage.append(machine.getActualMachineState().toString());
		TransactionalError error = new TransactionalError(errorMessage.toString());
		return new ResponseEntity<TransactionalError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Autowired
	public void setMachine(IMachine machine) {
		this.machine = machine;
	}

	@Autowired
	public void setInsertCoinTemplate(InsertCoinTemplate insertCoinTemplate) {
		this.insertCoinTemplate = insertCoinTemplate;
	}

	@Autowired
	public void setGiveBackMoneyTemplate(GiveBackMoneyTemplate giveBackMoneyTemplate) {
		this.giveBackMoneyTemplate = giveBackMoneyTemplate;
	}

	@Autowired
	public void setStartToPlayTemplate(StartToPlayTemplate startToPlayTemplate) {
		this.startToPlayTemplate = startToPlayTemplate;
	}

	@Autowired
	public void setShootTemplate(ShootTemplate shootTemplate) {
		this.shootTemplate = shootTemplate;
	}

	@Autowired
	public void setDisponeMoneyTemplate(DisponeMoneyTemplate disponeMoneyTemplate) {
		this.disponeMoneyTemplate = disponeMoneyTemplate;
	}
	
	

	

}
