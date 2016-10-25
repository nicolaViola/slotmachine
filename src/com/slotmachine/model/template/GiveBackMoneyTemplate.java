package com.slotmachine.model.template;

import org.springframework.stereotype.Component;

import com.slotmachine.model.machine.state.IState;

@Component
public class GiveBackMoneyTemplate extends OperationTemplate{

	@Override
	void operation(IState state) {
		state.giveBackMoney();
	}

}
