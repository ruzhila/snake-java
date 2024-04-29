import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SnakeGame extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    java.util.Random random = new java.util.Random();
    int bodyParts = 6;
    int applesEaten;
    int appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
    int appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    char direction = 'R';
    boolean running = true;

    SnakeGame() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        new Timer(150, this).start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        String text = running ? "Score: " + applesEaten + " | ruzhila.cn" : "Game Over";
        if (running) {
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            g.setColor(Color.green);
            for (int i = 0; i < bodyParts; i++) {
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
        g.setColor(running ? Color.blue : Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        g.drawString(text, (SCREEN_WIDTH - getFontMetrics(g.getFont()).stringWidth(text)) / 2, g.getFont().getSize());
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            x[i] = x[(i - 1)];
            y[i] = y[(i - 1)];
        }
        if (direction == 'U') {
            y[0] -= UNIT_SIZE;
        } else if (direction == 'D') {
            y[0] += UNIT_SIZE;
        } else if (direction == 'L') {
            x[0] -= UNIT_SIZE;
        } else if (direction == 'R') {
            x[0] += UNIT_SIZE;
        }
    }

    public class MyKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_LEFT && direction != 'R') {
                direction = 'L';
            } else if (keyCode == KeyEvent.VK_RIGHT && direction != 'L') {
                direction = 'R';
            } else if (keyCode == KeyEvent.VK_UP && direction != 'D') {
                direction = 'U';
            } else if (keyCode == KeyEvent.VK_DOWN && direction != 'U') {
                direction = 'D';
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            if ((x[0] == appleX) && (y[0] == appleY)) {
                applesEaten++;
                appleX = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
                appleY = random.nextInt((int) (SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
            }
            for (int i = bodyParts; i > 0; i--) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    running = false;
                }
            }
            if (x[0] < 0 || x[0] > SCREEN_WIDTH || y[0] < 0 || y[0] > SCREEN_HEIGHT) {
                running = false;
            }
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.add(new SnakeGame());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}