import com.google.gson.Gson;

public class TestApiJsonParsing 
{

   public static void main(String[] args) 
   {
      
    String data = """
                  {"status":true,"data":{"city":"Beverly Hills","state":"CA","state_fullname":"California","latitude":"34.103131","longitude":"-118.416253","population":"21741"}}                    
                     
                  """;
                      
      Gson gson = new Gson();      
      CityInfo ci = gson.fromJson(data, CityInfo.class);    
   }

}