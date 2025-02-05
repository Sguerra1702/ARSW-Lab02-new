package snakepackage;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

import enums.GridSize;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.*;


/**
 * @author jd-
 *
 */
public class SnakeApp {

    private static SnakeApp app;
    public static final int MAX_THREADS = 8;
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
    private static Board board;
    int nr_selected = 0;
    Thread[] thread = new Thread[MAX_THREADS];
    private boolean isRunning = false;
    private boolean isPaused = false;
    private JButton startButton, pauseButton, resumeButton;
    private JLabel longestSnakeLabel, worstSnakeLabel;

    public SnakeApp() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("The Snake Race");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(GridSize.GRID_WIDTH * GridSize.WIDTH_BOX + 17,
                GridSize.GRID_HEIGHT * GridSize.HEIGH_BOX + 40);
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2,
                dimension.height / 2 - frame.getHeight() / 2);

        board = new Board();
        frame.add(board, BorderLayout.CENTER);

        // Crear panel de botones
        JPanel actionsPanel = new JPanel();
        actionsPanel.setLayout(new FlowLayout());
        startButton = new JButton("Iniciar");
        pauseButton = new JButton("Pausar");
        resumeButton = new JButton("Reanudar");

        pauseButton.setEnabled(false);
        resumeButton.setEnabled(false);

        longestSnakeLabel = new JLabel("Serpiente más larga: N/A");
        worstSnakeLabel = new JLabel("Peor serpiente: N/A");

        actionsPanel.add(startButton);
        actionsPanel.add(pauseButton);
        actionsPanel.add(resumeButton);
        actionsPanel.add(longestSnakeLabel);
        actionsPanel.add(worstSnakeLabel);

        frame.add(actionsPanel, BorderLayout.SOUTH);

        for (int i = 0; i < MAX_THREADS; i++) {
            snakes[i] = new Snake(i + 1, spawn[i], i + 1);
            snakes[i].addObserver(board);
        }
        // Agregar eventos a los botones
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pauseGame();
            }
        });

        resumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resumeGame();
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        app = new SnakeApp();
    }

    private void startGame() {
        if (isRunning) return;

        isRunning = true;
        isPaused = false;
        startButton.setEnabled(false);
        pauseButton.setEnabled(true);
        resumeButton.setEnabled(false);

        for (int i = 0; i < MAX_THREADS; i++) {
            thread[i] = new Thread(snakes[i]);
            thread[i].start();
        }
    }

    private synchronized void pauseGame() {
        if (!isRunning || isPaused) return;

        isPaused = true;
        pauseButton.setEnabled(false);
        resumeButton.setEnabled(true);

        for (Snake snake : snakes) {
            snake.pause();
        }

        // Mostrar estadísticas después de pausar
        //updateStatistics();
    }



    private synchronized void resumeGame() {
        if (!isPaused) return;

        isPaused = false;
        pauseButton.setEnabled(true);
        resumeButton.setEnabled(false);

        for (Snake snake : snakes) {
            snake.resume();
        }
    }


    private void salida() {
        if (JOptionPane.showConfirmDialog(frame, "¿Desea salir del juego?", "Salir del juego",
                JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION) {
            System.exit(0);
        }else {
            frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        }
    }

    public static SnakeApp getApp() {
        return app;
    }



}
