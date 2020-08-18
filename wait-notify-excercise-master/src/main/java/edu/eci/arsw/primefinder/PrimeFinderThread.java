package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFinderThread extends Thread{

	
	int a,b;
	
	private List<Integer> primes;
	private AtomicInteger primeCounter;
	private boolean running;
	public PrimeFinderThread(int a, int b,AtomicInteger primeCounter) {
		super();
		this.primes = new LinkedList<>();
		this.a = a;
		this.b = b;
		this.primeCounter = primeCounter;
		this.running = true;
	}

	@Override
	public void run(){
            for (int i= a;i < b;i++){						
                if (isPrime(i)){
                	while(!running){
                		synchronized (this){
                			pause();
						}
					}
                    primes.add(i);
                    primeCounter.getAndIncrement();
                    System.out.println(i);
                }
            }
	}
	
	boolean isPrime(int n) {
	    boolean ans;
            if (n > 2) { 
                ans = n%2 != 0;
                for(int i = 3;ans && i*i <= n; i+=2 ) {
                    ans = n % i != 0;
                }
            } else {
                ans = n == 2;
            }
	    return ans;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

	public boolean isRunning() {
		return running;
	}
	public void pause(){
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void setRunning(boolean running) {
		if (running){
			synchronized (this){
				notify();
			}
		}
		this.running = running;
	}
}
