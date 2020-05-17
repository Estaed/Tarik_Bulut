package Varliklar;

import Harita.*;
import Sesler.SesOynaticisi;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JOptionPane; 
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;


public class Oyuncu extends HaritaObjeleri{
    
    //Oyuncu Maddesi
    private int Can;
    private int MaxCan;
    private int Ates;
    private int MaxAtes;
    private boolean olum;
    private boolean kacinma;
    private long KacinmaSuresi;
    
    //AtesTopu
    private boolean Atesleme;
    private int AtesBedeli;
    private int AtesTopuHasari;
    
    private ArrayList<AtesTopu> AtesTopu;
    
    //pençelemek
    private boolean Pencelemek;
    private int PencelemeHasari;
    private int PencelemeUzakligi;
    
    //Süzülme
    private boolean Suzulme; 
    
    //Animasyonlar
    private ArrayList<BufferedImage[]> sprite;
    private final int [] CerceveSayisi = {
        2, 8, 1, 2, 4, 2, 5
    };
    
    //Animasyon Aksiyonlari
    private static final int BOS = 0;
    private static final int YURUME = 1;
    private static final int ZPLAMA = 2;
    private static final int DUSME = 3;
    private static final int SUZULME = 4;
    private static final int ATESTOPU = 5;
    private static final int PENCELEMEK = 6;
    
    private final HashMap<String, SesOynaticisi> sfx;
    
    public Oyuncu(Harita tm){
        
        super(tm);
        Genislik = 30;
        Yukseklik = 30;
        CGenislik = 20;
        CYukseklik = 20;
        
        HaraketHizi = 0.3;
        MaxHizi = 1.6;
        DurmaHizi = 0.4;
        DusmeHizi = 0.15;
        MaxDusmeHizi = 4.0;
        BaslangicZiplamasi = -4.8;
        ZiplamaHiziDurma = 0.3;
        
        KarsiSag = true;
        
        Can = MaxCan = 3;
        Ates = MaxAtes = 1000;
        
        AtesBedeli = 100;
        AtesTopuHasari = 1;
        
        AtesTopu = new ArrayList<>();
        
        PencelemeHasari = 1;
        PencelemeUzakligi = 40;
        
        //Sprite yükle
        try{
            
            BufferedImage HaraketliGrafik = ImageIO.read(getClass().getResourceAsStream(
                    "/Sprites/Player/playersprites.gif"));
            
            
            sprite = new ArrayList<>();
            for(int i = 0; i < 7; i++){
                BufferedImage[] bi = new BufferedImage[CerceveSayisi[i]];
                for(int j = 0; j < CerceveSayisi[i];j++){
                    
                    if(i != PENCELEMEK){
                         bi[j] = HaraketliGrafik.getSubimage(j * Genislik, i * Yukseklik, Genislik, Yukseklik);
                    }
                    else{
                         bi[j] = HaraketliGrafik.getSubimage(j * Genislik * 2, i * Yukseklik, Genislik * 2, Yukseklik);
                    }
                }
                
                sprite.add(bi);
                
            }
            
        }
        catch(IOException e){
        }
        
        animasyon = new Animasyon();
        SimdikiAksiyon = BOS;
        animasyon.CerceveAyarla(sprite.get(BOS));
        animasyon.DelayAyarla(400);
        
        sfx = new HashMap<>();
        sfx.put("Ziplayis", new SesOynaticisi("/SFX/jump.mp3"));
        sfx.put("Penceleme", new SesOynaticisi("/SFX/scratch.mp3"));
        
        
    }
    
    public int CanAl(){ return Can; }
    public int MaxCanAl(){ return MaxCan; }
    public int AtesAl(){ return Ates; }
    public int MaxAtesAl(){ return MaxAtes; }
    
    public void AteslemeyiAyarla(){
        Atesleme = true;
    }
    
    public void PencelemeyiAyarla(){
        Pencelemek = true;
    }
    
    public void Suzulme(boolean b){
        Suzulme = b;
    }
    
    public void AtesKontrolEt(ArrayList<Dusmanlar> dusmanlar){
        
        //Dusmanlar dongusu
        
        for(int i=0;i<dusmanlar.size();i++){
            
            Dusmanlar e = dusmanlar.get(i);
            
            //Penceleme saldirisi
            
            if(Pencelemek){
                if(KarsiSag){
                    if(e.Al_x() > x && e.Al_x() < x + PencelemeUzakligi &&
                            e.Al_y() > y - Yukseklik / 2 && e.Al_y() < y + Yukseklik / 2){
                        e.Vurus(PencelemeHasari);
                    }
                }
                else{
                    if(e.Al_x() < x && e.Al_x() > x - PencelemeUzakligi &&
                            e.Al_y() > y - Yukseklik / 2 && e.Al_y() < y + Yukseklik / 2){
                        e.Vurus(PencelemeHasari);
                    }
                }
            } 
            
            //Ates Topu Kontrol Et
            for(int j=0;j<AtesTopu.size();j++){
                if(AtesTopu.get(j).intersects(e)){
                    e.Vurus(AtesTopuHasari);
                    AtesTopu.get(j).VurusAyarla();
                    break;
                }
            }
            
            //Dusmanla olan carpismayi kontrol et
            if(intersects(e)){
                hit(e.HasarAyarla());
            }
            
        }   
    }
    
    public void hit(int hasar){
        if(kacinma) return;
        Can -= hasar;
        if(Can < 0) Can = 0;
        if(Can == 0) {
        	String message = "Nasil ya o kadar da kolay yapmistim";
        	JOptionPane.showInputDialog(message);
        	System.exit(0);
        }
        kacinma = true;
        KacinmaSuresi = System.nanoTime();
    }
    
