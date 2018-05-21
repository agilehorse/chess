/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.pjv.engine.board;

import java.util.Arrays;
import java.util.Collection;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author vior
 */
@RunWith(Parameterized.class)
public class BoardUtilsTest
{
    private final int row;
    private final int column;
    private final boolean t1rslt;
    private final String t2rslt;
    
    
    public BoardUtilsTest(int row, int column, boolean t1rslt, String t2rslt)
    {
	this.row = row;
	this.column = column;
	this.t1rslt = t1rslt;
	this.t2rslt = t2rslt;
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @Parameterized.Parameters
    public static Collection PrimeNumbers()
    {
	return Arrays.asList(new Object[][]
	{
	    {7,5,true, "f1"},
	    {75,59,false, null},
	    {5,0,true, "a3"},
	    {500,51651616,false, null},
	    {1,0,true, "a7"},
	    {1,1,true, "b7"},
	    {4,7,true, "h4"},
	    {2,6,true, "g6"},

	});
    }
    
    /**
     * Test of isValidTileCoordinate method, of class BoardUtils.
     */
    @Test
    public void testIsValidTileCoordinate()
    {
	System.out.println("isValidTileCoordinate");
	
	boolean result = BoardUtils.isValidTileCoordinate(row, column);
	
	assertEquals(t1rslt, result);

    }


    /**
     * Test of getPositionAtCoordinate method, of class BoardUtils.
     */
    @Test
    public void testGetPositionAtCoordinate()
    {
	System.out.println("getPositionAtCoordinate");
	if(t2rslt != null)
	{String result = BoardUtils.getPositionAtCoordinate(row, column);
	
	
	    assertEquals(t2rslt, result);
	}
    }
  
}
