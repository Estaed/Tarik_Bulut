package DusmanVarliklar;

import Harita.Harita;
import Varliklar.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Sert extends Dusmanlar {
    
    private BufferedImage[] sprite;
    
    public Sert(Harita tm) {
        super(tm);
        
        HaraketHizi = 0.3;
        MaxHizi = 0.3;
        DusmeHizi = 0.2;
        MaxDusmeHizi = 10.0;
        
        Genislik = 30;
        Yukseklik = 30;
        CGenislik = 20;
        CYukseklik = 20;
        
        Can = MaxCan =1;
        Hasar = 1;
        
        //sprite Yukle
        try{
            
            BufferedImage spritesheet = ImageIO.read(getClass().getResourceAsStream("/Sprites/Enemies/slugger.gif"));
            
            sprite = new BufferedImage[3];
            for(int i = 0;i<sprite.length;i++){
                sprite[i] = spritesheet.getSubimage(i * Genislik, 0, Genislik, Yukseklik);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        animasyon = new Animasyon();
        animasyon.CerceveAyarla(sprite);
        animasyon.DelayAyarla(300);
        
        sag = true;
        KarsiSag = true;
    }
    
    private void SonrakiPozisyonAl(){
        
       //Haraket
        if(sol){
            dx -= HaraketHizi;
            if(dx < MaxHizi){
                dx = -MaxHizi;
            }
        }
        else if(sag){
            dx += HaraketHizi;
            if(dx > MaxHizi){
                dx = MaxHizi;
            }
        }
        
        //Dusme
        if(dusus){
            dy += DusmeHizi;
        }
        
    }
    
    @Override
    public void update(){
        
        //Update pozisyon
        SonrakiPozisyonAl();
        HaritaCarpismaKontrolEt();
        PozisyonAl(Xtemp, Ytemp);
        
        //Kacinma Kontrolu
        if(Kacinma){
            long elapsed = (System.nanoTime() - KacinmaSuresi) / 1000000;
            if(elapsed > 400){
                Kacinma = false;
            }
        }
        
        //Duvara carpinca geri yone dogru ilerlemesi
        if(sag && dx == 0){
            sag = false;
            sol = true;
            KarsiSag = false;
        }
        else if(sol && dx == 0){
            sol = false;
            sag = true;
            KarsiSag = true;
        }
        
        //update Animasyon
        animasyon.update();
        
        
    }
    
    @Override
    public void draw(Graphics2D g){
        
        //if(KapaliEkran()) return;
        
        HaritaPozisyonAl();
        
        super.draw(g);
        
    }
    
}
