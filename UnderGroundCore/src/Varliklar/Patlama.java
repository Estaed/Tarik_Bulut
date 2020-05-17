package Varliklar;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Patlama {
    
    private int x;
    private int y;
    private int xmap;
    private int ymap;
    
    private int Genislik;
    private int Yukseklik;
    
    private Animasyon animasyon;
    private BufferedImage[] sprite;
    
    private boolean remove;
    
    public Patlama(int x, int y){
        
        this.x = x;
        this.y = y;
        
        Genislik = 30;
        Yukseklik = 30;
        
        try{
           
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/explosion"));
            sprite = new BufferedImage[6];
            
            for(int i=0;i<sprite.length;i++){
                sprite[i] = spritesheet.getSubimage(i * Genislik, 0, Genislik, Yukseklik);
            }
        }
        catch(IOException e){
        }
     
        animasyon = new Animasyon();
        animasyon.CerceveAyarla(sprite);
        animasyon.DelayAyarla(70);
        
    }
    
    public void update(){
        animasyon.update();
        if(animasyon.hasOynanan()){
            remove = true;
        }
    }
    
    public boolean EgerRemovesa(){ return remove;}
    public void HaritaPozisyonAyarla(int x, int y) {
		xmap = x;
		ymap = y;
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(
			animasyon.ImageAl(),
			x + xmap - Genislik / 2,
			y + ymap - Yukseklik / 2,
			null
		);
	}
    
}
