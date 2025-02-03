package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {

    int a, b;

    private List<Integer> primes;

    private boolean paused = false;

    private final Object lock;

    public PrimeFinderThread(int a, int b, Object lock) {
        super();
        this.primes = new LinkedList<>();
        this.a = a;
        this.b = b;
        this.lock = lock;
    }

    @Override
    public void run() {
        for (int i = a; i < b; i++) {
            synchronized (lock) {
                while (paused) {
                    try {
                        lock.wait(); // Espera si está pausado
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (isPrime(i)) {
                primes.add(i);
            }
        }
    }

    boolean isPrime(int n) {
        boolean ans;
        if (n > 2) {
            ans = n % 2 != 0;
            for (int i = 3; ans && i * i <= n; i += 2) {
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

    public void pauseThread() {
        paused = true;
}

    public void resumeThread() {
        synchronized (lock) {
            paused = false;
            lock.notifyAll();
        }
    }
}
