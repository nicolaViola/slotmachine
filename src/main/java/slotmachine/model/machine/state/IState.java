package slotmachine.model.machine.state;

public interface IState {
	
	void insertCoin();
	
	void giveBackMoney();
	
	void startToPlay();
	
	void shoot();
	
	void disponeMoney();
	
	STATE_TYPE getType();
	

}
