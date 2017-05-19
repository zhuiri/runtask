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
public class Task2 extends BaseTask implements ShouldRunBeforeWardsJob {
    int num =1000000000;
    
    public Task2() {
        super();
    }

    public Task2(Queue queue) {
        super(queue);
    }

    @Override
    public void runJob() {
        //prepare
        double start = System.currentTimeMillis();
        System.out.println("[" + Thread.currentThread().getName() + "]"+"start runing Task2 "+start/1.0e9);        
        doJob();        
        System.out.println("[" + Thread.currentThread().getName() + "]"+"finish runing Task2 "+(System.currentTimeMillis()-start));
    }

    @Override
    public void doJob() {
        for(int index =0;index<num;index++){
            int a = index;
            a = a*1;
        }
    }
    
    @Override
    String getName() {
        return "Task2";
    }

}
