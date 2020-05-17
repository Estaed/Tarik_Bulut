package OyunDurumu;
public abstract class OyunDurumu {
    
    protected OyunDurumuYoneticisi gsm;
    
    public abstract void init();
    public abstract void update();
    public abstract void draw(java.awt.Graphics2D g);
    public abstract void keyPressed(int k);
    public abstract void keyReleased(int k);


}
