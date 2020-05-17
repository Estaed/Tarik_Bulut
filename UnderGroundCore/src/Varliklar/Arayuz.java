package Varliklar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Arayuz {
    
    private Oyuncu oyuncu;
    private BufferedImage image;
    private Font font;
    public Arayuz(Oyuncu o){
        oyuncu = o;
        try{
            
            image =ImageIO.read(getClass().getResourceAsStream("/HUD/hud.gif"));
            font = new Font("Arial", Font.PLAIN, 14);
            
        }
        catch(IOException e){
        }
    }
    
    public void draw(Graphics2D g){
        g.drawImage(image, 0, 10, null);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(oyuncu.CanAl() + "/" + oyuncu.MaxCanAl(), 30, 25);
        g.drawString(oyuncu.AtesAl() / 100 + "/" + oyuncu.MaxAtesAl() / 100, 30, 45);
    }
    
}
