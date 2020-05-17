package Sesler;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SesOynaticisi {
    
    private Clip clip;
    
    public SesOynaticisi(String s){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(s));
            AudioFormat TemelFormat = ais.getFormat();
            AudioFormat DecodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
                    TemelFormat.getSampleRate(), 
                    16, 
                    TemelFormat.getChannels(),
                    TemelFormat.getChannels() * 2,
                    TemelFormat.getSampleRate(),
                    false
            );
            
            AudioInputStream dais = AudioSystem.getAudioInputStream(DecodeFormat, ais);
            clip = AudioSystem.getClip();
            clip.open(dais);
            
        }
        catch(IOException | LineUnavailableException | UnsupportedAudioFileException e){
        }
    }
    
    public void play(){
        if(clip == null) return;
        stop();
        clip.setFramePosition(0);
        clip.start();
    }
    
    public void stop(){
        if(clip.isRunning()) clip.stop();
    }
    
    public void close(){
        stop();
        clip.close();
    }
    
}
