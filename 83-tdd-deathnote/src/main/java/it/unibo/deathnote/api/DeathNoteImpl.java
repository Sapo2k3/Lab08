package it.unibo.deathnote.api;

import java.nio.channels.IllegalSelectorException;
import java.util.*;

public class DeathNoteImpl implements DeathNote{
    
    private Map<String, Death> deathnote = new HashMap<>();
    private Death death = new Death();
    private String lastNameWritten = "";
    private Long TIMER;

    public boolean noNameWritten(){
        if(deathnote.values().isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule(final int ruleNumber) {
        if(ruleNumber <= 0 || ruleNumber>RULES.size()){
            throw new IllegalArgumentException("The number should be grater than 0 and smaller or equals to " + RULES.size());
        }
        return RULES.get(ruleNumber);
    }

    @Override
    public void writeName(String name) {
        TIMER = System.currentTimeMillis();
        if (name.isBlank()){
            throw new IllegalArgumentException("The provided name is null");
        }
        death.cause="Heart attack";
        death.setDetails(null);
        deathnote.put(name, death);
        lastNameWritten = name;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(lastNameWritten.isBlank() || cause.isEmpty()){
            throw new IllegalStateException("There is no name written in this DeathNote or the cause is null");
        }
        if(System.currentTimeMillis() - TIMER <= 40){
            death.setDeath(cause);
            deathnote.replace(lastNameWritten, death);
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(String details) {
         if(lastNameWritten.isEmpty() || details.isEmpty()){
            throw new IllegalStateException("There is no name written in this Death Note or the details are null");
        }
        if(System.currentTimeMillis() - TIMER <= 6040){
            death.setDetails(details);
            deathnote.replace(lastNameWritten, death);
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(String name) {
        if(!isNameWritten(name)){
            throw new IllegalArgumentException("The provided name is not written in this Death Note");
        }
        else {
            return deathnote.get(name).cause;
        }
    }

    @Override
    public String getDeathDetails(String name) {
        if(!isNameWritten(name)){
            throw new IllegalArgumentException("The provided name is not written in this Death Note");
        }
        else {
            String cause = deathnote.get(name).cause;
            return deathnote.get(name).getDetails(cause);
        }
    }

    @Override
    public boolean isNameWritten(String name) {
        return deathnote.containsKey(name);
    }
    
    private static class Death {
        private Map<String, String> death = new HashMap<>();
        private String cause;

        public void setDeath(final String cause){
            death.put(cause, "");
            this.cause = cause;
        }

        public void setDetails(final String details){
            death.replace(cause, null, details);
        }

        public String getDetails(final String cause){
            return this.death.get(cause);
        }
    }

}
