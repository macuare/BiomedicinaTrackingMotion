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
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 *
 * @author ANDY
 */
public class Tratamiento_Imagenes {
    private BufferedImage memoria;
    private BufferedImage imagen_anterior;
    private LinkedList<Integer> valores;
    private int delta_pixeles;

    public Tratamiento_Imagenes() {
    }
   
   
    
    public Tratamiento_Imagenes(BufferedImage original) {
        delta_pixeles = 0;
        memoria = original; //obteniendo todos los datos de la imagen capturada
        valores = new LinkedList<>();
    }

    public BufferedImage getMemoria() {
        return memoria;
    }

    public void setMemoria(BufferedImage memoria) {
        this.memoria = memoria;
    }

    public BufferedImage getImagen_anterior() {
        return imagen_anterior;
    }

    public void setImagen_anterior(BufferedImage imagen_anterior) {
        this.imagen_anterior = imagen_anterior;
    }

   
   
    
    
    
    private void limites(BufferedImage imagen){
        int ancho = imagen.getWidth();//640
        int alto = imagen.getHeight();//480
        int centro_x = ancho/2, centro_y = alto/2;
        int ajuste_x = 100, ajuste_y = 100;
        
        Graphics2D g2d = (Graphics2D) imagen.getGraphics();//obteniendo la referencia de la imagen para agregar artefactos de interes
        g2d.setColor(Color.blue);
        g2d.drawString("Ms.C. Cusatti Andy", 10, 470);// colocando etiqueta
        
        g2d.drawLine(centro_x-ajuste_x, centro_y-ajuste_y, centro_x+ajuste_x, centro_y-ajuste_y);// linea superior
        g2d.drawLine(centro_x-ajuste_x, centro_y+ajuste_y, centro_x+ajuste_x, centro_y+ajuste_y);// linea inferior
        g2d.drawLine(centro_x-ajuste_x, centro_y-ajuste_y, centro_x-ajuste_x, centro_y+ajuste_y);// linea vertical izquierda
        g2d.drawLine(centro_x+ajuste_x, centro_y-ajuste_y, centro_x+ajuste_x, centro_y+ajuste_y);// linea vertical derecha
        
        g2d.dispose();//liberando la memoria para edicion de la imagen
    }
    
    
    public BufferedImage escala_grises(){
        Color color = null;
        int r,g,b,promedio;
        
        BufferedImage modificado = memoria;//tomando la copia de la memoria
            
        for(int f=0; f<modificado.getHeight(); f++){
                for(int c=0; c<modificado.getWidth(); c++){
                    color = new Color(modificado.getRGB(c, f));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    promedio = (r+g+b)/3;
                    modificado.setRGB(c, f, new Color(promedio, promedio, promedio).getRGB());
                }
        }        
      
        //this.limites(modificado);
        return modificado;    
    }
    
    public BufferedImage escala_colores(int sensibilidad){
        Color color = null;
        int r,g,b,promedio;
        
        BufferedImage modificado = memoria;//tomando la copia de la memoria
            
        for(int f=0; f<modificado.getHeight(); f++){
                for(int c=0; c<modificado.getWidth(); c++){
                    if(sensibilidad<=85){
                        color = new Color(modificado.getRGB(c, f));
                        r = color.getRed();                    
                        modificado.setRGB(c, f, new Color(r, 0, 0).getRGB());
                    }
                    if(sensibilidad>85 && sensibilidad<=170){
                        color = new Color(modificado.getRGB(c, f));
                        g = color.getGreen();                    
                        modificado.setRGB(c, f, new Color(g, 0, 0).getRGB());
                    }
                    if(sensibilidad>170){
                        color = new Color(modificado.getRGB(c, f));
                        b = color.getBlue();                    
                        modificado.setRGB(c, f, new Color(b, 0, 0).getRGB());
                    }
                    
                }
        }        
      
      //  this.limites(modificado);
        return modificado;    
    }
    
    public BufferedImage binarizacion(int umbral){
        Color color = null;
        int r,g,b,promedio;
        
        BufferedImage modificado = memoria; // tomando la copia de la original
            
        for(int f=0; f<modificado.getHeight(); f++){
                for(int c=0; c<modificado.getWidth(); c++){
                    color = new Color(modificado.getRGB(c, f));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    promedio = (r+g+b)/3;
                        if(promedio > umbral){
                            modificado.setRGB(c, f, new Color(0,0,0).getRGB());
                        }else{
                            modificado.setRGB(c, f, new Color(255,255,255).getRGB());
                        }     
                }
        }        
      
        this.limites(modificado);
        return modificado;   
    
    }
    
