import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawPanel extends JPanel {
    private JPanel drawPanel;
    private ArrayList<Ball> balls;

    public DrawPanel(ArrayList<Ball> balls) {
        this.balls = balls;
        drawPanel = new JPanel();
        drawPanel.setPreferredSize(new Dimension(Dimensions.PANEL_WIDTH, Dimensions.PANEL_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        balls.forEach(ball -> {
            ball.draw(g);
        });
    }
}
