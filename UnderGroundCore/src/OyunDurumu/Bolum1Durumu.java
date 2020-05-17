package OyunDurumu;

import AnaDosya.OyunPaneli;
import Harita.ArkaPlan;
import Harita.Harita;
import Varliklar.Dusmanlar;
import Varliklar.Oyuncu;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import DusmanVarliklar.Sert;
import Varliklar.Arayuz;
import Varliklar.Patlama;
import java.awt.Point;
import Sesler.SesOynaticisi;


public class Bolum1Durumu extends OyunDurumu {
    
    private Harita harita;
    private ArkaPlan bg;
    
    private Oyuncu oyuncu;
    
    private ArrayList<Dusmanlar> dusmanlar;
    private ArrayList<Patlama> patlama;
    
    private Arayuz arayuz;
    
    private SesOynaticisi bgMuzik;
    
    public Bolum1Durumu(OyunDurumuYoneticisi gsm){
        this.gsm = gsm;
        init();
    }
    


    @Override
    public void init() {
    
        harita = new Harita(30);
        harita.TemelYukle("/Tilesets/grasstileset.gif");
        harita.HaritaYukle("/Maps/level1-1.map");
        harita.PozisyonAl(0, 0);
        harita.setTween(1);
        
        
        bg = new ArkaPlan("/Backgrounds/grassbg1.gif",0.1);
        
        oyuncu = new Oyuncu(harita);
        oyuncu.PozisyonAl(100, 100);
        
        DusmanlariDoldurmak();
        
        patlama = new ArrayList<>();
        
        arayuz = new Arayuz(oyuncu);
        
        
        bgMuzik = new SesOynaticisi("/Music/level1-1.mp3");
        bgMuzik.play();
        
    }
    
    private void DusmanlariDoldurmak(){
        
        dusmanlar = new ArrayList<>();
		
		Sert s;
		Point[] points = new Point[] {
			new Point(860, 200),
			new Point(1525, 200),
			new Point(1680, 200),
			new Point(1800, 200),
			new Point(1020, 200),
			new Point(2980, 200),
			new Point(3030, 200),
			new Point(3070, 200),
			new Point(3120, 200),
			new Point(3150, 200),
			new Point(3160, 200)
		};
		for(int i = 0; i < points.length; i++) {
			s = new Sert(harita);
                        s.PozisyonAl(points[i].x, points[i].y);
			dusmanlar.add(s);
		}
		
	}

    
    @Override
    public void update() {
        //update oyuncu
        oyuncu.update();
        harita.PozisyonAl(OyunPaneli.GENISLIK / 2 - oyuncu.Al_x(), OyunPaneli.YUKSEKLIK / 2 - oyuncu.Al_y());
        
        //ArkaPlani Ayarla
        bg.PozisyonAyarla(harita.Al_x(), harita.Al_y());
        
        //Dusman saldirisi
        oyuncu.AtesKontrolEt(dusmanlar);
        
        //Butun Dusmanlari Guncelle
        for(int i=0;i<dusmanlar.size();i++){
            Dusmanlar e = dusmanlar.get(i);
            e.update();
            if(e.Olu()){
                dusmanlar.remove(i);
                i--;
                patlama.add(new Patlama(e.Al_x(), e.Al_y()));
            }
        }
        
        //update patlama
        for(int  i=0;i<patlama.size();i++){
            patlama.get(i).update();
            if(patlama.get(i).EgerRemovesa()){
                patlama.remove(i);
                i--;
            }
        }
    
    }
    
    @Override
    public void draw(Graphics2D g) {
    
        //Arkaplan çiz
        bg.draw(g);
        
        //harita çiz
        harita.Ciz(g);
        
        //Oyuncu Ciz
        oyuncu.draw(g);
        
        //Dusmanlari Ciz
        for(int i=0;i<dusmanlar.size();i++){
            dusmanlar.get(i).draw(g);
        }
        
        //Patlamayi ciz
        for(int i=0;i<patlama.size();i++){
            patlama.get(i).HaritaPozisyonAyarla((int)harita.Al_x(), (int)harita.Al_y());
            patlama.get(i).draw(g);
        }
        
        
        //Arayuz Ciz
        arayuz.draw(g);
    }
    
    @Override
    public void keyPressed(int k) {
    
        if(k == KeyEvent.VK_LEFT) oyuncu.SolAyarla(true);
		if(k == KeyEvent.VK_RIGHT) oyuncu.SagAyarla(true);
		if(k == KeyEvent.VK_UP) oyuncu.YukariAyarla(true);
		if(k == KeyEvent.VK_DOWN) oyuncu.AssagiAyarla(true);
		if(k == KeyEvent.VK_W) oyuncu.ZiplamaAyarla(true);
		if(k == KeyEvent.VK_E) oyuncu.Suzulme(true);
		if(k == KeyEvent.VK_R) oyuncu.PencelemeyiAyarla();
		if(k == KeyEvent.VK_F) oyuncu.AteslemeyiAyarla();
    
    }
    
    @Override
    public void keyReleased(int k) {
        if (k == KeyEvent.VK_LEFT) oyuncu.SolAyarla(false);
        if(k == KeyEvent.VK_RIGHT) oyuncu.SagAyarla(false);
		if(k == KeyEvent.VK_UP) oyuncu.YukariAyarla(false);
		if(k == KeyEvent.VK_DOWN) oyuncu.AssagiAyarla(false);
		if(k == KeyEvent.VK_W) oyuncu.ZiplamaAyarla(false);
		if(k == KeyEvent.VK_E) oyuncu.Suzulme(false);
    
    }

}
