package Harita;

import AnaDosya.OyunPaneli;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;


public class ArkaPlan {
    
    private BufferedImage image;
    
    private double x;
    private double y;
    private double dx;
    private double dy;
    
    private double HaraketOlcegi;
    
    public ArkaPlan(String s, double ms){
        
        try{
            image = ImageIO.read(getClass().getResourceAsStream(s));
            HaraketOlcegi = ms;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void PozisyonAyarla(double x, double y){ //setPosition
        this.x = (x * HaraketOlcegi) % OyunPaneli.GENISLIK;
        this.y = (y * HaraketOlcegi) % OyunPaneli.YUKSEKLIK;
    }
    
    public void VektorAyarla(double dx, double dy){
        
        this.dx = dx;
        this.dy = dy;
    }
    
    public void update(){
        x += dx;
        y += dy;
    }
    
    public void draw(Graphics2D g){
        g.drawImage(image, (int)x, (int)y, null);
        if(x < 0){
            g.drawImage(image, (int)x + OyunPaneli.GENISLIK, (int)y, null);
            
        }
        if(x > 0){
            g.drawImage(image, (int)x - OyunPaneli.GENISLIK, (int)y, null);
        }
    }

}
