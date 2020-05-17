package Varliklar;

import AnaDosya.OyunPaneli;
import Harita.Harita;
import Harita.Temel;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public abstract class HaritaObjeleri {
    
    //Fayans?
    protected Harita harita;
    protected int temelboyu;
    protected double xmap;
    protected double ymap;
    
    //Pozisyon ve vektor
    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    
    //Boyutlar
    protected int Genislik;
    protected int Yukseklik;
    
    //Çarpýþma Kutusu
    protected int CGenislik;
    protected int CYukseklik;
    
    //Çarpýþma
    protected int currSatir;
    protected int currSutun;
    protected double Xkader;
    protected double Ykader;
    protected double Xtemp;
    protected double Ytemp;
    protected boolean ToplamSag;
    protected boolean ToplamSol;
    protected boolean SolButon;
    protected boolean SagButon;
    
    //Animasyon
    protected Animasyon animasyon;
    protected int SimdikiAksiyon;
    protected int OncekiAksiyon;
    protected boolean KarsiSag;
    
    //Haraket
    protected boolean sol;
    protected boolean sag;
    protected boolean yukari;
    protected boolean assagi;
    protected boolean ziplayis;
    protected boolean dusus;
    
    //Haraket Oznitelikleri
    protected double HaraketHizi;
    protected double MaxHizi;
    protected double DurmaHizi;
    protected double DusmeHizi;
    protected double MaxDusmeHizi;
    protected double BaslangicZiplamasi;
    protected double ZiplamaHiziDurma;
    
    //Ýnsat
    public HaritaObjeleri(Harita tm){
        harita = tm;
        temelboyu = tm.TemelBoyut();
        
    }
    
    public boolean intersects(HaritaObjeleri o){
        
        Rectangle r1 = GetRectangle();
        Rectangle r2 = o.GetRectangle();
        return r1.intersects(r2);
    }
    
    public Rectangle GetRectangle(){
        return new Rectangle((int)x - CGenislik, (int)y - CYukseklik, CGenislik, CYukseklik);
    }
    
    public void KoseHesapla(double x, double y){
        int SolTile = (int) (x - CGenislik / 2) / temelboyu;
        int SagTile = (int) (x + CGenislik / 2 - 1) / temelboyu;
        int TopTile = (int) (y - CYukseklik / 2) / temelboyu;
        int Buton = (int) (y + CYukseklik / 2 - 1) / temelboyu;
        
        int tsol = harita.getType(TopTile, SolTile);
        int tsag = harita.getType(TopTile, SagTile);
        int bsol = harita.getType(Buton, SolTile);
        int bsag = harita.getType(Buton, SagTile);
        
        ToplamSol = tsol == Temel.BLOCKED;
        ToplamSag = tsag == Temel.BLOCKED;
        SolButon = bsol == Temel.BLOCKED;
        SagButon = bsag == Temel.BLOCKED;
    }
    
    public void HaritaCarpismaKontrolEt(){
        currSutun = (int)x / temelboyu;
        currSatir = (int)y / temelboyu;
        Xkader = x + dx;
        Ykader = y + dy;
        
        Xtemp = x;
        Ytemp = y;
        
        KoseHesapla(x, Ykader);
        if(dy < 0){
            if(ToplamSol || ToplamSag){
                dy = 0;
                Ytemp = currSatir * temelboyu + CYukseklik / 2;
            }
            else{
                Ytemp += dy;
            }
        }
        if(dy > 0){
            if(SolButon || SagButon){
                dy = 0;
                dusus = false;
                Ytemp = (currSatir + 1) * temelboyu - CYukseklik / 2;
            }
            else{
                Ytemp += dy;
            }
        }
        
        KoseHesapla(Xkader, y);
        if(dx < 0){
            if(ToplamSol || SolButon){
                dx = 0;
                Xtemp = currSutun * temelboyu + CGenislik / 2;
            }
            else{
                Xtemp += dx;
            }
        }
        if(dx > 0){
            if(ToplamSag || SagButon){
                dx = 0;
                Xtemp = (currSutun + 1) * temelboyu - CGenislik / 2;
            }
            else{
                Xtemp += dx;
            }
        }
        
        if(!dusus){
            KoseHesapla(x, Ykader + 1);
            if(!SolButon && !SagButon){
                dusus = true;
            }
        }
        
    }
    
    public int Al_x(){ return (int)x; }
    public int Al_y(){ return (int)y; }
    public int genislik(){ return Genislik; }
    public int yukseklik(){ return Yukseklik; }
    public int CGenislikAl(){ return CGenislik; }
    public int CYukseklikAl(){ return CYukseklik; }
    
    
    public void PozisyonAl(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public void VektorAl(double dx, double dy){
        this.dx = dx;
        this.dy = dy;
    }
    
    //setMapPozition Burasý Önemli !!!!!!!!!1111
    public void HaritaPozisyonAl(){
        xmap = harita.Al_x();
        ymap = harita.Al_y();
    }
    
    public void SolAyarla(boolean b){ sol = b; }
    public void SagAyarla(boolean b){ sag = b; }
    public void YukariAyarla(boolean b){ yukari = b; }
    public void AssagiAyarla(boolean b){ assagi = b; }
    public void ZiplamaAyarla(boolean b){ ziplayis = b; }
    public void DususAyarla (boolean b){ dusus = b; }
    
    public boolean KapaliEkran(){
        return x + xmap + Genislik < 0 || x + xmap - Genislik > OyunPaneli.GENISLIK || 
               y + ymap + Yukseklik < 0 || y + ymap - Yukseklik > OyunPaneli.YUKSEKLIK;
    }
    
    public void draw(Graphics2D g){
         if(KarsiSag){
            g.drawImage(animasyon.ImageAl(), (int)(x + xmap - Genislik / 2), (int)(y + ymap - Yukseklik / 2), null);
        }
        else{
            g.drawImage(animasyon.ImageAl(), (int)(x + xmap - Genislik / 2 + Genislik), 
                    (int)(y + ymap - Yukseklik / 2), - Genislik, Yukseklik, null);
            
        }
    }
    
}
