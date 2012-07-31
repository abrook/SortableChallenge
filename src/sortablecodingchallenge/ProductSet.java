package sortablecodingchallenge;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * 
 * Class to map manufacturers to products. Listing objects are compared against
 * this set to find possible matches. 
 *
 * @author Andrew
 */
public class ProductSet {
    
    private Map<String, List<Product>> map;
    private Map<String, String> alternateManufacturers; // other names for same manufacturer

    public ProductSet(BufferedReader reader) throws IOException, JSONException, ParseException{
        
        map = new HashMap<>();
        alternateManufacturers = new HashMap<>();
        
        //parse "products.txt"
        String line;
        while((line = reader.readLine()) != null){
            JSONObject o = new JSONObject(new JSONTokener(line));
            add(new Product(
                    Utils.getValue(o, Product.PRODUCT_NAME_STRING), 
                    Utils.getValue(o, Product.MANUFACTURER_STRING), 
                    Utils.getValue(o, Product.FAMILY_STRING), 
                    Utils.getValue(o, Product.MODEL_STRING)));
        }
        
    }
    
    private void add(Product p){
        String key = p.getManufacturer();
        if(key == null) return; //useless w/o manufacturer
        if(!map.containsKey(key)){
            map.put(key, new ArrayList<Product>());
        }
        map.get(key).add(p);
    }
    
    /* 
     * Return all products as a list.
     */
    public List<Product> getProducts(){
        List<Product> list = new ArrayList<>();
        for(String key : map.keySet()){
            if(map.get(key) == null) continue;
            list.addAll(map.get(key));
        }
        return list;
    }
    
    /* 
     * Return list of products corresponding to a given manufacturer. If exact
     * match not found, try alternative list or add to alternative list. Return 
     * null if no match can be made. 
     */
    public List<Product> getProductsByManufacturer(String manufacturer){
        if(map.containsKey(manufacturer)){
            return map.get(manufacturer);
        }
        if(alternateManufacturers.containsKey(manufacturer) && alternateManufacturers.get(manufacturer) != null){
            return map.get(alternateManufacturers.get(manufacturer));
        }
        
        //attempt to match all words in product manufacturer with listing manufacturer
        for(String m : map.keySet()){
            boolean match = true;
            for(String word : m.split(" ")){
                if(!manufacturer.contains(word)){
                    match = false;
                    break;
                }
            }
            if(match){
                alternateManufacturers.put(manufacturer, m);
                return map.get(m);
            }
        }
        
        return null;        
    }
    
    /*
     * Try to find a Product to match Listing. Compare models and product 
     * families to listing keywords (in title). 
     */
    public void match(Listing listing){
        
        //match to list by manufacturer
        String manufacturer = listing.getManufacturer();
        List<Product> productList = getProductsByManufacturer(manufacturer);
        if(productList == null) return;
        
        //generate keywords from listing title
        List<String> keywords = listing.generateKeywords();
        
        //attempt to match product model/family to keywords
        for(Product p : productList){
            if(!searchKeywords(keywords, p.getModel())) continue;
            if(p.getFamily() != null && !searchKeywords(keywords, p.getFamily())) continue;
            p.addListingMatch(listing.getOriginal());
        }
    }
    
    /*
     * Try to match String s to set of consecutive keywords. If s not found in 
     * s single keyword, try in two adjacent words. Useful when product code is
     * sometimes split into multiple words. 
     */
    private static boolean searchKeywords(List<String> keywords, String s){
        s = s.replaceAll("-|_| ", "");
        if(keywords.contains(s)) return true;
        
        for(int i = 0; i < keywords.size()-1; i++){
            String x = keywords.get(i) + keywords.get(i+1);
            if(x.equals(s)) return true;
        }
        
        return false;
    }
    
}
