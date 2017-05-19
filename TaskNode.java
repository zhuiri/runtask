/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platform.test.run;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author renjing
 */
public class TaskNode extends DefaultMutableTreeNode{
    
    private BaseTask task;
   
    public BaseTask getTask(){
        return task;
    }
    
    public TaskNode(Object name,BaseTask task){
        super(name);
        this.task = task;
    }
    
}