    private void SonrakiPozisyonAl(){
        //Haraket
        if(sol){
            dx -= HaraketHizi;
            if(dx < -MaxHizi){
                dx = -MaxHizi;
            }
        }
        else if(sag){
            dx += HaraketHizi;
            if(dx > MaxHizi){
                dx = MaxHizi;
            }
        }
        else{
            if(dx > 0){
                dx -= DurmaHizi;
                if(dx <0){
                    dx = 0;
                }
            }
            else if(dx < 0){
                dx += DurmaHizi;
                if(dx > 0){
                    dx = 0;
                }
            }
        }
        
        //Haraket ederken saldýramama kodlarý
        if((SimdikiAksiyon == PENCELEMEK) || SimdikiAksiyon ==ATESTOPU
                && (!ziplayis && dusus)){
            dx = 0;
        }
        
        //Ziplama
        if(ziplayis && !dusus){
            sfx.get("Ziplayis").play();
             dy = BaslangicZiplamasi;
             dusus = true;
        }
        
        //dusus
        if(dusus){
            if(dy > 0 && Suzulme){
                dy += DusmeHizi * 0.1;
            }
            else{
                dy += DusmeHizi;
            }
            
            if(dy > 0) ziplayis = false;
            if(dy < 0 && !ziplayis) dy += ZiplamaHiziDurma;
            if(dy > MaxDusmeHizi) dy = MaxDusmeHizi;
            
        }
        
    }
    
    public void update(){
        
        //Update pozisyonu
        SonrakiPozisyonAl();
        HaritaCarpismaKontrolEt();
        PozisyonAl(Xtemp, Ytemp); //pozisyonayarla
        
        //Saldirinin durmasini kontrol et
        if(SimdikiAksiyon == PENCELEMEK){
            if(animasyon.hasOynanan()) Pencelemek = false;
        }
        if(SimdikiAksiyon == ATESTOPU){
            if(animasyon.hasOynanan()) Atesleme = false;
        }
        
        //AtesTopu saldirisi
        Ates += 1;
        if(Ates > MaxAtes) Ates = MaxAtes;
        if(Atesleme && SimdikiAksiyon !=ATESTOPU){
            if(Ates > AtesBedeli){
                Ates -= AtesBedeli;
                AtesTopu at = new AtesTopu(harita, KarsiSag);
                at.PozisyonAl(x, y);
                AtesTopu.add(at);
            }
        }
        
        //update AtesTopu
        for(int i=0;i<AtesTopu.size();i++){
            AtesTopu.get(i).update();
            if(AtesTopu.get(i).NasilRemove()){
                AtesTopu.remove(i);
                i--;
            }
        }
        
        // KacinmaDurmnasi
        if(kacinma){
            long elapsed = (System.nanoTime() - KacinmaSuresi) / 1000000;
            if(elapsed > 1000){
                kacinma = false;
            }
        }
        
        
        //Animasyon Ayarla
        if(Pencelemek){
            if(SimdikiAksiyon != PENCELEMEK){
                sfx.get("Penceleme").play();
                SimdikiAksiyon = PENCELEMEK;
                animasyon.CerceveAyarla(sprite.get(PENCELEMEK));
                animasyon.DelayAyarla(50);
                Genislik = 60;  
            }
        }
            else if(Atesleme){
                if(SimdikiAksiyon != ATESTOPU){
                    SimdikiAksiyon = ATESTOPU;
                    animasyon.CerceveAyarla(sprite.get(ATESTOPU));
                    animasyon.DelayAyarla(100);
                    Genislik = 30;
                }    
            }
            else if(dy > 0){
                if(Suzulme){
                    if(SimdikiAksiyon != SUZULME){
                    SimdikiAksiyon = SUZULME;
                    animasyon.CerceveAyarla(sprite.get(SUZULME));
                    animasyon.DelayAyarla(100);
                    Genislik = 30;
                }
            }
                
                else if(SimdikiAksiyon != DUSME){
                    SimdikiAksiyon = DUSME;
                    animasyon.CerceveAyarla(sprite.get(DUSME));
                    animasyon.DelayAyarla(100);
                    Genislik = 30;
                }  
            }
                else if(dy < 0){
                    if(SimdikiAksiyon != ZPLAMA){
                    SimdikiAksiyon = ZPLAMA;
                    animasyon.CerceveAyarla(sprite.get(ZPLAMA));
                    animasyon.DelayAyarla(-1);
                    Genislik = 30;
                }
            }
                else if(sol || sag){
                    if(SimdikiAksiyon != YURUME){
                    SimdikiAksiyon = YURUME;
                    animasyon.CerceveAyarla(sprite.get(YURUME));
                    animasyon.DelayAyarla(40);
                    Genislik = 30;
                }
            }    
                
                else{
                    if(SimdikiAksiyon != BOS){
                    SimdikiAksiyon = BOS;
                    animasyon.CerceveAyarla(sprite.get(BOS));
                    animasyon.DelayAyarla(400);
                    Genislik = 30;
                }
            }    
        
            animasyon.update();
            
            //Yonu Ayarla
            if(SimdikiAksiyon != PENCELEMEK && SimdikiAksiyon != ATESTOPU){
                if(sag) KarsiSag = true;
                if(sol) KarsiSag = false;
            }
    }
    
    @Override
    public void draw(Graphics2D g) {
        
        //AtesTopuCiz
        for(int i=0;i<AtesTopu.size();i++){
            AtesTopu.get(i).draw(g);
        }
        
        HaritaPozisyonAl();
        
        //oyuncuyu ciz
        if(kacinma){
            long elapsed = (System.nanoTime() - KacinmaSuresi) / 1000000;
            if(elapsed / 100 % 2 == 0){
                return;
            }
        }
        super.draw(g);
    }
}

