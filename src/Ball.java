import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Ball {
    private BufferedImage image;
    private int xPos;
    private int yPos;
    private int height;
    private int width;
    private int xVel;
    private int yVel;
    private boolean selected;
    private Random random;


    public Ball(){
        width = 60;
        height = 60;
        selected = false;
        try {
            image =  ImageIO.read(new File("E:\\JavaSem2\\balls\\ball.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        random = new Random();
        xPos = random.nextInt(Dimensions.PANEL_WIDTH-width);
        yPos = random.nextInt(Dimensions.PANEL_HEIGHT-width);
        xVel = (int) Math.pow(-1,random.nextInt());
        yVel = (int) Math.pow(-1,random.nextInt());
    }



    public void draw(Graphics g){
        g.drawImage(image,xPos,yPos,width,height,null);
        if(selected){
            g.setColor(Color.RED);
            g.drawRect(xPos,yPos,width,height);
        }
    }

    public boolean isSelected(){
        return selected;
    }

    public void checkSelected(int mouseX,int mouseY, boolean value){
        if(mouseX >= xPos && mouseX <= (xPos+width) && mouseY >= yPos && mouseY <= (yPos+height)){
            selected = value;
        }
    }

    public void setxVel(int xVel) {
        this.xVel = xVel;
    }

    public void setyVel(int yVel) {
        this.yVel = yVel;
    }

    public void move(){
        if (xPos >= Dimensions.PANEL_WIDTH - width  && xVel > 0) {
            xVel *= -1;
        }
        if(xPos <= 0 && xVel < 0){
            xVel *= -1;
        }

        if (yPos >= Dimensions.PANEL_HEIGHT  && yVel > 0) {
            yVel *= -1;
        }
        if(yPos <= 0 && yVel < 0){
            yVel *= -1;
        }
        xPos += xVel;
        yPos += yVel;
    }

}
