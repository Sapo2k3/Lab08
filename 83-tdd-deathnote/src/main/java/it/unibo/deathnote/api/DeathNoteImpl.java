package it.unibo.deathnote.api;

import java.nio.channels.IllegalSelectorException;
import java.util.*;

public class DeathNoteImpl implements DeathNote{
    private String name;
    private String cause;
    
    public DeathNoteImpl(final String name, private String cause){
        this.name=name;
        this.cause=cause;
    }

    @Override
    public String getRule(int ruleNumber) {
        throw new IllegalArgumentException("The number should be grater than 0 and smaller or equals to " + RULES.size());
    }

    @Override
    public void writeName(String name) {
        throw new NullPointerException("The given name is null");
    }

    @Override
    public boolean writeDeathCause(String cause) {
        throw new IllegalStateException("There is no name written in this Death Note or the cause is null");
    }

    @Override
    public boolean writeDetails(String details) {
        throw new IllegalStateException("There is no name written in this Death Note or the cause is null");
    }

    @Override
    public String getDeathCause(String name) {
        // TODO Auto-generated method stub
        throw new IllegalArgumentException("The provided name is not written in this Death Note");
    }

    @Override
    public String getDeathDetails(String name) {
        // TODO Auto-generated method stub
        throw new IllegalArgumentException("The provided name is not written in this Death Note");
    }

    @Override
    public boolean isNameWritten(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isNameWritten'");
    }
    
}
