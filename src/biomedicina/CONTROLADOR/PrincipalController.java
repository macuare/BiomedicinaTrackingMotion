/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package biomedicina.CONTROLADOR;

import biomedicina.MODELO.Analisis_Imagen;
import biomedicina.MODELO.Capturador;
import biomedicina.MODELO.Flujo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author ANDY
 */
public class PrincipalController implements Initializable {
    private Analisis_Imagen ai;
    private Capturador c;
    
    @FXML
    private ImageView Patrones;
     
   @FXML
    private Pane Pantalla;
   @FXML
    private ImageView Foto;
   @FXML
    private Slider Deslizador_R;
   @FXML
    private Slider Deslizador_G;
   @FXML
    private Slider Deslizador_B;
   @FXML
    private ImageView Imagen;
   @FXML
    private Button Camara;
   @FXML
    private TextField Comentario;
   @FXML
    private ImageView Experimental;
    @FXML
    private Slider Sensibilidad;   
    @FXML
    private TextField Extras;
   
    @FXML
    void REPRODUCIR(ActionEvent event) {
        Flujo f = new Flujo();
        f.configurando(Pantalla);
    }

     @FXML
    void ACTUALIZAR_R(MouseEvent event) {
        ai.montar_imagen(Foto, (int)Deslizador_R.getValue(),(int)Deslizador_G.getValue(),(int)Deslizador_B.getValue());
    }
      @FXML
    void ACTUALIZAR_G(MouseEvent event) {
        ai.montar_imagen(Foto, (int)Deslizador_R.getValue(),(int)Deslizador_G.getValue(),(int)Deslizador_B.getValue());
    }
      @FXML
    void ACTUALIZAR_B(MouseEvent event) {
        ai.montar_imagen(Foto, (int)Deslizador_R.getValue(),(int)Deslizador_G.getValue(),(int)Deslizador_B.getValue());
    }

     @FXML
    void CAMARA_WEB(ActionEvent event) {
        //c.camara_web(Imagen);
        c.capturar_video(Imagen,Comentario,Extras ,Experimental);
    }
   @FXML
    void CAMBIO_DE_VALOR(MouseEvent event) {
        
        c.setSensibilidad((int)Sensibilidad.getValue());
    }
    
    @FXML
    void SELECCIONAR_PATRON_COLOR(MouseEvent event) {
        System.out.println("COLOR DE REFERENCIA");
        c.color_referencia((int)event.getX(), (int)event.getY());
    }
    
    @FXML
    void CARGAR_IMAGEN(MouseEvent event) {
        System.out.println("CARGAR IMAGEN");
        ai.montar_imagen_alternativo(Patrones);

    }

    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     /*  ai  = new Analisis_Imagen();
       Thread hilo = new Thread(new Runnable() {
           @Override
           public void run() {
               while(true){
               Platform.runLater(new Runnable() {
                     @Override public void run() {
                         ai.montar_imagen(Foto,200,200,200);
                         try {
                             Thread.sleep(100);
                         } catch (InterruptedException ex) {
                             Logger.getLogger(PrincipalController.class.getName()).log(Level.SEVERE, null, ex);
                         }
                     }
                 });
               
               }
           }
       });
       
        hilo.start();
        */
       c  = new Capturador();
       ai = new Analisis_Imagen();
    }   
    
}
