package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.Game;
import application.Square;

public class MineSweeperTest {

    private Game game;

    // Har lagret et game vi kjenner til i denne stringen så vi vet hvor alle
    // bomber osv. Legger til bildet av hvordan brettet ser ut
    private String gameData = "false-true-false,true-true-false,true-true-false,false-true-false,false-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,true-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,false-true-false,true-true-false,false-true-false,";

    @BeforeEach
    public void setup() {
	this.game = new Game(gameData);
    }

    @Test
    public void checkIsEditable() {
	for (int i = 0; i < 100; i++) {
	    assertTrue(game.isSquareEditable(i));
	}
    }

    @Test
    public void checkIsEditable2() {
	game.openSquare(4);
	assertFalse(game.getBoard().getSquares().get(4).getIsEditable());
    }

    @Test
    public void testIsBomb() {
	// Lager et square objekt med 100% sjanse for at det er en bombe
	Square sq = new Square(100);
	assertTrue(sq.getIsBomb());
    }

    @Test
    public void openSquare() {
	game.openSquare(0);
	game.openSquare(4);
	game.openSquare(1);
	assertEquals(2, game.getBoard().getSquares().get(0).getNearbyBombs());
	assertEquals(0, game.getBoard().getSquares().get(4).getNearbyBombs());
	assertTrue(game.isSquareBomb(1));

    }

    @Test
    public void openNearbySquares() {
	game.openSquare(70);
	assertFalse(game.isSquareEditable(60));
	assertFalse(game.isSquareEditable(61));
	assertFalse(game.isSquareEditable(62));
	assertFalse(game.isSquareEditable(70));
	assertFalse(game.isSquareEditable(71));
	assertFalse(game.isSquareEditable(72));
	assertFalse(game.isSquareEditable(80));
	assertFalse(game.isSquareEditable(81));
	assertFalse(game.isSquareEditable(82));
	assertTrue(game.isSquareEditable(50));
	assertTrue(game.isSquareEditable(51));
	assertTrue(game.isSquareEditable(52));
	assertTrue(game.isSquareEditable(90));
	assertTrue(game.isSquareEditable(91));
	assertTrue(game.isSquareEditable(92));
    }

    @Test
    public void testFlagSquare() {
	game.flagSquare(0);
	assertFalse(game.isSquareEditable(0));
	assertTrue(game.getBoard().getSquares().get(0).getIsFlagged());
	game.flagSquare(0);
	assertTrue(game.isSquareEditable(0));
	assertFalse(game.getBoard().getSquares().get(0).getIsFlagged());
    }

    @Test
    public void testIsGameLost() {
	game.openSquare(1);
	assertTrue(game.getIsGameLost());
    }

    @Test
    public void testUpdateRemainingBombs() {
	game.flagSquare(1);
	game.flagSquare(2);
	game.flagSquare(11);
	assertEquals(13, game.getRemainingBombs());
	game.flagSquare(3);
	game.flagSquare(4);
	game.flagSquare(5);
	game.flagSquare(6);
	game.flagSquare(7);
	game.flagSquare(8);
	game.flagSquare(9);
	game.flagSquare(10);
	game.flagSquare(12);
	game.flagSquare(14);
	game.flagSquare(15);
	game.flagSquare(16);
	game.flagSquare(17);
	assertEquals(0, game.getRemainingBombs());
	game.flagSquare(18);
	game.flagSquare(19);
	assertEquals(-2, game.getRemainingBombs());
    }

    @Test
    public void testIsSquareInBoard() {
	assertFalse(game.isSquareInBoard(10, 0));
	assertFalse(game.isSquareInBoard(-1, 0));
	assertFalse(game.isSquareInBoard(0, 10));
	assertFalse(game.isSquareInBoard(0, -1));
    }

    @Test
    public void testOpenSquare2() {
	game.openSquare(0);
	game.openSquare(0);
    }

    // Åpner alle nødvendige felter og flagger alle bombene og tester om man vinner
    @Test
    public void testIsGameWon() {
	game.openSquare(4);
	game.openSquare(9);
	game.openSquare(59);
	game.openSquare(70);
	game.openSquare(83);
	game.openSquare(0);
	game.openSquare(10);
	game.openSquare(12);
	game.openSquare(17);
	game.openSquare(21);
	game.openSquare(22);
	game.openSquare(31);
	game.openSquare(32);
	game.openSquare(37);
	game.openSquare(39);
	game.openSquare(40);
	game.openSquare(41);
	game.openSquare(42);
	game.openSquare(43);
	game.openSquare(47);
	game.openSquare(51);
	game.openSquare(52);
	game.openSquare(53);
	game.openSquare(67);
	game.openSquare(77);
	game.openSquare(86);
	game.openSquare(87);
	game.openSquare(89);
	game.openSquare(90);
	game.openSquare(95);
	game.openSquare(96);
	game.openSquare(97);
	game.openSquare(99);
	game.flagSquare(1);
	game.flagSquare(2);
	game.flagSquare(7);
	game.flagSquare(11);
	game.flagSquare(20);
	game.flagSquare(27);
	game.flagSquare(30);
	game.flagSquare(33);
	game.flagSquare(38);
	game.flagSquare(50);
	game.flagSquare(57);
	game.flagSquare(63);
	game.flagSquare(85);
	game.flagSquare(88);
	game.flagSquare(91);
	game.flagSquare(98);
	assertTrue(game.isGameWon());
    }
}
