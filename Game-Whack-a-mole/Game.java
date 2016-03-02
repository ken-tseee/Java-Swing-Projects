import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class simulate the game Whack-a-mole.
 * @author Junjian Xie
 * Andrew ID: junjianx
 */
public class Game implements ActionListener {
    /**
     * Number of moles or buttons.
     */
    private static final int NUM_MOLES = 64;
    /**
     * Timer sleep time in millisecond.
     */
    private static final int TIMER_SLEEP_TIME = 1000;
    /**
     * Sleep time for a non-popping-up mole in millisecond.
     */
    private static final int DOWN_SLEEP_TIME = 2000;
    /**
     * Sleep time for ending game in millisecond.
     */
    private static final int END_SLEEP_TIME = 5000;
    /**
     * The most time in millisecond for a mole to pop up.
     */
    private static final int MOST_UP_TIME = 4000;
    /**
     * The most time in millisecond for game playing.
     */
    private static final int MOST_GAME_TIME = 20;
    /**
     * The random starting time in millisecond.
     */
    private static final int START_SLEEP_TIME = 3000;
    /**
     * The number of rows in the grid.
     */
    private static final int ROW = 8;
    /**
     * The number of columns in the grid.
     */
    private static final int COLUMN = 8;
    /**
     * String displayed if a mole's head is not popped up.
     */
    private static final String DOWN_STRING = "   ";
    /**
     * String displayed if a mole's head is popped up.
     */
    private static final String UP_STRING = ":-)";
    /**
     * String displayed if a mole's head is hit.
     */
    private static final String HIT_STRING = ":-(";
    /**
     * Color displayed if a mole's head is not popped up.
     */
    private static final Color DOWN_COLOR = Color.LIGHT_GRAY;
    /**
     * Color displayed if a mole's head is popped up.
     */
    private static final Color UP_COLOR = Color.GREEN;
    /**
     * Color displayed if a mole's head is hit.
     */
    private static final Color HIT_COLOR = Color.RED;
    /**
     * Game time.
     */
    private static int gameTime = MOST_GAME_TIME;
    /**
     * Initial score.
     */
    private static int score;
    /**
     * Reference to main window.
     */
    private JFrame frame;
    /**
     * Reference to main panel.
     */
    private JPanel pane;
    /**
     * Reference to header panel.
     */
    private JPanel headerPane;
    /**
     * Reference to button panel.
     */
    private JPanel buttonPane;
    /**
     * Reference to start button.
     */
    private JButton startButton;
    /**
     * Reference to time label.
     */
    private JLabel timeLabel;
    /**
     * Reference to time text field.
     */
    private JTextField timeField;
    /**
     * Reference to score label.
     */
    private JLabel scoreLabel;
    /**
     * Reference to score text field.
     */
    private JTextField scoreField;
    /**
     * Array of moles buttons popping up or not.
     */
    private JButton[] molesButton;
    /**
     * Random object to pick a random time for a mole to pop up.
     */
    private static Random random = new Random();

    /**
     * Constructor.
     */
    public Game() {
        frame = new JFrame("Whack-a-mole");
        frame.setSize(620, 310);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pane = new JPanel();
        headerPane = new JPanel();
        buttonPane = new JPanel();

        Font font = new Font(Font.MONOSPACED, Font.BOLD, 14);

        headerPane = createHeader(new JPanel());
        pane.add(headerPane);

        buttonPane = createGrid(buttonPane, font);
        pane.add(buttonPane);

        frame.setContentPane(pane);
        frame.setVisible(true);
    }

    /**
     * Create a header panel.
     * @param panel header panel
     * @return a panel with text fields and start button
     */
    private JPanel createHeader(JPanel panel) {
        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
        panel.setLayout(flowLayout);

        startButton = new JButton("Start");
        startButton.addActionListener(this);
        panel.add(startButton);

        timeLabel = new JLabel("Time Left:");
        panel.add(timeLabel);

        timeField = new JTextField(8);
        timeField.setText("20");
        timeField.setEnabled(false);
        panel.add(timeField);

        scoreLabel = new JLabel("Score:");
        panel.add(scoreLabel);

        scoreField = new JTextField(8);
        scoreField.setText("0");
        scoreField.setEditable(false);
        panel.add(scoreField);

        return panel;
    }

