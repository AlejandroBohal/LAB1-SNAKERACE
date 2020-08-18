/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;
    private long t;
    private PrimeFinderThread pft[];
    private AtomicInteger primeCounter = new AtomicInteger(0);
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
        boolean isAlive = true;
        long startTime = System.currentTimeMillis();
        while (isAlive){
            if (System.currentTimeMillis() - startTime >= t){
                waitAll();
                System.out.println("Numero de primos encontrados hasta el momento:" + " "+ primeCounter);
                System.out.println("Presiona Enter para continuar");
                Scanner scanner = new Scanner(System.in);
                scanner.nextLine();
                resumeAll();
                startTime = System.currentTimeMillis();
            }


        }
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }
    public void waitAll(){
        for (int i=0; i<NTHREADS;i++){
            pft[i].setRunning(false);
        }
    }
    public void resumeAll(){
        for (int i=0; i<NTHREADS; i++){
            pft[i].setRunning(true);
        }
    }
}