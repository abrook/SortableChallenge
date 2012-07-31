package sortablecodingchallenge;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * Class for representing a "product" object, as in "products.txt". Only 
 * necessary information is stored. When a matching listing is found, it is 
 * added to list. 
 * 
 * @author Andrew
 */
public class Product {

    private String productName;
    private String manufacturer;
    private String family;
    private String model;
    private String originalName;
    
    private List<JSONObject> matchedListings;
    
    public static String PRODUCT_NAME_STRING = "product_name";
    public static String MANUFACTURER_STRING = "manufacturer";
    public static String FAMILY_STRING = "family";
    public static String MODEL_STRING = "model";
    public static String LISTINGS_STRING = "listings";

    public Product(String productName, String manufacturer, String family, String model) {
        this.originalName = productName;
        this.matchedListings = new ArrayList<>();
        
        this.productName = Utils.cleanString(productName);
        this.manufacturer = Utils.cleanString(manufacturer);
        this.family = Utils.cleanString(family);
        this.model = Utils.cleanString(model);
    }

    public void addListingMatch(JSONObject listing){
        matchedListings.add(listing);
    }
    
    public List<JSONObject> getListings(){
        return matchedListings;
    }
    
    public String getFamily() {
        return family;
    }

    public String getManufacturer() {
        return manufacturer;
    }
    
    public String getModel() {
        return model;
    }

    public String getProductName() {
        return productName;
    }

    public String getOriginalName() {
        return originalName;
    }
    
}
