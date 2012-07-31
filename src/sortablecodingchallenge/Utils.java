/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sortablecodingchallenge;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Andrew
 */
public class Utils {

    /*
     * Get value from JSONObject given key. 
     */
    public static String getValue(JSONObject o, String key){
        try {
            return o.getString(key);
        } catch (JSONException ex) {
            return null; //key not found
        }
    }
    
    /*
     * Normalize strings. 
     */
    public static String cleanString(String s){
        return s == null ? null : s.trim().toLowerCase();
    }
    
}
