package sortablecodingchallenge;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Andrew
 */
public class SortableCodingChallenge {
    
    private static String FILE_PRODUCTS = "products.txt";
    private static String FILE_LISTINGS = "listings.txt";
    private static String FILE_OUTPUT = "output.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //generate readers for input files
        BufferedReader productsReader;
        BufferedReader listingsReader;                
        try {
            productsReader = new BufferedReader(new FileReader(new File(FILE_PRODUCTS)));
            listingsReader = new BufferedReader(new FileReader(new File(FILE_LISTINGS)));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SortableCodingChallenge.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        //generate products and listings
        ProductSet products = null;
        List<Listing> listings = null;
        try {
            products = new ProductSet(productsReader);
            listings = Listing.createListings(listingsReader);
        } catch (Exception ex){
            Logger.getLogger(SortableCodingChallenge.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //check each listing against existing products
        for(Listing l : listings){
            products.match(l);
        }
        
        //generate output
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(FILE_OUTPUT));
            List<Product> productList = products.getProducts();
            for(Product p : productList){
                if(!p.getListings().isEmpty()){
                    JSONObject o = new JSONObject();
                    o.put(Product.PRODUCT_NAME_STRING, p.getProductName());
                    o.put(Product.LISTINGS_STRING, new JSONArray(p.getListings()));
                    output.write(o.toString(4));
                    output.newLine();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(SortableCodingChallenge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex){
            Logger.getLogger(SortableCodingChallenge.class.getName()).log(Level.SEVERE, null, ex);
        }       

    }
}
