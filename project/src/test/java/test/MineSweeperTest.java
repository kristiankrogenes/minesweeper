package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Game;

public class MineSweeperTest {

    private Game game;

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

}
