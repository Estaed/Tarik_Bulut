package OyunDurumu;


public class OyunDurumuYoneticisi {
    private final OyunDurumu[] oyunDurumu;
    private int SimdikiDurum; //currentState;
    
    public static final int NUMOYUNDURUMU = 2;
    public static final int MENUDURUMU = 0;
    public static final int BOLUM1DURUMU = 1;
            
    public OyunDurumuYoneticisi(){
        oyunDurumu = new OyunDurumu[NUMOYUNDURUMU];
        
        SimdikiDurum = MENUDURUMU;
        DurumYukle(SimdikiDurum);
    }
    
    
    public void DurumYukle(int durum){
        if(durum == MENUDURUMU)
            oyunDurumu[durum] = new MenuDurumu(this);
            if(durum == BOLUM1DURUMU)
                oyunDurumu[durum] = new Bolum1Durumu(this);
    }
    
    public void DurumYukleme(int durum){
        oyunDurumu[durum] = null;
    }
    
    public void DurumBelirle(int durum){
        DurumYukleme(SimdikiDurum);
        SimdikiDurum = durum;
        DurumYukle(SimdikiDurum);
        //oyunDurumu[SimdikiDurum].init();
    }
    
    public void update(){
        
        try{
        oyunDurumu[SimdikiDurum].update();
        }
        catch(Exception e){}
    }
    
    public void draw(java.awt.Graphics2D g){
        try{
        oyunDurumu[SimdikiDurum].draw(g);
        }
         catch(Exception e){}
    }
    
    public void keyPressed(int k){
        oyunDurumu[SimdikiDurum].keyPressed(k);
    }
    
    public void keyReleased(int k){
        oyunDurumu[SimdikiDurum].keyReleased(k);
    }
}