    public BufferedImage binarizacion2(int umbral){
        Color color = null;
        int r,g,b,promedio;
        
        BufferedImage modificado = memoria; // tomando la copia de la original
            
        for(int f=0; f<modificado.getHeight(); f++){
                for(int c=0; c<modificado.getWidth(); c++){
                    color = new Color(modificado.getRGB(c, f));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    promedio = (r+g+b)/3;
                        if(promedio > umbral){
                            modificado.setRGB(c, f, new Color(0,0,0).getRGB());
                        }else{
                            modificado.setRGB(c, f, new Color(255,255,255).getRGB());
                        }     
                }
        }        
      
        return modificado;   
    }
    
    public void extremos_figura(BufferedImage imagen, BufferedImage original){
        Color color = null;
        int r,g,b;
        int x, y;
        int xmin = 1000000, ymin = 1000000, xmax = 0, ymax = 0;
        
        BufferedImage modificado = imagen; // tomando la copia de la original
        for(int f=0; f<modificado.getHeight(); f++){
                for(int c=0; c<modificado.getWidth(); c++){
                    color = new Color(modificado.getRGB(c, f));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    x = c;
                    y = f;
                    if(r == 255 && g == 255 && b == 255){
                        if(x<xmin) xmin = x;
                        if(x>xmax) xmax = x;
                        if(y<ymin) ymin = y;
                        if(y>ymax) ymax = y;
                        
                    }
                }
        }
        
        System.out.println("valores: min: "+xmin+","+ymin+" - "+xmax+","+ymax);
        System.out.println("horizontales");
        
        Graphics2D g2d = (Graphics2D) original.getGraphics();
        g2d.setColor(Color.BLUE);
        
        for(int h=1; h<=9; h++){
            g2d.drawLine(xmin, (ymax/9)*h, xmax, (ymax/9)*h);        
        }
        for(int v=1; v<=9; v++){
            g2d.drawLine((xmax/9)*v, ymin, (xmax/9)*v, ymax);        
        }
        
        g2d.dispose();
    }
    
