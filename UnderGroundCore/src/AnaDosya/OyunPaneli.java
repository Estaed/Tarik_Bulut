package AnaDosya;

import OyunDurumu.OyunDurumuYoneticisi;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.JPanel;

public class OyunPaneli extends JPanel implements Runnable, KeyListener{
    //Boyutlar = Dimension
    public static final int GENISLIK = 320;
    public static final int YUKSEKLIK = 240;
    public static final int OLCEKLI = 2;
    
    //Oyunun Konusu thread=konu
    private Thread thread;
    private boolean running;
    private final int FPS = 60;
    private final long targetTime = 1000/FPS;
    
    //Görseller
    private BufferedImage image;
    private Graphics2D g;
    
    //oyun durumu yöneticisi
    private OyunDurumuYoneticisi gsm;
    
    
    public OyunPaneli(){
      super();
      setPreferredSize(new Dimension(GENISLIK * OLCEKLI, YUKSEKLIK * OLCEKLI));
      setFocusable(true);
      requestFocus();
    }
    
    @Override
    public void addNotify(){
        super.addNotify();
        if(thread == null){
            thread = new Thread(this);
            addKeyListener(this);
            thread.start();
        }
    }
    
    private void init(){
        
        image = new BufferedImage(GENISLIK,YUKSEKLIK,BufferedImage.TYPE_INT_RGB);
        g = (Graphics2D) image.getGraphics();
        
        running = true;
        
        gsm = new OyunDurumuYoneticisi();
    }    
    @Override
    public void run(){
        
        init();
        
        long start;
        long elapsed; //geçen demekmiþ
        long wait;
        //Oyunun Döngüsü Yapýlacak
        while(running){
            
            start = System.nanoTime();
            
            update();
            draw();
            drawToScreen();
            
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;
            if(wait < 0 ) wait = 5;
            
            try{
                Thread.sleep(wait); 
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        
    }
    
    private void update(){
       gsm.update();
    }
    private void draw(){
       gsm.draw(g);
    }
    private void drawToScreen(){
        Graphics g2 = getGraphics();
        g2.drawImage(image,0,0, GENISLIK * OLCEKLI, YUKSEKLIK * OLCEKLI, null);
        g2.dispose();
    }
    

    @Override
    public void keyTyped(KeyEvent key) {}
    @Override
    public void keyPressed(KeyEvent key) {
       gsm.keyPressed(key.getKeyCode());
    }
    @Override
   	public void keyReleased(KeyEvent key) {
   		gsm.keyReleased(key.getKeyCode());
   	}

}