    /**
     * Create mole buttons in grid layout.
     * @param panel button panel
     * @param font font style
     * @return a panel with buttons
     */
    private JPanel createGrid(JPanel panel, Font font) {
        GridLayout gridLayout = new GridLayout(ROW, COLUMN);
        panel.setLayout(gridLayout);
        molesButton = new JButton[NUM_MOLES];
        for (int i = 0; i < molesButton.length; i++) {
            molesButton[i] = new JButton(DOWN_STRING);
            molesButton[i].setBackground(DOWN_COLOR);
            molesButton[i].setFont(font);
            molesButton[i].setOpaque(true);
            panel.add(molesButton[i]);
            molesButton[i].addActionListener(this);
        }
        return panel;
    }

    /**
     * Invoke a method when specific button is pressed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Thread[] moleThread = new MoleThread[NUM_MOLES];
        if (e.getSource() == startButton) {
            startButton.setEnabled(false);

            TimerThread timerThread = new TimerThread();
            Thread timer = new Thread(timerThread);
            timer.start();

            for (int i = 0; i < moleThread.length; ++i) {
                moleThread[i] = new MoleThread(molesButton[i]);
                moleThread[i].start();
            }
        }

        for (int i = 0; i < NUM_MOLES; ++i) {
            if (gameTime > -1 && e.getSource() == molesButton[i]
                               && molesButton[i].getText().equals(UP_STRING)) {
                molesButton[i].setText(HIT_STRING);
                molesButton[i].setBackground(HIT_COLOR);
                scoreField.setText(String.valueOf(++score));
            }
        }
    }

    /**
     * Private nested class to create a thread for a mole button.
     * @author Junjian Xie
     * Andrew ID: junjianx
     */
    private class MoleThread extends Thread {
        /**
         * Instance variable for a mole button.
         */
        private JButton button;

        /**
         * Constructor.
         * @param button a mole button
         */
        MoleThread(JButton button) {
            this.button = button;
//            if (gameTime > -1) {
//                if (button.getText().equals(DOWN_STRING)) {
//                    button.setText(UP_STRING);
//                    button.setBackground(UP_COLOR);
//                } else {
//                    button.setText(DOWN_STRING);
//                    button.setBackground(DOWN_COLOR);
//                }
//            }
        }

        /**
         * Override the run method for mole buttons.
         */
        @Override
        public void run() {
            while (gameTime > -1) {
                int randomUpTime = random.nextInt(MOST_UP_TIME - 999) + 1000;
                if (button.getText().equals(DOWN_STRING)) {
                    if (gameTime >= 18) {
                        int randomStart = random.nextInt(START_SLEEP_TIME);
                        try {
                            Thread.sleep(randomStart);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (gameTime * 1000 < randomUpTime) {
                        break;
                    }

                    button.setText(UP_STRING);
                    button.setBackground(UP_COLOR);
                    try {
                        Thread.sleep(randomUpTime);
                    } catch (InterruptedException e) {
                        throw new AssertionError(e);
                    }
                } else {
                    button.setText(DOWN_STRING);
                    button.setBackground(DOWN_COLOR);
                    try {
                        Thread.sleep(DOWN_SLEEP_TIME);
                    } catch (InterruptedException e) {
                        throw new AssertionError(e);
                    }
                }
            }

            if (gameTime == -1) {
                button.setText(DOWN_STRING);
                button.setBackground(DOWN_COLOR);
            }
        }
    }

    /**
     * Private nested class to create a thread for the timer.
     * @author Junjian Xie
     * Andrew ID: junjianx
     */
    private class TimerThread implements Runnable {

        /**
         * Constructor.
         */
        TimerThread() {

        }

        /**
         * Override the run method for the timer.
         */
        @Override
        public void run() {
            try {
                while (gameTime > -1) {
                    timeField.setText(String.valueOf(gameTime--));
                    Thread.sleep(TIMER_SLEEP_TIME);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(END_SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            gameTime = MOST_GAME_TIME;
            score = 0;
            timeField.setText(String.valueOf(gameTime));
            scoreField.setText(String.valueOf(score));
            startButton.setEnabled(true);
        }
    }

    /**
     * Main method to show Swing GUI.
     * @param args arguments
     */
    public static void main(String[] args) {
        new Game();
    }
}
