package it.unibo.deathnote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {
    private DeathNoteImpl deathN;
    private final String NAME = "Giorgo";
    private final String OTHER_NAME = "Marco";

    /**
     * Creates a new deathnote implementation with no name written name on it
     */
    @BeforeEach
    public void setUp() {
        this.deathN =  new DeathNoteImpl();
    }

    /**
     * Tries to search an Impossible rule index, sees if the method is handled correctly
     */
    @Test
    public void testRuleNumber() {
        try {
            deathN.getRule(-1);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Rules must be searched between 1 and "+DeathNote.RULES.size()+".", e.getMessage());
        }
    }

    /**
     * Checks if the set of rule doesn't contain any irregular Strings
     */
    @Test
    public void testRules() {
        for (String rule : DeathNote.RULES) {
            if (rule == null || rule == "") {
                Assertions.fail();
            }
        }
    }

    /**
     * Checks if the name writing procedure is done in the right way, in particular, no empty or null String can be
     * written
     */
    @Test
    public void testWriteName() {
        Assertions.assertFalse(this.deathN.isNameWritten(NAME));
        this.deathN.writeName(NAME);
        Assertions.assertTrue(this.deathN.isNameWritten(NAME));
        Assertions.assertFalse(this.deathN.isNameWritten(OTHER_NAME));
        Assertions.assertNotEquals("",NAME);
        Assertions.assertFalse(this.deathN.isNameWritten(""));
    }

    @Test
    public void testWriteDeathCause() throws InterruptedException {
        try {
            this.deathN.writeDeathCause("Crossfire in a gunfight between n*****");
        } catch (IllegalStateException e) {
            Assertions.assertEquals("No name written yet...", e.getMessage());
        }
        this.deathN.writeName(NAME);
        Assertions.assertEquals("Cardiac arrest", this.deathN.getDeathCause(NAME));
        this.deathN.writeName(OTHER_NAME);
        Assertions.assertTrue(this.deathN.writeDeathCause("Karting accident"));
        Assertions.assertEquals("Karting accident", this.deathN.getDeathCause(OTHER_NAME));
        Thread.sleep(100L);
        Assertions.assertFalse(this.deathN.writeDeathCause("Watching a Surreal Power minecraft walkthrough while driving"));
    }

    /**
    * 5. Only if the cause of death is written within the next 6 seconds and 40 milliseconds of writing the death's details, it will happen.
      * check that writing the death details before writing a name throws the correct exception
      * write the name of a human in the notebook
      * verify that the details of the death are currently empty
      * set the details of the death to "ran for too long"
      * verify that death details have been set correctly (returned true, and the details are indeed "ran for too long")
      * write the name of another human in the notebook
      * sleep for 6100ms
      * try to change the details
      * verify that the details have not been changed
     * @throws InterruptedException
     */
    @Test
    public void testWriteDeathDetails() throws InterruptedException {
        try {
            deathN.writeDetails("Stabs his n*ts");
        } catch (IllegalStateException e) {
            Assertions.assertEquals("No name written yet...", e.getMessage());
        }
        this.deathN.writeName(NAME);
        Assertions.assertEquals("", this.deathN.getDeathDetails(NAME));
        Assertions.assertTrue(this.deathN.writeDetails("Ran for too long"));
        Assertions.assertEquals("Ran for too long", this.deathN.getDeathDetails(NAME));
        this.deathN.writeName(OTHER_NAME);
        Thread.sleep(6100L);
        Assertions.assertFalse(this.deathN.writeDetails("Watching a Surreal Power minecraft walkthrough while driving"));
    }
}