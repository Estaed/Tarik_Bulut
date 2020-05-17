package Varliklar;

import Harita.Harita;

public class Dusmanlar extends HaritaObjeleri{
    
    protected int Can;
    protected int MaxCan;
    protected boolean Olum;
    protected int Hasar;
    
    protected boolean Kacinma;
    protected long KacinmaSuresi;
    
    
    public Dusmanlar(Harita tm) {
        super(tm);
    }
    
    public boolean Olu(){ return Olum;}
    public int HasarAyarla(){ return Hasar;}
    public void Vurus(int Hasar){
        if(Olum || Kacinma) return;
           Can -= Hasar;
           if(Can < 0) Can = 0;
           if(Can == 0) Olum = true;
           Kacinma = true;
           KacinmaSuresi = System.nanoTime();
                
    }
    
    public void update(){
        
    }
    
}
