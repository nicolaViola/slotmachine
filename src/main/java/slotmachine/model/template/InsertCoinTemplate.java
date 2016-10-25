package slotmachine.model.template;

import org.springframework.stereotype.Component;

import slotmachine.model.machine.state.IState;

@Component
public class InsertCoinTemplate extends OperationTemplate{

	@Override
	void operation(IState state) {
		state.insertCoin();
	}

}
