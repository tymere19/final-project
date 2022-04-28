import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.URL;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.IOException;
import com.google.gson.Gson;
import javafx.application.Platform;
//private HttpClient client;
//private String Cityinfo ci;
//private String updatecity; 
//private String updatestate;
//private String updatestate_fullname;
//private double updatelatitude;
//private double updatelongitude;


public class ZipCodeAppController {

    @FXML
    private Button buttonprocessor;

    @FXML
    private Pane finishedproject;

    @FXML
    private Circle resultcircle;

    @FXML
    private Label title;

    @FXML
    private TextField zipcodeinsert;
    private String zip;
 
    
    private HttpClient client;
 
 public static final String TEMP_ZIPKEY = "zipcode_key";
  
   @FXML 
   protected void getzipcodedata(ActionEvent event) {
      System.out.println("button has been pressed");   
    zip = zipcodeinsert.getText().trim(); 
    System.out.println(zip);
    updateZipCodeData();
   }  
      
   protected void updateUI() {
   

   }
   

   protected void processZipCodeData(String data) {     
      
      
      System.out.print(data);
      Gson gson = new Gson();      
      CityInfo ci = gson.fromJson(data, CityInfo.class);    
      Platform.runLater( new Runnable() {
                           public void run() {
                              updateUI();
                           }
                        });
   }
   

   protected void updateZipCodeData() {
   
      if(this.client == null)
         this.client = HttpClient.newHttpClient();
    
     
      try {
           
            HttpRequest request = HttpRequest.newBuilder()            
              .uri(new URI("https://service.zipapi.us/zipcode/" + this.zip + "?X-API-KEY=js-e0849a56f79e3e72c801c8087d938a43&fields=geolocation,population" ))
              .GET()
              .build();
              
            
           client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(this::processZipCodeData);     
           
          } catch(URISyntaxException e) { 
            
            System.out.println("please wait");
          }
          
      
      System.out.println("Updating your information...... one sec ...");
      
   }
   
   
 }
   
   

