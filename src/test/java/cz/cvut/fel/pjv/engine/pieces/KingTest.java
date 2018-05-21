/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.pjv.engine.pieces;

import cz.cvut.fel.pjv.engine.Colour;
import cz.cvut.fel.pjv.engine.board.Board;
import cz.cvut.fel.pjv.engine.board.moves.Move;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author vior
 */
public class KingTest
{
    
    public KingTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of calculateMoves method, of class King.
     */
    @Test
    public void testCalculateMoves()
    {
	System.out.println("calculateMoves");
	Board board = new Board();
	King instance = new King(5,5, Colour.WHITE);
	String expResult = "[Kg3, Ke3, Kf4, Kg4, Ke4]";
	Collection<Move> result = instance.calculateMoves(board);
	assertEquals(expResult, result.toString());
    }

    /**
     * Test of toString method, of class King.
     */
    @Test
    public void testToString()
    {
	System.out.println("toString");
	King instance = new King(5,5, Colour.WHITE);
	String expResult = "K";
	String result = instance.toString();
	assertEquals(expResult, result);
    }


    
}
