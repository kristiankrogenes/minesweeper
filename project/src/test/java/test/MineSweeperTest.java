package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Board;
import application.Game;
import application.Square;

public class MineSweeperTest {

	private Game game;
	private Board board;

	@BeforeEach
	public void setup() {
		this.game = new Game();
	}

	@Test
	public void checkIsEditable() {
		for (int i = 0; i < 100; i++) {
			assertTrue(game.isSquareEditable(i));
		}
	}

	@Test
	public void checkIsEditable2() {
		game.getBoard().getSquares().get(5).setIsEditable();
		assertFalse(game.getBoard().getSquares().get(5).getIsEditable());
	}

	@Test
	public void testIsBomb() {
		// Lager et square objekt med 100% sjanse for at det er en bombe
		Square sq = new Square(100);
		assertTrue(sq.getIsBomb());
	}

	@Test
	public void testIsNearBySquareEmpty() {

	}

}
