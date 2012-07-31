package sortablecodingchallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * Class for representing listing object, as in "listings.txt". Only useful
 * information is stored. 
 * 
 * @author Andrew
 */
public class Listing {
    
    private String title;
    private String manufacturer;
    
    //stored for ease of use
    private JSONObject original;
    
    public static String TITLE_STRING = "title";
    public static String MANUFACTURER_STRING = "manufacturer";
    
    public Listing(String title, String manufacturer, JSONObject original){
        this.title = Utils.cleanString(title);
        this.manufacturer = Utils.cleanString(manufacturer);
        this.original = original;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getTitle() {
        return title;
    }

    public JSONObject getOriginal() {
        return original;
    }
    
    public String toString() {
        return original.toString();
    }
    
    /*
     * Split "title" section of listing to get a set of keywords to compare
     * to Product objects. 
     */
    public List<String> generateKeywords(){
        String[] list = this.title.split(" ");
        List<String> result = new ArrayList<>();
        for(String s : list){
            s = s.replaceAll("\\(|\\)|-|_", "");
            if(s.length() > 1){
                result.add(s);
            }
        }
        return result;
    }

    /*
     * Read from "listings.txt" and generate set of Listing objects. 
     */
    public static List<Listing> createListings(BufferedReader reader) throws IOException, JSONException, ParseException{
        
        List<Listing> listings = new ArrayList<>();
        
        String line;
        while((line = reader.readLine()) != null){
            JSONObject o = new JSONObject(new JSONTokener(line));
            if(Utils.getValue(o, Listing.MANUFACTURER_STRING) == null) continue; //useless without manufacturer
            listings.add(new Listing(
                    Utils.getValue(o, Listing.TITLE_STRING), 
                    Utils.getValue(o, Listing.MANUFACTURER_STRING), 
                    o));
        }
        
        return listings;
    }
    
}
