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



public class ZipCodeAppController {

    @FXML
    private Button buttonprocessor;
    
    @FXML
    private Label cityName;
   
    @FXML
    private Pane finishedproject;

    @FXML
    private Circle resultcircle;
    
    @FXML
    private Label stateName;


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
      
   CityInfo ci = new CityInfo();
   
   protected void updateUI() {
   
   
   cityName.setText(this.ci.data.city);
   stateName.setText(this.ci.data.state);
   
   // ****************************
   // You need to call set text on a label
   // You will need to put a label, or several, over the circle in your GUI
   // Give the labels clear names like cityNameLabel, stateNameLabel, etc
   // 
   // 
   // Since processZipCodeData(String) sets an instance variable, assuming the name ci, 
   //   you would do the following to access the data in your project (see processZipCodeData(String) below.
   // I'm using a print statement here but you would pass this to setText() of the label
   //   component you want to update. The process is the same for each label.
   // You can see how I do this in the sample temperature app when I update the min, max, and current temperatures.
   //
   
   //System.out.println( this.ci.data.state );
   
   // Note you can chain the call to access the data and since these items are all public
   //   you can access the variables directly by name, no need for a getter.
   //
   }
   

   protected void processZipCodeData(String data) {     
      
      
      System.out.print(data);
      Gson gson = new Gson();
      //CityInfo ci = new CityInfo();
      // ****************
      // Create an instance variable above to store the CityInfo object      
      // As written the ci variable is only visible in this method because it is declared here
      //      
      ci = gson.fromJson(data, CityInfo.class);    
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
   
   
