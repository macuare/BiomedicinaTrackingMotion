/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biomedicina.MODELO;

import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 *
 * @author ANDY
 */
public class Flujo {

    public Flujo() {
    }
    
    
    public String recurso(){
      //  String direccion = "http://localhost/Users/ANDY/Desktop/video.mp4";
       //String direccion = "http://www.youtube.com/watch?v=k0BWlvnBmIE";
      //String direccion = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
    //  String direccion = "http://download.oracle.com/otndocs/products/javafx/JavaRap/prog_index.m3u8";
     // String direccion = "http://192.168.1.107:8081/";
      // String direccion = "http://192.168.1.107:8888/";
     //  String direccion = "http://mirror.bigbuckbunny.de/peach/bigbuckbunny_movies/big_buck_bunny_480p_h264.mov";
      
      String direccion = "http://cdn2.telesur.ultrabase.net/livecf/telesurLive/media-u36z9q6xv.m3u8"; //TELESUR
    // String direccion = "http://cast.streamingconnect.tv:1935/vtvweb3/vtvweb3/playlist.m3u8"; //VTV
      
      //<iframe scrolling="no" marginwidth="0" marginheight="0" frameborder="0" width="650" height="400" src="http://vercanalestv.com/tv/venezuela/telesur.html"></iframe>
      // File archivo = new File("C://Users/ANDY/Desktop/glassvideo.flv");
       
      /*
        File archivo = new File("C://Users/ANDY/Desktop/video.mp4");
        try {
            direccion = archivo.toURI().toURL().toString();
            System.out.println("ruta:"+direccion);
        } catch (MalformedURLException ex) {
            Logger.getLogger(Flujo.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
    return direccion;
    }
    
  
    
    
    public void configurando(Pane pantalla){
        Media elemento = new Media(this.recurso()); System.out.println("cargando el recurso");
       
        
        MediaPlayer mp = new MediaPlayer(elemento); System.out.println("cargando el media playe");
        mp.setAutoPlay(true);
        
        MediaView mv = new MediaView(mp); System.out.println("cargando el visor del reproductor");
        mv.setPreserveRatio(false);
        mv.setFitHeight(pantalla.getHeight());
        mv.setFitWidth(pantalla.getWidth());
        pantalla.getChildren().add(mv); System.out.println("agregando todo");
        
    
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
}// fin de la clase
