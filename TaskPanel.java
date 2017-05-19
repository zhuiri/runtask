/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.platform.test.run;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;

/**
 *
 * @author renjing
 */
public class TaskPanel extends JPanel implements RunManagerListener{
    
    
    private JToolBar toolbar;
    private JButton start;
    private JButton step;
    private JButton pause;
    private JButton stop;
    private JButton clear;
    private JButton setting;
    
    BlockingQueue<BaseTask> tasks;
    JTree tasksTree; 
    public TaskPanel(BlockingQueue<BaseTask> tasks){
        this.tasks = tasks;
        initComponent();
    }
    
    private void initComponent(){
        
        configToolbar();
        
        //add tasks tree
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("tasks");
//        BlockingQueue<BaseTask> clonetasks = new LinkedBlockingQueue<>();
//        clonetasks.addAll(tasks);
        for (BaseTask task : tasks) {
            TaskNode tasknode = new TaskNode(task.getName(), task);
            root.add(tasknode);
        }
//        while(clonetasks.size()>0){
//            try {
//                BaseTask task = clonetasks.take();
//                DefaultMutableTreeNode tasknode = new DefaultMutableTreeNode(task.getName());
//                root.add(tasknode);
//                
//            } catch (InterruptedException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//            
//        }
        
        
        tasksTree = new JTree(root);
        DefaultTreeCellRenderer render = new DefaultTreeCellRenderer();
        render.setLeafIcon(new ImageIcon(ImageUtilities.loadImage("com/platform/test/resources/task.png")));
        tasksTree.setCellRenderer(render);
//        tasksTree.setRootVisible(false);
        tasksTree.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    TreePath p = tasksTree.getPathForLocation(e.getPoint().x, e.getPoint().y);
                    if (p == null) {
                        return;
                    }
                    Object o = p.getLastPathComponent();
//                    if (o instanceof ItemNode) {
//                        ItemNode node = (ItemNode) o;
//                    }
                }
            }

        });
        configToolbar();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder());
        JScrollPane sc = new JScrollPane(tasksTree);
        sc.setBorder(null);
        add(sc, BorderLayout.CENTER);
        add(toolbar, BorderLayout.NORTH);
        
        testRunManager.getInstance().addListener(this);
 

  

    

    }
    
    
    private void updateListContent() {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tasksTree.getModel().getRoot();
        ((DefaultTreeModel) tasksTree.getModel()).reload();
//        GUIUtil.setTreeExpanded(tasksTree, true);
    }
    
    
     @Override
    public void itemWillBeRun(BaseTask task) {
        final TreePath p = getPath(task);
        if (p != null) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        tasksTree.addSelectionPath(p);
                        tasksTree.scrollPathToVisible(p);
//                        tasksTree.updateUI();
                    }
                });
            } catch (InterruptedException | InvocationTargetException ignore) {
                //ignore
            }
        }
    }

    @Override
    public void itemFinishRun(BaseTask task) {
        final TreePath p = getPath(task);
        if (p != null) {
            try {
                SwingUtilities.invokeAndWait(new Runnable() {
                    @Override
                    public void run() {
                        tasksTree.removeSelectionPath(p);
//                        tasksTree.updateUI();
                    }
                });
            } catch (InterruptedException | InvocationTargetException ignore) {
                //ignore
            }
        }
    }
    
     private TreePath getPath(BaseTask task) {
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tasksTree.getModel().getRoot();
        Enumeration en = root.children();

        while (en.hasMoreElements()) {
            TaskNode tc = (TaskNode) en.nextElement();
            if (tc != null&& tc.getTask() == task) {
                return new TreePath(new Object[]{root,tc});
            }
        }
        return null;
    }
    
    
    private void configToolbar() {
        toolbar = new JToolBar();

        start = new JButton(new ImageIcon(ImageUtilities.loadImage("com/platform/test/resources/tr-start.png")));
        start.setActionCommand("doStartUp");
        start.setToolTipText("Start");
        start.addActionListener(al);

        step = new JButton(new ImageIcon(ImageUtilities.loadImage("com/platform/test/resources/tr-step.png")));
        step.setActionCommand("step");
        step.setToolTipText("Step");
        step.addActionListener(al);

        pause = new JButton(new ImageIcon(ImageUtilities.loadImage("com/platform/test/resources/tr-pause.png")));
        pause.setActionCommand("pause");
        pause.setToolTipText("Pause");
        pause.addActionListener(al);

        stop = new JButton(new ImageIcon(ImageUtilities.loadImage("com/platform/test/resources/tr-stop.png")));
        stop.setActionCommand("stop");
        stop.setToolTipText("Stop");
        stop.addActionListener(al);

        clear = new JButton(new ImageIcon(ImageUtilities.loadImage("com/platform/test/resources/clear.png")));
        clear.setActionCommand("clear");
        clear.setToolTipText("Clear");
        clear.addActionListener(al);

        toolbar.add(start);
        toolbar.add(step);
        toolbar.add(pause);
        toolbar.add(stop);
        toolbar.addSeparator();
        toolbar.add(clear);

//        setting = new JButton(new ImageIcon(getClass().getResource("/image/tr-setting.png")));
//        setting.setActionCommand("setting");
//        setting.setToolTipText("Setting");
//        setting.addActionListener(al);
//        toolbar.add(setting);
        toolbar.setFloatable(false);
    }
    
    private ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                switch (e.getActionCommand()) {
//                    case "setting":
//                        showSettingDialog();
//                        break;
                    case "doStartUp":
                        testRunManager.getInstance().addTasks(tasks);
                        testRunManager.getInstance().run();
                        
                        break;
                    case "step":
                        break;
                    case "pause":
                        break;
                    case "stop":
                        break;
                    case "clear":
//                        RunManager.clear();
                }

            }catch (Exception e1){
                e1.printStackTrace();
            }

        }
    };
    
}
