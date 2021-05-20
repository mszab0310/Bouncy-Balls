import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Frame {
    private JFrame frame;
    private DrawPanel drawPanel;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton startButton;
    private JButton stopButton;
    private JButton removeButton;
    private ArrayList<Ball> balls;
    private ArrayList<Thread> threadList;
    private boolean stop;
    private Ball selectedBall;
    private JLabel selectedLabel;

    public Frame() {
        balls = new ArrayList<>();
        threadList = new ArrayList<>();
        frame = new JFrame();
        stop = true;
        frame.setSize(Dimensions.FRAME_WIDTH, Dimensions.FRAME_HEIGHT);
        drawPanel = new DrawPanel(balls);
        drawPanel.setFocusable(true);
        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);
        buttonPanel.setLayout(new FlowLayout());
        initButtons();
        frame.add(drawPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        buttonListeners();
        addMouseListenerToPanel();
        addKeyboardListener();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setFocusable(true);
        frame.setLocationByPlatform(true);
        //frame.pack();
        frame.setVisible(true);
    }

    void addKeyboardListener() {
        drawPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (selectedBall != null) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> {
                            selectedBall.setyVel(-1);
                            selectedBall.setxVel(0);
                        }
                        case KeyEvent.VK_DOWN -> {
                            selectedBall.setyVel(1);
                            selectedBall.setxVel(0);
                        }
                        case KeyEvent.VK_LEFT -> {
                            selectedBall.setxVel(-1);
                            selectedBall.setyVel(0);
                        }
                        case KeyEvent.VK_RIGHT -> {
                            selectedBall.setxVel(1);
                            selectedBall.setyVel(0);
                        }
                    }
                }
            }
        });
    }

    void initButtons() {
        addButton = new JButton("Add");
        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        removeButton = new JButton("Remove");
        selectedLabel = new JLabel("");
        selectedLabel.setPreferredSize(new Dimension(200, 30));
        buttonPanel.add(addButton);
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(selectedLabel);
    }

    void addMouseListenerToPanel() {
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int x = e.getX();
                int y = e.getY();
                if (selectedBall == null) {
                    for (Ball ball : balls) {
                        ball.checkSelected(x, y, true);
                        if (ball.isSelected()) {
                            selectedBall = ball;
                            selectedLabel.setText("Use arrow keys to modify direction");
                            break;
                        }
                    }
                    drawPanel.repaint();
                } else {
                    balls.forEach(ball -> ball.checkSelected(x, y, false));
                    drawPanel.repaint();
                    selectedBall = null;
                    selectedLabel.setText("");
                }
            }
        });
    }

    void buttonListeners() {

        addButton.addActionListener(e -> {
            balls.add(new Ball());
            drawPanel.repaint();
            drawPanel.requestFocus();
        });

        startButton.addActionListener(e -> {
            stop = true;
            threadList = new ArrayList<>();
            balls.forEach(ball -> {
                Thread thread = new Thread(() -> {
                    while (stop) {
                        ball.move();
                        drawPanel.repaint();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                thread.start();
                threadList.add(thread);
            });
            drawPanel.requestFocus();
        });

        stopButton.addActionListener(e -> {
            stop = false;
            drawPanel.requestFocus();
        });

        removeButton.addActionListener(e -> {
            if (selectedBall != null) {
                balls.remove(selectedBall);
                selectedBall = null;
            }
            selectedLabel.setText("");
            drawPanel.requestFocus();
        });
    }


}
