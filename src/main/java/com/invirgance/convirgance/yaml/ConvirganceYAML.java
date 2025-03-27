/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.invirgance.convirgance.yaml;

import com.invirgance.convirgance.CloseableIterator;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.FileSource;
import com.invirgance.convirgance.input.InputCursor;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;





/**
 *
 * @author timur
 */
public class ConvirganceYAML {

    public static void main(String[] args) 
    {
        
        // yaml parser      
//        Yaml yaml = new Yaml();
           
//        try (InputStream input = new FileInputStream("./src/test/resources/data.yaml")) 
//        {
//            // get an iterable access to records
//            Iterable<Object> records = yaml.loadAll(input); 
//            
//             call on one object at a time
//            for (Object record : records) 
//            {
//                if (record instanceof Map) 
//                {
//                    // translate into JSONObjects
//                    JSONObject object = new JSONObject((Map) record);
//                    System.out.println(object);
//                    
////                    printMap((Map) record, 0);
////                    System.out.println("---");
//                    
////                    Map<String, Object> data = (Map<String, Object>) record;
////                    printMap(data, 1);
////                    System.out.println("---------------------------");
//                    
//               
//                }
//            }
//        } catch (Exception e) 
//        {
//            e.printStackTrace();
//        } 
//        
        
        // reorganize that code into an Input
        // follow the example of CSVInput:
          // override InputCursor<JSONObject> read(Source source)
          //override iterator and its methods
          
          
          
       
        // TEST YAMLInput
        YAMLInput input = new YAMLInput();
        System.out.println(System.getProperty("user.dir"));
        System.out.println("wtf");
        FileSource source = new FileSource("src/test/resources/data.yaml");

        InputCursor<JSONObject> cursor = input.read(source);

        CloseableIterator<JSONObject> iterator = cursor.iterator();

        System.out.println("Printing");
        System.out.println(iterator.next());
          
          
       
          
          
          
    }
    
    
    // USE LATER FOR OUTPUT
     // Recursive method to print unknown YAML fields dynamically
    private static void printMap(Map<String, Object> map, int level) 
    {
        String indent = "  ".repeat(level); // Indentation for better readability
        for (Map.Entry<String, Object> entry : map.entrySet()) 
        {
            System.out.print(indent + entry.getKey() + ": ");
            if (entry.getValue() instanceof Map) 
            {
                System.out.println();
                printMap((Map<String, Object>) entry.getValue(), level + 1); // Recursively print nested structures
            } else 
            {
                System.out.println(entry.getValue());
            }
        }
    }
}
