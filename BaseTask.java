/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platform.test.run;

import java.util.Queue;

/**
 *
 * @author renjing
 */
public abstract class BaseTask {
    enum State{
        FINISH,
        NOT
    }
    
    protected Queue queue;
    
    protected State state;
    
    public BaseTask(){
       this(null); 
    }
    
    public BaseTask(Queue queue){
        this.queue = queue;
    }
    
    public State getState(){
        return state;
    }
    
    public void setState(State state){
        this.state = state;
    }
    
    abstract void  runJob();
    
    abstract void doJob();
    
    abstract String getName();
    
}
