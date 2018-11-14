package tcp.frame;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public final class FrameController {

    private JFrame frame;

    private static FrameController instance;

    public void openFrame(String title, String text, int textSize) {

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 500, 400);

        JLabel label = new JLabel(text);
        label.setFont(new Font(label.getFont().getName(), label.getFont().getStyle(), textSize));
        label.setBorder(new LineBorder(Color.BLACK));

        frame.getContentPane().add(label);
        frame.setSize(1000, 600);

        frame.setVisible(true);

    }

    public void updateFrame(String text, int textSize) {

        if (frame == null) {
            return;
        }

        frame.getContentPane().removeAll();

        JLabel refresh = new JLabel(text);
        refresh.setFont(new Font(refresh.getFont().getName(), refresh.getFont().getStyle(), textSize));
        refresh.setBorder(new LineBorder(Color.BLACK));

        frame.getContentPane().add(refresh);
        frame.revalidate();

    }

    public static FrameController getInstance() {
        if (instance == null) {
            synchronized (FrameController.class) {
                if (instance == null) {
                    instance = new FrameController();
                }
            }
        }
        return instance;
    }

}
