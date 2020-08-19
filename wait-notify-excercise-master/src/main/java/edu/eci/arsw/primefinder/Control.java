/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Control.
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;
    private long t;
    private PrimeFinderThread pft[];
    private AtomicInteger primeCounter = new AtomicInteger(0);
    private boolean isAlive = true;
    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];
        this.t = TMILISECONDS;
        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA,primeCounter);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1,primeCounter);
    }

    /**
     * New control control.
     *
     * @return the control
     */
    public static Control newControl() {
        return new Control();
    }

    public static Control newControl(long t){
        Control control = new Control();
        control.setT(t);
        return control;
    }
    @Override
    public void run() {
        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
        }
        long startTime = System.currentTimeMillis();
        while (isAlive){
            if (System.currentTimeMillis() - startTime >= t) {
                this.isAlive = false;
                waitAll();
                System.out.println("Numero de primos encontrados hasta el momento:" + " " + primeCounter);
                System.out.println("Presiona Enter para continuar");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                resumeAll();
                if (areAlive()) {
                    this.isAlive = true;
                }
                startTime = System.currentTimeMillis();


            }

        }

    }

    /**
     * Gets t.
     *
     * @return the t
     */
    public long getT() {
        return t;
    }

    /**
     * Sets t.
     *
     * @param t the t
     */
    public void setT(long t) {
        this.t = t;
    }

    /**
     * Wait all.
     */
    public void waitAll(){
        for (int i=0; i<NTHREADS;i++){
            pft[i].setRunning(false);
        }
    }

    /**
     * Resume all.
     */
    public void resumeAll(){
        for (int i=0; i<NTHREADS; i++){
            pft[i].setRunning(true);
        }
    }

    /**
     * Are alive boolean.
     *
     * @return the boolean
     */
    public boolean areAlive(){
        boolean areAlive;
        for (int i=0; i<NTHREADS; i++){
            if (pft[i].isAlive()){
                return true;
            }
        }return false;
    }
}
