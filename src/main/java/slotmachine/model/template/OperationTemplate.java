package slotmachine.model.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import slotmachine.model.GameTable;
import slotmachine.model.machine.IMachine;
import slotmachine.model.machine.state.IState;

public abstract class OperationTemplate {
	
	@Autowired
	IMachine machine;
	
	public final ResponseEntity<GameTable> progress(){
		IState state = machine.getActualMachineState();
		operation(state);
		GameTable gameTable = new GameTable(machine.getCards(), machine.getActualMachineState());
		return new ResponseEntity<GameTable>(gameTable, HttpStatus.OK);
	}
	
	abstract void operation(IState state);

}
