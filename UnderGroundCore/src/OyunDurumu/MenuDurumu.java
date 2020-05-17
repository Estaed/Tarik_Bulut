package OyunDurumu;

import Harita.ArkaPlan;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MenuDurumu extends OyunDurumu{

    private ArkaPlan bg;
    
    private int SimdikiSecim = 0;
    private String[] options = {
        "Baslat", "Yardým" ,"Cýkýs"
    };
    

    
    private Color BaslikRengi;
    private Font BaslikFontu;
    
    private Font font;
    public MenuDurumu(OyunDurumuYoneticisi gsm){
        
    this.gsm = gsm;
    
       try{
           
          bg = new ArkaPlan("/Backgrounds/menubg.gif",1);
          bg.VektorAyarla(-0.1, 0);
          
          BaslikRengi = new Color(255,235,59);
          BaslikFontu = new Font("Century Gothic", Font.PLAIN, 28);//sayýlar yazý boyutu
          font = new Font("Arial", Font.PLAIN, 12);
       }
       catch (Exception e){
       }
    }
    
    @Override
    public void init(){}
    @Override
    public void update(){
       bg.update();
    }
    @Override
    public void draw(Graphics2D g){
    
        //Arka Planý çiz
        bg.draw(g);
        
        //Baþlýklarý çiz
        g.setColor(BaslikRengi);
        g.setFont(BaslikFontu);
        g.drawString("UnderGroundCore", 45, 70);
        
        //Menü Ayarlarýný çiz
        g.setFont(font);
        for(int i=0;i<options.length;i++){
            if(i==SimdikiSecim){
                g.setColor(Color.GRAY);
            }
            else{
                g.setColor(Color.RED);
            }
            g.drawString(options[i], 145, 140 + i * 15);
        }
        
    }
    
    private void select(){
        if(SimdikiSecim == 0){
            gsm.DurumBelirle(OyunDurumuYoneticisi.BOLUM1DURUMU);
        }
        if(SimdikiSecim == 1){
            //yardým
        }
        if(SimdikiSecim == 2){
            System.exit(0);
        }
    }
    
    @Override
    public void keyPressed(int k){
        if(k == KeyEvent.VK_ENTER){
            select();
        }
        if(k == KeyEvent.VK_UP){
            SimdikiSecim--;
            if(SimdikiSecim == -1){
                SimdikiSecim = options.length - 1;
            }
        }
        if(k == KeyEvent.VK_DOWN){
            SimdikiSecim++;
            if(SimdikiSecim == options.length){
                SimdikiSecim = 0;
            }
        }
    }
    @Override
    public void keyReleased(int k){}

}
