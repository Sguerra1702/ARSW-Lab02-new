/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

/**
 *
 */

import java.util.Scanner;
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;
    

    private PrimeFinderThread pft[];
    private final Object lock = new Object();
    
    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];

        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA, lock);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1, lock);
    }

    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {
        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
        }
        while (true) {
            try {
                Thread.sleep(TMILISECONDS);
                pauseThreads();
                showPrimesCount();
                waitForUserInput();
                resumeThreads();
                if (allThreadsFinished()) {
                    System.out.println("Todos los hilos han finalizado. Terminando ejecución.");
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void pauseThreads() {
            for (PrimeFinderThread thread : pft) {
                thread.pauseThread();
        }
    }

    private void resumeThreads() {
        for (PrimeFinderThread thread : pft) {
            thread.resumeThread();
        }
    }

    private void showPrimesCount() {
        int totalPrimes = 0;
        for (PrimeFinderThread thread : pft) {
            totalPrimes += thread.getPrimes().size();
    }
        System.out.println("Número total de primos encontrados hasta ahora: " + totalPrimes);
    }

    private void waitForUserInput() {
        System.out.println("Presione ENTER para continuar...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    private boolean allThreadsFinished() {
        for (PrimeFinderThread thread : pft) {
            if (thread.isAlive()) { // Si algún hilo sigue vivo, el proceso continúa
                return false;
            }
        }
        return true; // Todos los hilos han terminado
    }

}
