package application;

import java.util.Random;

public class Square {
	
	private boolean isBomb;
	private boolean isEditable = true;
	private boolean isFlagged = false;
	
	public Square(int difficulty) {
		
		int num = new Random().nextInt(101);
		
		if (num <= difficulty) {
			this.isBomb = true;
		} else {
			this.isBomb = false;
		}
	}
	
	public Square(boolean isBomb, boolean isEditable, boolean isFlagged) {
		this.isBomb = isBomb;
		this.isEditable = isEditable;
		this.isFlagged = isFlagged;
	}
	
	public boolean getIsBomb() {
		return this.isBomb;
	}
	
	public boolean getIsEditable() {
		return this.isEditable;
	}
	
	public boolean getIsFlagged() {
		return this.isFlagged;
	}
	
	public void setIsEditable() {
		this.isEditable = !this.isEditable;
	}
	
	public void setIsFlagged() {
		this.isFlagged = !this.isFlagged;
	}
}
