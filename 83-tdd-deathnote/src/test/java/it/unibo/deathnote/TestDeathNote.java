package it.unibo.deathnote;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.api.DeathNoteImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertLinesMatch;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static java.lang.Thread.sleep;

import org.junit.jupiter.api.Assertions;

class TestDeathNote {

    @Test
    public void testGetRule(){
        DeathNote deathnote = new DeathNoteImpl();
        try {
            int ruleRequest = -1;
            int ruleRequest2 = 0;
            deathnote.getRule(ruleRequest);
            deathnote.getRule(ruleRequest2);
            Assertions.fail("The rule requested is invalid but the program doesn't throw an exception");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }

    @Test
    public void testEmptyRules(){
        DeathNote deathnote = new DeathNoteImpl();
        for (String rule : deathnote.RULES) {
                assertNotNull(rule);
                assertFalse(rule.isBlank());
        }
    }
    
    @Test 
    public void testWriteAName(){
        DeathNoteImpl deathnote = new DeathNoteImpl();
        assertTrue(deathnote.noNameWritten());
        deathnote.writeName("Marco Massa");
        assertTrue(deathnote.isNameWritten("Marco Massa"));
        assertFalse(deathnote.isNameWritten(""));
    }

    @Test
    public void testDeathTimer() throws InterruptedException{
        DeathNote deathnote = new DeathNoteImpl();
        try {
            deathnote.writeDeathCause("Incidente");
            Assertions.fail("The program must throw an exception becouse there is no name written for this cause");
        } catch (IllegalStateException e) {
            assertNotEquals(e, "");
            assertNotNull(e);
        }
        deathnote.writeName("Marco Massa");
        assertTrue(deathnote.getDeathCause("Marco Massa") == "Heart attack");
        deathnote.writeName("Mario Rossi");
        assertTrue(deathnote.writeDeathCause("Karting accident"));
        assertTrue(deathnote.getDeathCause("Mario Rossi") == "Karting accident");
        sleep(100);
        assertFalse(deathnote.writeDeathCause("Shooting"));
    }

    @Test
    public void testDetailsTimer() throws InterruptedException{
        DeathNote deathnote = new DeathNoteImpl();
        try {
            deathnote.writeDetails("Hitting a wall");
            Assertions.fail("The program must throw an exception becouse there is no name written for this cause");
        } catch (IllegalStateException e) {
            assertNotEquals(e, "");
            assertNotNull(e);
        }
        deathnote.writeName("Mario Rossi");
        assertTrue(deathnote.writeDeathCause("Ran for too long"));
        deathnote.writeName("Marco Massa");
        sleep(6100);
        assertFalse(deathnote.writeDetails("Hitting a wall during a kart race"));
    }
}