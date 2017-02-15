/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biomedicina.MODELO;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.SeekableByteChannel;

/**
 *
 * @author ANDY
 */
public class Experimento {

    public Experimento() {
    }
    
   public void secuencias(){
        try {
            double startSec = 51.632;
            FrameGrab grab = new FrameGrab((SeekableByteChannel) new File("filename.mp4"));
            grab.seekToFramePrecise((int) startSec);
            for (int i = 0; i < 100; i++) {
                ImageIO.write(grab.getFrame(), "png",
                        new File(System.getProperty("user.home"), String.format("Desktop/frame_%08d.png", i)));
            }   } catch (IOException ex) {
            Logger.getLogger(Experimento.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JCodecException ex) {
            Logger.getLogger(Experimento.class.getName()).log(Level.SEVERE, null, ex);
        }
   
   } 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}// fin de la clase
