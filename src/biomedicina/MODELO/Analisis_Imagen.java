/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biomedicina.MODELO;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;

/**
 *
 * @author ANDY
 */
public class Analisis_Imagen {
 private  File archivo = null;
 private URL nUrl ;
 private Tratamiento_Imagenes ti;
 
    public Analisis_Imagen() {
       archivo = new File("c://dientes.jpg");
       
       
    }
    
    private BufferedImage escala_de_grises(BufferedImage aux){        
        BufferedImage bi = aux;
        //Graphics2D g2d = (Graphics2D) bi.getGraphics();
        
        int r=0,g=0,b=0, promedio=0;
        Color color;
        BufferedImage memoria=bi;    
    
            //cargando imagen original en memoria
          
           /* Graphics2D g2d = memoria.createGraphics();
            g2d.drawImage(bi,0,0,null);
            g2d.dispose();*/

            //pasando a escala de grises
            for(int f=0; f<memoria.getHeight(); f++){
                for(int c=0; c<memoria.getWidth(); c++){
                color = new Color(memoria.getRGB(c, f));
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                promedio = (r+g+b)/3;
                memoria.setRGB(c, f, new Color(promedio, promedio, promedio).getRGB());
                }
            }
            //regresando el buffer en escala de grises
    
        return memoria;
    }
    
    private BufferedImage bordes(BufferedImage bi, int valor_r, int valor_g, int valor_b){
        int r=0,g=0,b=0;
        Color color;
        BufferedImage memoria=bi;
        
        for(int f=0; f<bi.getHeight(); f++){
                for(int c=0; c<bi.getWidth(); c++){
                     color = new Color(bi.getRGB(c, f));
                     r = color.getRed();
                     g = color.getGreen();
                     b = color.getBlue();
                    if(r>=valor_r && g>=valor_g && b>=valor_b){
                        
                    }else{
                        memoria.setRGB(c, f, new Color(0, 0, 0).getRGB());                    
                    }
                }
        }
    return memoria;
    }
    
    
    
    public Image cargar_imagen(int valor_r,int valor_g,int valor_b){
         Image image = null;
     try {
        
         nUrl  = new URL("http://192.168.1.104:8080/shot.jpg");
       //  nUrl  = new URL("http://192.168.1.107:8886");
         //  BufferedImage bufferedImage = ImageIO.read(archivo);
         BufferedImage bufferedImage = ImageIO.read(nUrl);
        // bufferedImage = this.escala_de_grises(bufferedImage);
        // bufferedImage = this.bordes(bufferedImage,valor_r,valor_g,valor_b);
         
         //image = SwingFXUtils.toFXImage(bufferedImage, null);
         image = SwingFXUtils.toFXImage(bufferedImage, null);
         
         
         
     } catch (IOException ex) {
         Logger.getLogger(Analisis_Imagen.class.getName()).log(Level.SEVERE, null, ex);
     }
     return image;
    }
    
    public void montar_imagen(ImageView visor, int valor_r, int valor_g, int valor_b){
         visor.setImage(this.cargar_imagen(valor_r, valor_g, valor_b));        
    }
    
    
    public void montar_imagen_alternativo(ImageView Patrones){
     Image image = null;
        
     
     try {         
         //System.out.println("ruta-->"+getClass().getResourceAsStream("/biomedicina/MODELO/IMAGENES/incisivo_sup_iz_labial.png").toString());
        // archivo = new File(getClass().getResourceAsStream("/biomedicina/DIENTES.png").toString());
         //System.out.println("existe: "+archivo.exists()+" - "+archivo.isFile());
         
         //if(archivo.exists() && archivo.isFile()){
         
            BufferedImage original = ImageIO.read(getClass().getResourceAsStream("/biomedicina/MODELO/IMAGENES/incisivo_sup_iz_labial.png"));
            
            BufferedImage bufferedImage = new BufferedImage(original.getWidth(), original.getHeight(), 2);
            bufferedImage.getGraphics().drawImage(original, 0, 0, null);
           
            ti = new Tratamiento_Imagenes(bufferedImage);
            bufferedImage = ti.escala_grises();
            bufferedImage = ti.binarizacion2(150);
            ti.extremos_figura(bufferedImage, original);
            
            
            //image = SwingFXUtils.toFXImage(bufferedImage, null);         
            image = SwingFXUtils.toFXImage(original, null);
            Patrones.setImage(image);
         //}
     } catch (IOException ex) {
         Logger.getLogger(Analisis_Imagen.class.getName()).log(Level.SEVERE, null, ex);
     }
    }
    
    
    
    
    
    
    
    
}//fin de la clase
