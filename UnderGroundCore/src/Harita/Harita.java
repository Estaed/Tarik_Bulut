package Harita;

import AnaDosya.OyunPaneli;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;

public class Harita {
    
    //Pozisyon
    private double x;
    private double y;
    
    //Sýnýr(ya da zýplama) Bounds olarak kullanýcam þimdilik
    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;
    
    private double tween; //tween (ne olduðunu bilmiyorum)
    
    //harita
    private int[][] harita;
    private final int temelboyu;
    private int satirlar;//numrows
    private int sutunlar;//numcols
    private int genislik;
    private int yukseklik;
    
    //tileset(ne olduðunu bilmiyorum)
    private BufferedImage tileset;
    private int numTilesAcross;
    private Temel[][] temeller;//tile temel demek galiba
    
    //Çizmek
    private int SatirOffSet;
    private int SutunOffSet;
    private final int numSatirCiz;
    private final int numSutunCiz;
    
    
    public Harita(int temelboyu){
        this.temelboyu = temelboyu;
        numSatirCiz = OyunPaneli.YUKSEKLIK / temelboyu + 2;
        numSutunCiz = OyunPaneli.GENISLIK / temelboyu + 2;
        tween = 0.07;
    }
    
    public void TemelYukle(String s){
    
        try{
            
            tileset = ImageIO.read(getClass().getResourceAsStream(s));
            numTilesAcross = tileset.getWidth() / temelboyu;
            temeller = new Temel[2][numTilesAcross];
            
            BufferedImage subimage;
            for(int sutun = 0; sutun < numTilesAcross;sutun++){
                subimage = tileset.getSubimage(sutun * temelboyu, 0, temelboyu, temelboyu);
                
                temeller[0][sutun] = new Temel(subimage, Temel.NORMAL);
                subimage = tileset.getSubimage(sutun * temelboyu, temelboyu, temelboyu, temelboyu);
                
                temeller[1][sutun] = new Temel(subimage, Temel.BLOCKED);
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void HaritaYukle(String s){
    
        try{
            
            InputStream in = getClass().getResourceAsStream(s);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            
            sutunlar = Integer.parseInt(br.readLine());
            satirlar = Integer.parseInt(br.readLine());
            harita = new int[satirlar][sutunlar];
            genislik = sutunlar * temelboyu;
            yukseklik = satirlar * temelboyu;
            
            xmin = OyunPaneli.GENISLIK - genislik;
            xmax = 0;
            ymin = OyunPaneli.YUKSEKLIK - yukseklik;
            ymax = 0;
            
            String delims = "\\s+";//bölmek
            for(int satir = 0; satir < satirlar;satir++){
                String line = br.readLine();
                String[] jetonlar = line.split(delims);
                for(int sutun = 0; sutun < sutunlar;sutun++){
                    harita[satir][sutun] = Integer.parseInt(jetonlar[sutun]);
                    
                }
            }
            
            
        }
        catch(Exception e){
            e.printStackTrace();
        }
          
    }
       
    public int TemelBoyut(){return temelboyu;}
    public double Al_x(){return x;}
    public double Al_y(){return y;}
    public int genislik(){return genislik;}
    public int yukseklik(){return yukseklik;}
    
    public int getType(int satir, int sutun){
        int rc = harita[satir][sutun];
        int r = rc / numTilesAcross;
        int c = rc % numTilesAcross;
        return temeller[r][c].getType();
        
    }
    
    public void setTween(double d) { tween = d; }
    
    public void PozisyonAl(double x, double y){
        
        //kamera pozisyonunu oyuncuya göre almasý bu mat iþlemleri kamera biraz daha önce gitsin diye
        this.x += (x - this.x) * tween;
        this.y += (y - this.y) * tween;
        
        fixBounds();//hata vermemesi için
        
        SutunOffSet = (int) - this.x / temelboyu;
        SatirOffSet = (int) - this.y / temelboyu;
        
    }
    
    private void fixBounds(){
        if(x < xmin) x = xmin;
        if(y < ymin) y = ymin;
        if(x > xmax) x = xmax;
        if(y > ymax) y = ymax;
         
    }
    
    public void Ciz(Graphics2D g){
        for(int satir = SatirOffSet; satir < SatirOffSet + numSatirCiz; satir++){
            
            if(satir >=satirlar) break;
            
            for(int sutun = SutunOffSet; sutun < SutunOffSet + numSutunCiz; sutun++){
                
                if(sutun >= sutunlar) break;
                
                if (harita[satir][sutun] == 0) continue;
                
                int rc = harita[satir][sutun];
                int r = rc / numTilesAcross;
                int c = rc % numTilesAcross;
                
                g.drawImage(temeller[r][c].getImage(), (int)x + sutun * temelboyu, (int)y + satir * temelboyu, null);
                
            }
            
        }
    }   
}
