package application;

import java.util.Random;

public class Square {
	
	private boolean isBomb;
	private boolean isEditable = true;
	
	public Square(int difficulty) {
		
		int num = new Random().nextInt(101);
		
		if (num <= difficulty) {
			this.isBomb = true;
		} else {
			this.isBomb = false;
		}
	}
	
	public boolean getIsBomb() {
		return this.isBomb;
	}
	
	public boolean getIsEditable() {
		return this.isEditable;
	}
	
	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
}
