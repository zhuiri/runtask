/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platform.test.run;

/**
 *
 * @author renjing
 */
public interface RunManagerListener {
    void itemWillBeRun(BaseTask task);
    void itemFinishRun(BaseTask task);
    
}
