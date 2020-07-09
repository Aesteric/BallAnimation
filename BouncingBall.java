import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.awt.event.*;


public class BouncingBall {
    private final static int FRAME_SIZE = 500;

    private JFrame frame;
    private DisplayPanel displayPanel;
    private JButton button;

    public static void main(String[] args) {
        BouncingBall ball = new BouncingBall();
        ball.start();
    }

    public void start() {
        frame = new JFrame("Bouncing Ball");
        frame.setSize(FRAME_SIZE, FRAME_SIZE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        displayPanel = new DisplayPanel();
        frame.add(displayPanel, BorderLayout.CENTER);

        button = new JButton("Pause");
        button.setPreferredSize(new Dimension(100,30));
        button.addActionListener(displayPanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(Color.BLACK);
        controlPanel.add(button);
        frame.add(controlPanel, BorderLayout.SOUTH);


        frame.setVisible(true);
    }

    class DisplayPanel extends JPanel implements ActionListener, Runnable, com.company.DisplayPanel {
        private static final int SPEED = 4;
        private static final int DIAMETER = 20;
        private boolean running;
        private int x, y , deltaX, deltaY;

        public DisplayPanel() {
            deltaY = deltaX = SPEED;
            x = new Random().nextInt(FRAME_SIZE - DIAMETER);
            y = 0;
            startThread();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLUE);
            g.fillOval(x,y,DIAMETER,DIAMETER);
        }

        @Override
        public void run() {
            int width, height;
            while(this.running) {
                width = getWidth();
                height = getHeight();

                x = x + deltaX;
                y = y + deltaY;

                if (x < 0 || x > width - DIAMETER) {
                    x = x - deltaX;
                    deltaX = -1 * deltaX;
                }

                if (y < 0 || y > height - DIAMETER) {
                    y = y - deltaY;
                    deltaY = -1 * deltaY;
                }
                repaint();
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                }
            }
        }

        void startThread() {
            Thread runner = new Thread(this);
            running = true;
            runner.start();
        }
        void stopThread() {
            this.running = false;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            JButton btn = (JButton)event.getSource();
            String btnText = event.getActionCommand();
            if (btnText.equals("Go")) {
                btn.setText("Pause");
                startThread();
            } else {
                btn.setText("Go");
                stopThread();
            }
        }


    }

}
