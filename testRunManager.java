/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platform.test.run;

import com.platformda.iv.help.DialogHelper;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import javax.swing.JFrame;
import org.openide.util.Exceptions;

/**
 *
 * @author renjing
 */
public class testRunManager {
    
    enum State{
        RESUME,
        STOP,
        START,        
        PAUSE
    }
    
    private static volatile State state = State.START;
    private List<RunManagerListener> listeners = new LinkedList<>();
    
    private static testRunManager instance;
    
    
    private BlockingQueue<BaseTask> tasks;
    
    public testRunManager(){
        
    }
    
    public static testRunManager getInstance(){
        if(instance == null){
            instance = new testRunManager();
        }
        
        return instance;
    }
    
    public void addListener(RunManagerListener listener){
        this.listeners.add(listener);
    }
    
     public void removeListener(RunManagerListener listener){
        this.listeners.remove(listener);
    }
    
    public void addTasks( final BlockingQueue<BaseTask> tasks){
        this.tasks = tasks;        
    }
    
    
    public void run() {
//        final Object lock = new Object();
//        final BlockingQueue<BaseTask> tasks = new LinkedBlockingQueue<>();
        final BlockingQueue<BaseTask> queue2 = new LinkedBlockingQueue<>();
       

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {

                    try {
                        BaseTask task = tasks.take();
                        informWillBeRun(task);
                        task.runJob();
                        informFinishRun(task);
                        task = tasks.take();
                        informWillBeRun(task);
                        task.runJob();
                        informFinishRun(task);
                        task = tasks.take();
                        queue2.offer(task);


                    } catch (InterruptedException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        BaseTask task = queue2.take();
                        informWillBeRun(task);
                        task.runJob();
                        informFinishRun(task);
                    } catch (InterruptedException ex) {
                        Exceptions.printStackTrace(ex);
                    }

                }
            }
        });


       thread1.start();
       thread2.start();
       
    }
    
    private void informFinishRun(BaseTask task) {
        for (int index = 0; index < listeners.size(); index++) {
            listeners.get(index).itemFinishRun(task);
        }
    }
    
    private void informWillBeRun(BaseTask task) {
        for (int index = 0; index < listeners.size(); index++) {
            listeners.get(index).itemWillBeRun(task);
        }
    }
    
    public static void main(String[] args){
//        ConcurrentLinkedQueue<BaseTask> tasks = new  ConcurrentLinkedQueue<>();
//        for (int index = 0; index < 10; index++) {
//            tasks.add(new Task1());
//            tasks.add(new Task2());
//            tasks.add(new Task3());
//        }
//        BaseRunManager.getInstance().addTasks(tasks);
//        Thread thread1 = BaseRunManager.getInstance().getNewThread(TaskThread.Type.BeforeWards);
//        Thread thread2 = BaseRunManager.getInstance().getNewThread(TaskThread.Type.AfterWards);
//        
//        //start
//        thread1.start();
//        thread2.start();
        
      
//        PriorityBlockingQueue<BaseTask> queue = new PriorityBlockingQueue<>();
//        queue.comparator();
//        
////        queue.comparator(new Comparator<BaseTask> (){
////
////            @Override
////            public int compare(BaseTask o1, BaseTask o2) {
////                return 1;
////            }
////            
////        });
//        ExecutorService pool = Executors.newFixedThreadPool(2);
//        
//        for(int index =0;index<10;index++){
//            Task1 task1 = new Task1(queue);
//            Task2 task2 = new Task2(queue);
//            Task3 task3 = new Task3(queue);
//            queue.add(task1);
//            queue.add(task2);
//            queue.add(task3);
//        }
//        
//        Task1 obj = new Task1(queue);
////        pool.execute(obj); //start download and place on queue once completed
//        pool.submit(task);
////        Data data = queue.take(); //get completely downloaded item
        
   
     
       
     
       
       
        
        
//        final BlockingQueue<BaseTask> tasks = new LinkedBlockingQueue<>();
//        final BlockingQueue<BaseTask> queue2 = new LinkedBlockingQueue<>();
//        for (int index = 0; index < 10; index++) {
//            tasks.add(new BaseTask("Task1"));
//            tasks.add(new BaseTask("Task2"));
//            tasks.add(new BaseTask("Task3"));
//        }
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        BaseTask task = tasks.take();
//                        task.run();
//                        task = tasks.take();
//                        task.run();
//                        task = tasks.take();
//                        queue2.offer(task);
//                    } catch (InterruptedException ex) {
////                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        });
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        BaseTask task = queue2.take();
//                        task.run();
//                    } catch (InterruptedException ex) {
////                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        });
//        thread2.start();
//        thread1.start();
//    }
//
//    private static class BaseTask implements Runnable {
//
//        private final String name;
//
//        public BaseTask(String name) {
//            this.name = name;
//        }
//
//        @Override
//        public void run() {
//            System.out.println(name + " ["
//                    + Thread.currentThread().getName() + "]");
//        }
//        
        
        
        int a = 0;
        
    }
    
}
