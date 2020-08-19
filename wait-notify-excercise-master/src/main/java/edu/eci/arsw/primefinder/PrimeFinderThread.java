package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Prime finder thread.
 */
public class PrimeFinderThread extends Thread{


	/**
	 * The A.
	 */
	int a, /**
	 * The B.
	 */
	b;
	
	private List<Integer> primes;
	private AtomicInteger primeCounter;
	private boolean running;

	/**
	 * Instantiates a new Prime finder thread.
	 *
	 * @param a            the a
	 * @param b            the b
	 * @param primeCounter the prime counter
	 */
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
					System.out.println(i);
                    primes.add(i);
                    primeCounter.getAndIncrement();
                }
            }
	}

	/**
	 * Is prime boolean.
	 *
	 * @param n the n
	 * @return the boolean
	 */
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

	/**
	 * Gets primes.
	 *
	 * @return the primes
	 */
	public List<Integer> getPrimes() {
		return primes;
	}

	/**
	 * Is running boolean.
	 *
	 * @return the boolean
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * Pause.
	 */
	public void pause(){
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets running.
	 *
	 * @param running the running
	 */
	public void setRunning(boolean running) {
		if (running){
			synchronized (this){
				notify();
			}
		}
		this.running = running;
	}
}
