package Varliklar;

import java.awt.image.BufferedImage;

public class Animasyon {
    
    private BufferedImage[] cerceve;
    private int SimdikiCerceve;
    
    private long ZamanBaslatici;
    private long delay;
    
    private boolean Oynanan;
    
    public Animasyon(){
        Oynanan = false;    
    }
    
    public void CerceveAyarla(BufferedImage[] cerceve){
        this.cerceve = cerceve;
        SimdikiCerceve = 0;
        ZamanBaslatici = System.nanoTime();
        Oynanan = false; 
    }
    
    public void DelayAyarla(long d){ delay = d; }
    public void CerceveAyarla(int i){ SimdikiCerceve = i; }
    
    public void update(){
        
        if(delay == -1) return; 
        
        long gecen = (System.nanoTime() - ZamanBaslatici) / 1000000;
        if(gecen > delay){
            SimdikiCerceve++;
            ZamanBaslatici = System.nanoTime();
        }
        if(SimdikiCerceve == cerceve.length){
            SimdikiCerceve = 0;
            Oynanan = true;
        }
    }
    
    public int CerceveAl(){ return SimdikiCerceve; }
    public BufferedImage ImageAl(){ return cerceve[SimdikiCerceve]; }
    public boolean hasOynanan(){ return Oynanan; }
    
    
}