    public BufferedImage variaciones(int delta){
        BufferedImage actual = escala_grises(); // tomando la modificacion de la original
        BufferedImage  vieja = imagen_anterior; // tomando la copia de la original        
        Color color = null;
        int pixel_a = 0, pixel_b = 0, calculo = 0;
        
        Graphics2D g2d = (Graphics2D) actual.getGraphics();//usando modificador especializado para dibujar figuras
        
        //las imagenes estan en escala de grises. por lo tanto cada componente rgb es igual al promedio de la suma ponderada de los tres componentes. componente con valor unico por pixel
        
        for(int filas = 0; filas < actual.getHeight(); filas++){//analizando por filas
            for( int columnas = 0; columnas < actual.getWidth(); columnas++){//recorrido por columnas
                color = new Color(actual.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen actual
                pixel_a = color.getRed();
                color = new Color(vieja.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen vieja
                pixel_b = color.getRed();
                
                calculo = Math.abs(pixel_a - pixel_b);
                if(calculo >= delta){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel
                    g2d.drawOval(columnas, filas, 1, 1);
                }
                
            }//fin recorrido por columnas
        }//fin analisis por filas
        
        
        g2d.dispose();//liberando buffer de memoria temporal
        this.setImagen_anterior(actual);//imagen actual en escala de grises ahora como anterior para proximo analisis
        return actual;
    
    }
    
    public BufferedImage deteccion_movimiento(int delta){
        BufferedImage actual = escala_grises(); // tomando la modificacion de la original
        BufferedImage  vieja = imagen_anterior; // tomando la copia de la original        
        Color color = null;
        int pixel_a = 0, pixel_b = 0, calculo = 0;
        
        Graphics2D g2d = (Graphics2D) actual.getGraphics();//usando modificador especializado para dibujar figuras
        
        //las imagenes estan en escala de grises. por lo tanto cada componente rgb es igual al promedio de la suma ponderada de los tres componentes. componente con valor unico por pixel
        
        for(int filas = 0; filas < actual.getHeight(); filas++){//analizando por filas
            for( int columnas = 0; columnas < actual.getWidth(); columnas++){//recorrido por columnas
                color = new Color(actual.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen actual
                pixel_a = color.getRed();
                color = new Color(vieja.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen vieja
                pixel_b = color.getRed();
                
                calculo = Math.abs(pixel_a - pixel_b);
                if(calculo == delta){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel
                    g2d.drawOval(columnas, filas, 1, 1);
                }
                
            }//fin recorrido por columnas
        }//fin analisis por filas
        
        
        g2d.dispose();//liberando buffer de memoria temporal
        this.setImagen_anterior(actual);//imagen actual en escala de grises ahora como anterior para proximo analisis
        return actual;
    
    }
    
    public BufferedImage deteccion_movimiento_reflejo(int delta){
        BufferedImage actual = escala_grises(); // tomando la modificacion de la original
        BufferedImage  vieja = imagen_anterior; // tomando la copia de la original
        BufferedImage  reflejo = new BufferedImage(actual.getWidth(), actual.getHeight(), BufferedImage.TYPE_INT_ARGB); // tomando la copia de la original
        
        Color color = null;
        int pixel_a = 0, pixel_b = 0, calculo = 0;
        int magnitud = 0;
        
        Graphics2D g2d = (Graphics2D) actual.getGraphics();//usando modificador especializado para dibujar figuras
        Graphics2D g2d1 = (Graphics2D) reflejo.createGraphics();//usando modificador especializado para dibujar figuras
        g2d1.setBackground(Color.BLACK);
        g2d1.setColor(Color.BLACK);
        //las imagenes estan en escala de grises. por lo tanto cada componente rgb es igual al promedio de la suma ponderada de los tres componentes. componente con valor unico por pixel
        
        for(int filas = 0; filas < actual.getHeight(); filas++){//analizando por filas
            for( int columnas = 0; columnas < actual.getWidth(); columnas++){//recorrido por columnas
                color = new Color(actual.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen actual
                pixel_a = color.getRed();
                color = new Color(vieja.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen vieja
                pixel_b = color.getRed();
                
                calculo = Math.abs(pixel_a - pixel_b);
                if(calculo >= delta && calculo <= delta+10){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel                   
                    g2d1.drawOval(columnas, filas, 1, 1);                   
                    magnitud++;//contando los pixeles comprometidos
                }
                
            }//fin recorrido por columnas
        }//fin analisis por filas
        
        g2d1.setColor(Color.blue);
        g2d1.drawString("PXs: "+magnitud, 10, 10);
        g2d.dispose();//liberando buffer de memoria temporal
        g2d1.dispose();//liberando buffer de memoria temporal
        this.setImagen_anterior(actual);//imagen actual en escala de grises ahora como anterior para proximo analisis
        return reflejo;
    
    }
    
    
    
    public BufferedImage deteccion_movimiento_reflejo_letras(int delta){
        BufferedImage actual = escala_grises(); // tomando la modificacion de la original
        BufferedImage  vieja = imagen_anterior; // tomando la copia de la original
        BufferedImage  reflejo = new BufferedImage(actual.getWidth(), actual.getHeight(), BufferedImage.TYPE_INT_ARGB); // tomando la copia de la original
        
        Color color = null;
        int pixel_a = 0, pixel_b = 0, calculo = 0;
        
        Graphics2D g2d = (Graphics2D) actual.getGraphics();//usando modificador especializado para dibujar figuras
        Graphics2D g2d1 = (Graphics2D) reflejo.createGraphics();//usando modificador especializado para dibujar figuras
        g2d1.setBackground(Color.BLACK);
        g2d1.setColor(Color.BLACK);
        //las imagenes estan en escala de grises. por lo tanto cada componente rgb es igual al promedio de la suma ponderada de los tres componentes. componente con valor unico por pixel
        
        for(int filas = 0; filas < actual.getHeight(); filas++){//analizando por filas
            for( int columnas = 0; columnas < actual.getWidth(); columnas++){//recorrido por columnas
                color = new Color(actual.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen actual
                pixel_a = color.getRed();
                color = new Color(vieja.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen vieja
                pixel_b = color.getRed();
                
                calculo = Math.abs(pixel_a - pixel_b);
               
                if(calculo >= delta && calculo <= delta+1){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel                   
                    g2d1.drawString("a",columnas, filas); 
                }
                if(calculo > delta+1 && calculo <= delta+2){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel                   
                    g2d1.drawString("b",columnas, filas); 
                }
                if(calculo > delta+2 && calculo <= delta+3){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel                   
                    g2d1.drawString("c",columnas, filas);  
                }
                if(calculo > delta+3 && calculo <= delta+4){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel                   
                    g2d1.drawString("d",columnas, filas);  
                }
                if(calculo > delta+4 && calculo <= delta+5){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel                   
                    g2d1.drawString("x",columnas, filas);  
                }
                
            }//fin recorrido por columnas
        }//fin analisis por filas
        
        
        g2d.dispose();//liberando buffer de memoria temporal
        g2d1.dispose();//liberando buffer de memoria temporal
        this.setImagen_anterior(actual);//imagen actual en escala de grises ahora como anterior para proximo analisis
        return reflejo;
    
    }
    
    
    public BufferedImage deteccion_x_cluster(int delta){
        BufferedImage actual = escala_grises(); // tomando la modificacion de la original
        BufferedImage  vieja = imagen_anterior; // tomando la copia de la original
        BufferedImage  reflejo = new BufferedImage(actual.getWidth(), actual.getHeight(), BufferedImage.TYPE_INT_ARGB); // tomando la copia de la original
        
        Color color = null;
        int pixel_a = 0, pixel_b = 0, calculo = 0;
        int magnitud = 0;
        
        //Graphics2D g2d = (Graphics2D) actual.getGraphics();//usando modificador especializado para dibujar figuras
        Graphics2D g2d1 = (Graphics2D) reflejo.createGraphics();//usando modificador especializado para dibujar figuras
        g2d1.setBackground(Color.BLACK);
        g2d1.setColor(Color.BLACK);
        //las imagenes estan en escala de grises. por lo tanto cada componente rgb es igual al promedio de la suma ponderada de los tres componentes. componente con valor unico por pixel
        
        for(int filas = 0; filas < actual.getHeight()-3; filas=filas+3){//analizando por filas
            for( int columnas = 0; columnas < actual.getWidth()-3; columnas=columnas+3){//recorrido por columnas
                color = new Color(actual.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen actual
                pixel_a = color.getRed();
                color = new Color(vieja.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen vieja
                pixel_b = color.getRed();
                
                calculo = Math.abs(pixel_a - pixel_b);
                if(calculo >= delta && calculo <= delta+10){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel                   
                    g2d1.drawOval(columnas, filas, 1, 1);                   
                    magnitud++;//contando los pixeles comprometidos
                }
                
            }//fin recorrido por columnas
        }//fin analisis por filas
        
        this.setImagen_anterior(actual);//imagen actual en escala de grises ahora como anterior para proximo analisis
        
        
        
        
        g2d1.setColor(Color.blue);
        g2d1.drawRect(200, 100, 340, 280);
        
        g2d1.drawString("Ms.C. Cusatti Andy ", 10, 10);
        g2d1.drawString("PXs: "+magnitud, 10, 25);        
        g2d1.dispose();//liberando buffer de memoria temporal
        //g2d1.dispose();//liberando buffer de memoria temporal
        
        return reflejo;
    
    }
    
    
    
    
    
    
    
    public BufferedImage deteccion_x_color(int red, int green, int blue){
        Color color = null;
        int r,g,b,promedio;
        int ref_r = red, ref_g = green, ref_b = blue;
        
        
        BufferedImage modificado = memoria;//tomando la copia de la memoria
        Graphics2D g2d = (Graphics2D) modificado.getGraphics();
                
        
        for(int f=0; f<modificado.getHeight(); f++){
                for(int c=0; c<modificado.getWidth(); c++){
                    color = new Color(modificado.getRGB(c, f));
                    r = color.getRed();
                    g = color.getGreen();
                    b = color.getBlue();
                    
                    if((r>=ref_r-10 && r<=ref_r+10) && 
                       (g>=ref_g-5 && g<=ref_g+5) && 
                       (b>=ref_b && b<=ref_b+1)
                            ){//sensibilidad al color
                        g2d.drawOval(c, f, 2, 2);
                    }
                    
                }
        }        
      
      //  this.limites(modificado);
        return modificado;    
    }
    
    public BufferedImage deteccion_movimiento_horizontal(int delta){
        BufferedImage actual = escala_grises(); // tomando la modificacion de la original
        BufferedImage  vieja = imagen_anterior; // tomando la copia de la original
        BufferedImage  reflejo = new BufferedImage(actual.getWidth(), actual.getHeight(), BufferedImage.TYPE_INT_ARGB); // tomando la copia de la original
        
        Color color = null;
        int pixel_a = 0, pixel_b = 0, calculo = 0;
        int magnitud = 0;
        int inicio = 1000, fin = 0, medio = 0; 
        
        Graphics2D g2d = (Graphics2D) actual.getGraphics();//usando modificador especializado para dibujar figuras
        Graphics2D g2d1 = (Graphics2D) reflejo.createGraphics();//usando modificador especializado para dibujar figuras
        g2d1.setBackground(Color.BLACK);
        g2d1.setColor(Color.BLACK);
        //las imagenes estan en escala de grises. por lo tanto cada componente rgb es igual al promedio de la suma ponderada de los tres componentes. componente con valor unico por pixel
        
        for(int filas = 0; filas < actual.getHeight(); filas++){//analizando por filas
            for( int columnas = 0; columnas < actual.getWidth(); columnas++){//recorrido por columnas
                color = new Color(actual.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen actual
                pixel_a = color.getRed();
                color = new Color(vieja.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen vieja
                pixel_b = color.getRed();                
                calculo = Math.abs(pixel_a - pixel_b); //resta de ambas magnitudes
               
                
                
                if(calculo >= delta && calculo <= delta+1){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel                   
                    g2d1.setColor(Color.black);
                    g2d1.drawOval(columnas, filas, 1, 1);                   
                    if(columnas<inicio){inicio = columnas;}
                    if(columnas>fin){fin = columnas;}                    
                    magnitud++;//contando los pixeles comprometidos
                }                
            }//fin recorrido por columnas
            
                        
                        if(inicio > 10 && fin < 600){//no hubo relevancia en la deteccion horizontal de la fila en analisis                        
                            medio = (fin - inicio)/2;//solo tomo la parte entera. los pixeles son enteros
                           // System.out.println(inicio+","+fin+"  medio:"+medio);
                            g2d1.setColor(Color.red);
                            g2d1.drawOval(inicio+medio, filas, 2, 2);
                        }
                        inicio = 1000; fin =0; //incializando las variables por cada analisis horizontal
            
            
        }//fin analisis por filas
        
        g2d1.setColor(Color.blue);
        g2d1.drawString("PXs: "+magnitud, 10, 10);
        g2d.dispose();//liberando buffer de memoria temporal
        g2d1.dispose();//liberando buffer de memoria temporal
        this.setImagen_anterior(actual);//imagen actual en escala de grises ahora como anterior para proximo analisis
        return reflejo;
    
    }
    
    public BufferedImage localizacion_movimiento(int delta){
        BufferedImage actual = escala_grises(); // tomando la modificacion de la original
        BufferedImage  vieja = imagen_anterior; // tomando la copia de la original
        BufferedImage  reflejo = new BufferedImage(actual.getWidth(), actual.getHeight(), BufferedImage.TYPE_INT_ARGB); // tomando la copia de la original
        
        Color color = null;
        int pixel_a = 0, pixel_b = 0, calculo = 0;
        int magnitud = 0;
        int  x1 = 1000, x2 = 0, y1 = 1000, y2 = 0; 
        
        Graphics2D g2d = (Graphics2D) actual.getGraphics();//usando modificador especializado para dibujar figuras
        Graphics2D g2d1 = (Graphics2D) reflejo.createGraphics();//usando modificador especializado para dibujar figuras
        g2d1.setBackground(Color.BLACK);
        g2d1.setColor(Color.BLACK);
        //las imagenes estan en escala de grises. por lo tanto cada componente rgb es igual al promedio de la suma ponderada de los tres componentes. componente con valor unico por pixel
        
        for(int filas = 0; filas < actual.getHeight(); filas++){//analizando por filas
            for( int columnas = 0; columnas < actual.getWidth(); columnas++){//recorrido por columnas
                color = new Color(actual.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen actual
                pixel_a = color.getRed();
                color = new Color(vieja.getRGB(columnas, filas));//obtenienco el componente color del pixel de la imagen vieja
                pixel_b = color.getRed();                
                calculo = Math.abs(pixel_a - pixel_b); //resta de ambas magnitudes
               
                
                
                if(calculo >= delta && calculo <= delta+10){// si es igual al delta de variacion. se asume que hubo un cambio en ese pixel                   
                    g2d1.setColor(Color.black);
                    g2d1.drawOval(columnas, filas, 1, 1);                   
                        if(columnas < x1) x1 = columnas;
                        if(columnas > x2) x2 = columnas;
                        if(filas < y1) y1 = filas;
                        if(filas > y2) y2 = filas;
                    magnitud++;//contando los pixeles comprometidos
                }                
            }//fin recorrido por columnas
            
        }//fin analisis por filas
        
        if(x1<1000 && x2>0 && y1<1000 && y2>0){//dibujandoo cuadro
                           // System.out.println("cuadro");
                            g2d1.setColor(Color.red);
                            g2d1.drawRect(x1, y1, (x2-x1), (y2-y1));
                        }
       //  x1 = 1000; x2 = 0; y1 = 1000; y2 = 0;//inicializando nuevamente las variables para el analisis de la proxima de fila
            
        delta_pixeles = magnitud;
        g2d1.setColor(Color.blue);
        //g2d1.drawString("PXs: "+magnitud, 10, 10);
        //this.graficas(g2d1, magnitud);
        g2d1.drawString("PXs: "+delta_pixeles, 10, 10);
        this.graficas(g2d1, delta_pixeles);
        g2d.dispose();//liberando buffer de memoria temporal
        g2d1.dispose();//liberando buffer de memoria temporal
        this.setImagen_anterior(actual);//imagen actual en escala de grises ahora como anterior para proximo analisis
        return reflejo;
    
    }

    public void reproducir_alarma(){
        
                        /*Platform.runLater(new Runnable() {
                            @Override public void run() {  */
                                System.out.println("reproduciendo alarma");
                                //String direccion = "C:\\Users\\ANDY\\Music\\Musica\\Coldplay - (2002) A Rush Of Blood To The Head {iMog}\\05 - Clocks.mp3";
                                String direccion = "C:\\Users\\ANDY\\Music\\Musica\\Coldplay - (2002) A Rush Of Blood To The Head {iMog}\\regina.mp3";
                                Media media = new Media(new File(direccion).toURI().toString());
                                MediaPlayer mediaPlayer = new MediaPlayer(media);
                                //mediaPlayer.setAutoPlay(true);
                                mediaPlayer.play();
                                MediaView mediaView = new MediaView(mediaPlayer);
                                
                                System.out.println("duracion: "+media.getDuration().toSeconds());
                                while(mediaPlayer.getCurrentTime().lessThan(media.getDuration())){
                                    System.out.println("revisando:\n  actual:"+mediaPlayer.getCurrentTime().toSeconds()+"\n  total:"+media.getDuration().toSeconds());
                                           try {
                                                Thread.sleep(1000);
                                           } catch (InterruptedException ex) {
                                                Logger.getLogger(Tratamiento_Imagenes.class.getName()).log(Level.SEVERE, null, ex);
                                           }
                                }
                                
                                System.out.println("FIN MUSICA");
                               
                            /*}
                        });*/
                        
    }
    
    
    public void temporizador_accion(){
        System.out.println("VIGILANDO");
       
        new Thread(new Runnable() {
            @Override
            public void run() {
                long t1=System.currentTimeMillis(), t2=t1;
                                    System.out.println("iniciando hilo");
                                    
                                    while((t2-t1)<=40000){ // 40s
                                        if(delta_pixeles <= 0){
                                            t2 = System.currentTimeMillis(); // actualizo el t2 cuando no hay movimiento
                                        }else{
                                          //  System.out.println("Reiniciando cuenta");
                                            t2 = System.currentTimeMillis(); // actualizo el t2
                                            t1 = t2; // actualizo a los nuevos tiempos.. es como resetear
                                        }
                                    }
                                    System.out.println("A L A R M A");
                                    reproducir_alarma();
                
            }
            
        }).start();
        
    }
    
    
   public void graficas(Graphics2D g2d, int valor){
       g2d.setColor(Color.GREEN);
       if(valores.isEmpty()){
           valores.add(valor);
       }else{
           for(int i=0; i<valores.size(); i++){
                        g2d.drawLine(50+i,450, 50+i, (450-(valores.get(i)/100)));
           }
           if(valores.size()>500){                   
               valores.clear();                
               valores.add(valor);
           }else{
               valores.add(valor);
           }
       
       }
       
   
   
   
   
   }
    
    
    
}// fin de la clase
