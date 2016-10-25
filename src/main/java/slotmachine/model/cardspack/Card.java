package slotmachine.model.cardspack;

public class Card {
	
	private int value;
	private String suit;
	private String descriptionValue;
	
	public Card(int value, String descriptionValue, String suit) {
		this.value = value;
		this.suit = suit;
		this.descriptionValue = descriptionValue;
	}

	public int getValue() {
		return value;
	}

	public String getSuit() {
		return suit;
	}

	public String getDescriptionValue() {
		return descriptionValue;
	}
	
	@Override
	public String toString() {
		StringBuilder card = new StringBuilder();
		card.append(descriptionValue).append(" ").append(suit);
		return card.toString();
	}
	
	

}
