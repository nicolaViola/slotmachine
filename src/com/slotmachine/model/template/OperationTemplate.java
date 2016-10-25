package com.slotmachine.model.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.slotmachine.model.GameTable;
import com.slotmachine.model.machine.IMachine;
import com.slotmachine.model.machine.state.IState;

public abstract class OperationTemplate {
	
	@Autowired
	IMachine machine;
	
	public ResponseEntity<GameTable> progress(){
		IState state = machine.getActualMachineState();
		operation(state);
		GameTable gameTable = new GameTable(machine.getCards(), machine.getActualMachineState());
		return new ResponseEntity<GameTable>(gameTable, HttpStatus.OK);
	}
	
	abstract void operation(IState state);

}
