/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.pjv.engine.board;

import cz.cvut.fel.pjv.engine.pieces.Piece;
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
public class TileTest
{
    
    public TileTest()
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
     * Test of createTile method, of class Tile.
     */
    @Test
    public void testCreateTileReturnsTile()
    {
//	arrange
	System.out.println("createTile");
	int tileRow = 5;
	int tileColumn = 5;
	Piece piece = null;
	Tile expResult = Tile.createTile(5, 5, null);
	
//	act
	Tile result = Tile.createTile(tileRow, tileColumn, piece);

//	asserts
	assertEquals(expResult, result);
    }
    
    /**
     * Test of toString method, of class Tile.
     */
    @Test
    public void testToStringReturnsSlash()
    {
//	arrange
	System.out.println("toString");
	int tileRow = 5;
	int tileColumn = 5;
	Piece piece = null;
	String expResult = "-";
	
//	act
	Tile tile = Tile.createTile(tileRow, tileColumn, piece);
	String result = tile.toString();
	
//	assert
	assertEquals(expResult, result);
    }
      
}
