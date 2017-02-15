/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biomedicina.MODELO;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 *
 * @author ANDY
 */
public class Capturador {
    private int contador = 0;
    private int sensibilidad = 127;
    private int red = 0, green = 0, blue = 0;    
    private BufferedImage bufferedImage = null;
    
    public Capturador() {
        /*System.out.println("cargando libreria de captura");
        System.loadLibrary("opencv_java249");*/
    }

    public int getSensibilidad() {
        return sensibilidad;
    }

    public void setSensibilidad(int sensibilidad) {
        this.sensibilidad = sensibilidad;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    
    
    
    
    
   private void adecuar(Mat matriz){
       
   }
    private BufferedImage convertir(Mat imagen) {
        MatOfByte matOfByte = new MatOfByte();
        Highgui.imencode(".jpg", imagen, matOfByte);
 
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
 
        try {
 
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return bufImage;
    }
   
   public void camara_web(ImageView imagen){
       Image fotograma = null;
       BufferedImage bufferedImage = null;
       VideoCapture camera = new VideoCapture(0);
       
       camera.open(0); //Useless
        if(!camera.isOpened()){
            System.out.println("Camara Error");
        }
        else{
            System.out.println("Camara OK?");
        }

        Mat frame = new Mat();
        
        
        camera.read(frame);
        System.out.println("Frame obtenido");
        
        System.out.println("dimension frame capturado " + frame.width() + "x"+frame.height());
        
        bufferedImage = this.convertir(frame);
        fotograma = SwingFXUtils.toFXImage(bufferedImage, null);
        imagen.setImage(fotograma);
        //Highgui.imwrite("camera.jpg", frame);//crea la foto como archivo
        
        System.out.println("OK");
        camera.release();
   }
   
   public void color_referencia(int coord_x, int coord_y){
      Color color = new Color(bufferedImage.getRGB(coord_x, coord_y));
                   red = color.getRed();
                   green = color.getGreen();
                   blue = color.getBlue();
           System.out.println("Color seleccionado: "+red+","+green+","+blue);
   }
   
   public void capturar_video(final ImageView imagen, final TextField comentario, final TextField extras,final ImageView Experimental){
       System.out.println("cargando libreria de captura");
             System.loadLibrary("opencv_java249");
             
             final Tratamiento_Imagenes ta = new Tratamiento_Imagenes(bufferedImage);//estableciendo la clase
   
       Thread procesamiento = new Thread(new Runnable() {
           @Override
           public void run() {
               //ENLANZANDO LA FUENTE DE VIDEO
             VideoCapture camera = new VideoCapture(); 
             //Camara Locar del equipo
             camera.open(0); // camara local
             
             //Direccion remota via local o web
            // camera.open("http://192.168.30.101:8080/video?dummy=param.mjpg"); // Modo camaras Ip
            
             if(!camera.isOpened()){
                    System.out.println("Camara Error");
             }else{
                    System.out.println("Camara OK?");
             }
             
             
             
             Mat frame = new Mat();//solo para inicializar
             camera.read(frame);
             bufferedImage = new Capturador().convertir(frame); //convirtiendo la captura en buffered image
             ta.setMemoria(bufferedImage);//almacenando la captura original para su posterior tratamiento
             ta.setImagen_anterior(ta.escala_grises());//incializando con la primera imagem
             
             //ta.temporizador_accion();
             
               //while(contador<=15000){
                while(contador<=15000){
                        // Thread.sleep(1000);
                           frame = new Mat();
                           camera.read(frame);
                           
                           //System.out.println("dimension frame capturado " + frame.width() + "x"+frame.height());

                           bufferedImage = new Capturador().convertir(frame); //convirtiendo la captura en buffered image
                           ta.setMemoria(bufferedImage);//almacenando la captura original para su posterior tratamiento

                           final Image fotograma = SwingFXUtils.toFXImage(bufferedImage, null);//adecuando el buffered image a Image de javafx2. Imagen original
                        //  final Image Modificacion = SwingFXUtils.toFXImage(ta.escala_grises(), null);//tratamiento de imagenes. Imagen Modificada
                        //   final Image Modificacion = SwingFXUtils.toFXImage(ta.binarizacion(sensibilidad), null);//tratamiento de imagenes. Imagen Modificada
                          //final Image Modificacion = SwingFXUtils.toFXImage(ta.variaciones(sensibilidad), null);//tratamiento de imagenes. Imagen Modificada
                        //   final Image Modificacion = SwingFXUtils.toFXImage(ta.deteccion_x_color(red, green, blue), null);//tratamiento de imagenes. Imagen Modificada
                         // final Image Modificacion = SwingFXUtils.toFXImage(ta.deteccion_movimiento(sensibilidad), null);//tratamiento de imagenes. Imagen Modificada
                         // final Image Modificacion = SwingFXUtils.toFXImage(ta.deteccion_movimiento_reflejo(sensibilidad), null);//tratamiento de imagenes. Imagen Modificada
                      //   final Image Modificacion = SwingFXUtils.toFXImage(ta.deteccion_movimiento_reflejo_letras(sensibilidad), null);//tratamiento de imagenes. Imagen Modificada
                         //  final Image Modificacion = SwingFXUtils.toFXImage(ta.escala_colores(sensibilidad), null);//tratamiento de imagenes. Imagen Modificada
                            //final Image Modificacion = SwingFXUtils.toFXImage(ta.deteccion_x_cluster(sensibilidad), null);//tratamiento de imagenes. Imagen Modificada
                        //  final Image Modificacion = SwingFXUtils.toFXImage(ta.deteccion_movimiento_horizontal(sensibilidad), null);//tratamiento de imagenes. Imagen Modificada
                        final Image Modificacion = SwingFXUtils.toFXImage(ta.localizacion_movimiento(sensibilidad), null);//tratamiento de imagenes. Imagen Modificada
                           
                           
                           contador =  1;


                             Platform.runLater(new Runnable() {
                                 @Override public void run() {
                                     imagen.setImage(fotograma);
                                     Experimental.setImage(Modificacion);
                                     comentario.setText(""+contador);
                                     
                                 }
                             });
                          //   System.out.println("contador "+contador);
               }
               
               
                  System.out.println("APAGANDO CAMARA WEB");
                  camera.release();
           }
       }, "PROCESAMIENTO");
       
       procesamiento.start();
       ta.temporizador_accion();      
         
   }
   
   
   
   
    
    
    
    
}
