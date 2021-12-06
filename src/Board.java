import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public class Board {
    private JFrame mainFrame;
    JLabel[] jLabels;
    final private AbstractBoard abstractBoard;

    private static class Color {
        public Color(int fc, int bgc) {
            fontColor = fc;
            bgColor = bgc;
        }

        public int fontColor;
        public int bgColor;
    }

    static HashMap<Integer, Color> colorMap = new HashMap<>() {{
        put(0, new Color(0x776e65, 0xCDC1B4));
        put(2, new Color(0x776e65, 0xeee4da));
        put(4, new Color(0x776e65, 0xede0c8));
        put(8, new Color(0xf9f6f2, 0xf2b179));
        put(16, new Color(0xf9f6f2, 0xf59563));
        put(32, new Color(0xf9f6f2, 0xf67c5f));
        put(64, new Color(0xf9f6f2, 0xf65e3b));
        put(128, new Color(0xf9f6f2, 0xedcf72));
        put(256, new Color(0xf9f6f2, 0xedcc61));
        put(512, new Color(0xf9f6f2, 0xe4c02a));
        put(1024, new Color(0xf9f6f2, 0xe2ba13));
        put(2048, new Color(0xf9f6f2, 0xFF8C00));
        put(4096, new Color(0xf9f6f2, 0xFF4500));
    }};

    Board(int firstNum) {
        abstractBoard = new AbstractBoard(firstNum);
        startGame();
        refresh();
    }

    private void startGame() {
        mainFrame = new JFrame("2048 Game");
        mainFrame.setSize(500, 500);
        mainFrame.setResizable(false);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new GridLayout(4, 4));
        mainFrame.getContentPane().setBackground(new java.awt.Color(0xCDC1B4));
        mainFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                switch (keyEvent.getKeyCode()) {
                    case KeyEvent.VK_UP, KeyEvent.VK_W -> {
                        abstractBoard.doAction(direction.UP);
                        refresh();
                    }
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                        abstractBoard.doAction(direction.DOWN);
                        refresh();
                    }
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                        abstractBoard.doAction(direction.LEFT);
                        refresh();
                    }
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                        abstractBoard.doAction(direction.RIGHT);
                        refresh();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        });
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        jLabels = new JLabel[16];
        JLabel label;
        for (int i = 0; i < 16; i++) {
            jLabels[i] = new JLabel("0", JLabel.CENTER);
            label = jLabels[i];
            label.setOpaque(true);
            label.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, new java.awt.Color(0xBBADA0)));
            label.setFont(new java.awt.Font("Dialog", Font.BOLD, 52));
            mainFrame.add(label);
        }
        mainFrame.setVisible(true);
    }

    private void refresh() {
        JLabel label;
        int[] datas = abstractBoard.toDatas();
        for (int i = 0; i < 16; i++) {
            int number = datas[i];
            label = jLabels[i];
            if (number == 0) {
                label.setText("");
            } else if (number >= 1024) {
                label.setFont(new java.awt.Font("Dialog", Font.BOLD, 42));
                label.setText(String.valueOf(datas[i]));
            } else {
                label.setFont(new java.awt.Font("Dialog", Font.BOLD, 50));
                label.setText(String.valueOf(number));
            }
            Color currColor = colorMap.get(number);
            label.setBackground(new java.awt.Color(currColor.bgColor));
            label.setForeground(new java.awt.Color(currColor.fontColor));
        }
    }
}
