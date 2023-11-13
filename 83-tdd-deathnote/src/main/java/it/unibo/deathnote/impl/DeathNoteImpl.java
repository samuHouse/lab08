package it.unibo.deathnote.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private Map<String,DeathType> pages;
    private final long TIME_LAPSE = 6040L;
    private final long SHORT_TIME_LAPSE = 40L;
    private String lastName;
    private long time;

    public DeathNoteImpl() {
        this.pages = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRule(int ruleNumber) {
        if (ruleNumber > 1 && ruleNumber <= RULES.size()) {
            return RULES.get(ruleNumber-1);
        }
        throw new IllegalArgumentException("Rules must be searched between 1 and "+RULES.size()+".");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void writeName(String name) {
        if (this.pages.keySet().contains(name)) {
            throw new IllegalArgumentException("writing the same name once again won't have any effect...");
        }
        else if (name == null || name == "") {
            throw new NullPointerException("the name written can't be blank...");
        }
        this.pages.put(name, new DeathType());
            this.lastName = name;
            this.time = System.currentTimeMillis();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDeathCause(String cause) {
        if (System.currentTimeMillis()-this.time <= SHORT_TIME_LAPSE) {
            if (cause == null || cause == "") {
                throw new IllegalArgumentException("No blank cause should be written...");
            }
            if (this.lastName == null) {
                throw new IllegalStateException("No name written yet...");
            }
            time = System.currentTimeMillis();
            this.pages.get(this.lastName).setDeathCause(cause);
            return true;
        }
        System.out.println("Time exceded for death cause...");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeDetails(String details) {
        if (System.currentTimeMillis()-this.time <= TIME_LAPSE) {
            if (details == null || details == "") {
                throw new IllegalArgumentException("No blank details should be written...");
            }
            if (this.lastName == null) {
                throw new IllegalStateException("No name written yet...");
            }
            this.pages.get(this.lastName).setDeathDetails(details);
            return true;
        }
        System.out.println("Time exceded for death details...");
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathCause(String name) {
        return this.pages.get(name).getDeathCause();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDeathDetails(String name) {
        return this.pages.get(name).getDeathDetails();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNameWritten(String name) {
       return this.pages.keySet().contains(name);
    }

}
