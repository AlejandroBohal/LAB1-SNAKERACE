package snakepackage;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import enums.GridSize;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author jd-
 *
 */
public class SnakeApp {

    private static SnakeApp app;
    private static String messages[] = new String[]{"",""};
    public static final int MAX_THREADS = 80;
    Snake[] snakes = new Snake[MAX_THREADS];
    private static final Cell[] spawn = {
        new Cell(1, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2,
        3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, GridSize.GRID_HEIGHT - 2),
        new Cell(1, 3 * (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell(GridSize.GRID_WIDTH - 2, (GridSize.GRID_HEIGHT / 2) / 2),
        new Cell((GridSize.GRID_WIDTH / 2) / 2, 1),
        new Cell(3 * (GridSize.GRID_WIDTH / 2) / 2,
        GridSize.GRID_HEIGHT - 2)};
    private JFrame frame;
    private boolean firstRun = false;
    private static Board board;
    private int nr_selected = 0;
    private Thread[] thread = new Thread[MAX_THREADS];
    private JButton start = new JButton("Start");
    private JButton pause = new JButton("Pause");
    private JButton resume = new JButton("Resume");
    private int maxSize = 0;
    private AtomicInteger firstDead = new AtomicInteger(-1);
    private AtomicBoolean firstDeadB = new AtomicBoolean(false);


    public SnakeApp() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("The Snake Race");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setSize(618, 640);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17,
                GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 40);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);
        board = new Board();
        frame.add(board,BorderLayout.CENTER);
        JPanel actionsBPabel=new JPanel();
        actionsBPabel.setLayout(new FlowLayout());


        actionsBPabel.add(start);
        actionsBPabel.add(pause);
        actionsBPabel.add(resume);

        frame.add(actionsBPabel,BorderLayout.SOUTH);
        this.prepareActions();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        app = new SnakeApp();
        app.init();
    }

    private void init() {
        for (int i = 0; i != MAX_THREADS; i++) {
            snakes[i] = new Snake(i + 1, spawn[i%8], i + 1, firstDead, firstDeadB);
            snakes[i].addObserver(board);
            thread[i] = new Thread(snakes[i]);
        }
        while (true) {
            int x = 0;
            for (int i = 0; i != MAX_THREADS; i++) {
                if (snakes[i].isSnakeEnd() == true) {
                    x++;
                }
            }
            if (x == MAX_THREADS) {
                break;
            }
        }
        synchronized (this){
            try {
                wait(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread (snake) status:");

        for (int i = 0; i != MAX_THREADS; i++) {
            System.out.println("["+i+"] :"+thread[i].getState());
        }
        

    }

    private void prepareActions() {
        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                startThreads();
            }
        });
        resume.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                resumeThreads();
            }
        });
        pause.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                pauseThreads();
            }
        });
    }

    /**
     * Resume los hilos
     */
    private void resumeThreads() {
        for(Snake s:snakes){
            s.setPaused(false);
        }
    }

    /**
     * Pausa los hilos
     */
    private void pauseThreads() {
        for(Snake s:snakes){
            s.setPaused(true);
            if(s.getSize()>maxSize){
                maxSize = s.getSize();
            }

        }
        String dead = firstDead.get()!=-1?String.valueOf(firstDead.get()):"No dead snakes yet";
        messages = new String[]{String.valueOf("Max Size: "+maxSize),String.valueOf("First Dead: "+dead)};
    }


    public static String[] getMessages() {
        return messages;
    }

    private void startThreads(){
        for (Thread t:thread){
            if (!firstRun) {
                t.start();
            }
        }
        firstRun = true;

    }

    public static SnakeApp getApp() {
        return app;
    }


}
