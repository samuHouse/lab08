package it.unibo.deathnote.impl;

public class DeathType {

    private String deathCause;
    private String deathDetails;

    public DeathType() {
        this.deathCause = "Cardiac arrest";
        this.deathDetails = "";
    }
    public String getDeathCause() {
        return deathCause;
    }
    public void setDeathCause(String deathCause) {
        this.deathCause = deathCause;
    }
    public String getDeathDetails() {
        return deathDetails;
    }
    public void setDeathDetails(String deathDetails) {
        this.deathDetails = deathDetails;
    }
    
    
}
