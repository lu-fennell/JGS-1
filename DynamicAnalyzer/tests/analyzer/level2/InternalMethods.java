package analyzer.level2;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import logging.L2Logger;

import org.junit.Before;
import org.junit.Test;

import tests.testClasses.TestSubClass;
import analyzer.level2.HandleStmtForTests;
import analyzer.level2.SecurityLevel;

public class InternalMethods {

	Logger LOGGER = L2Logger.getLogger();
	
	@Before
	public void init() {
		HandleStmtForTests.init();
	}

	@Test
	public void checkLocalPCTest() {
		LOGGER.log(Level.INFO, "LOCAL PC TEST STARTED");

		HandleStmtForTests hs = new HandleStmtForTests();
		hs.addLocal("int_x");
		
		// Level(x) = LOW, Level(lpc) = LOW
		assertEquals(true, hs.checkLocalPC("int_x"));
		
		// Level(x) = HIGH, Level(lpc) = LOW
		hs.makeLocalHigh("int_x");
		assertEquals(true, hs.checkLocalPC("int_x"));
		
		// Level(x) = HIGH, Level(lpc) = HIGH
		hs.setLocalPC(SecurityLevel.HIGH);
		assertEquals(true, hs.checkLocalPC("int_x"));
		
		// Level(x) = LOW, Level(lpc) = HIGH
		hs.makeLocalLow("int_x");
		assertEquals(false, hs.checkLocalPC("int_x"));
		
	    hs.close();	
	    
		
	    LOGGER.log(Level.INFO, "LOCAL PC TEST FINISHED");
	}
	
	@Test
	public void joinLocalsTest() {
		LOGGER.log(Level.INFO, "JOIN LOCALS TEST STARTED");

		HandleStmtForTests hs = new HandleStmtForTests();
		hs.addLocal("int_x", SecurityLevel.LOW);
		hs.addLocal("int_y", SecurityLevel.HIGH);
		hs.addLocal("int_z", SecurityLevel.LOW);
		assertEquals(SecurityLevel.LOW, hs.joinLocals("int_x"));
		assertEquals(SecurityLevel.HIGH, hs.joinLocals("int_x", "int_y"));		
		assertEquals(SecurityLevel.HIGH, hs.joinLocals("int_x", "int_y", "int_z"));
		

	    hs.close();	
		
	    LOGGER.log(Level.INFO, "JOIN LOCALS TEST FINISHED");
	}
	
	
	@Test
	public void argumentsListTest() {
		LOGGER.log(Level.INFO, "ARGUMENTS LIST TEST STARTED");
			
		HandleStmtForTests hs = new HandleStmtForTests();
		
		hs.addLocal("TestSubClass_xy");
		TestSubClass xy = new TestSubClass();
		
		hs.addLocal("int_i1");
		hs.addLocal("int_i2");
		hs.addLocal("int_i3");
		int i1 = 0;
		int i2 = 0;
		int i3 = 0;
		
		hs.storeArgumentLevels("int_i1", "int_i2", "int_i3");
		xy.methodWithArgs(i1, i2, i3);
		
		hs.close();
		
		LOGGER.log(Level.INFO, "ARGUMENTS LIST TEST FINISHED");
	}

}