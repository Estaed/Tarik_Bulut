package Varliklar;

import java.awt.image.BufferedImage;
import Harita.Harita;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.imageio.ImageIO;


public class AtesTopu extends HaritaObjeleri{
    
    private boolean hit;
    private boolean remove;
    private BufferedImage[] sprite;
    private BufferedImage[] hitsprite;
    
    
    public AtesTopu(Harita tm, boolean sag){
        
        super(tm);
        
        KarsiSag = sag;
        
        HaraketHizi = 3.8;
        if(sag) dx = HaraketHizi;
        else dx -= HaraketHizi;
        
        Genislik = 30;
        Yukseklik = 30;
        CGenislik = 14;
        CYukseklik = 14;
        
        //sprite yukle
        try{
            
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Player/fireball.gif"));
            sprite = new BufferedImage[4];
            for(int i=0;i<sprite.length;i++){
                sprite[i] = spritesheet.getSubimage(i * Genislik, 0, Genislik, Yukseklik);
            }
                hitsprite = new BufferedImage[3];
                for(int i=0;i<hitsprite.length;i++){
                    hitsprite[i] = spritesheet.getSubimage(i * Genislik, Yukseklik, Genislik, Yukseklik);
                }
            animasyon = new Animasyon();
            animasyon.CerceveAyarla(sprite);
            animasyon.DelayAyarla(70);
        }
        catch(IOException e){
        }
        
    }
    
    public void VurusAyarla(){
        if(hit) return;
        hit = true;
        animasyon.CerceveAyarla(hitsprite);
        animasyon.DelayAyarla(70);
        dx = 0;
    }
    
    public boolean NasilRemove(){ return remove;}
    
    public void update(){
        
        HaritaCarpismaKontrolEt();
        PozisyonAl(Xtemp, Ytemp);
        
        if(dx == 0 && !hit){
            VurusAyarla();
        }
        
        animasyon.update();
        if(hit && animasyon.hasOynanan()){
            remove = true;
        }
        
    }
    @Override
    public void draw(Graphics2D g){
     
        HaritaPozisyonAl();
       
        super.draw(g);
        
    }
    
}
