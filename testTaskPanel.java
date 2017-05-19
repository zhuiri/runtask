/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platform.test.run;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JFrame;

/**
 *
 * @author renjing
 */
public class testTaskPanel {
    
    public static void main(String[] args) {
        BlockingQueue<BaseTask> tasks = new LinkedBlockingQueue<>();
        for (int index = 0; index < 10; index++) {
            tasks.add(new Task1());
            tasks.add(new Task2());
            tasks.add(new Task3());
        }
        
        
        TaskPanel panel = new TaskPanel(tasks);

        JFrame frame = new JFrame();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.show();
    }
    
}
