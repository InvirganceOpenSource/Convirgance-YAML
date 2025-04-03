/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.invirgance.convirgance.yaml;

import com.invirgance.convirgance.ConvirganceException;
import com.invirgance.convirgance.json.JSONArray;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.output.OutputCursor;
import com.invirgance.convirgance.target.ByteArrayTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for YAMLOutput class.
 * @author timur
 */
public class YAMLOutputTest
{
    private ByteArrayTarget target;
    private YAMLOutput output;
    
    @BeforeEach
    void setUp() 
    { 
        target = new ByteArrayTarget();
        output = new YAMLOutput(); 
    }

    /**
     * Test of GetContentType.
     */
    @Test
    void testGetContentType()
    {
        assertEquals("application/yaml", output.getContentType());
    }
    
    /**
     * Test for writing a single object to YAML.
     */
    @Test
    void testCursorWriteSingleObject() throws Exception
    {
        JSONObject record = new JSONObject("{\"name\":\"John Doe\",\"age\":30,\"email\":\"johndoe@example.com\"}");
        String expected = "name: John Doe\n" +
                          "age: 30\n" +
                          "email: johndoe@example.com\n";

        try (OutputCursor cursor = output.write(target))
        {
            cursor.write(record);
        }
        catch(Exception e)
        {
            throw new ConvirganceException(e);
        }
        
        assertEquals(expected, new String(target.getBytes(), "UTF-8")); 
    }
    
    /**
     * Test for writing an empty JSONObject to YAML.
     */
    @Test
    void testCursorWriteEmptyObject() throws Exception
    {
        JSONObject record = new JSONObject();
        String expected = "";

        try (OutputCursor cursor = output.write(target))
        {
            cursor.write(record);
        }
        catch(Exception e)
        {
            throw new ConvirganceException(e);
        }
        
        assertEquals(expected, new String(target.getBytes(), "UTF-8")); 
    }
    
    /**
     * Test for writing multiple JSONObjects in YAML.
     */
    @Test
    void testCursorWriteMultipleObjects() throws Exception
    {
        JSONArray records = new JSONArray("[{\"name\":\"John Doe\",\"age\":30,\"email\":\"johndoe@example.com\"}," +
                                          "{\"name\":\"Jane Smith\",\"age\":25,\"email\":\"janesmith@example.com\"}," +
                                          "{\"name\":\"Alice Johnson\",\"age\":40,\"email\":\"alice@example.com\"}]");

        String expected = "---\n" +
                          "name: John Doe\n" +
                          "age: 30\n" +
                          "email: johndoe@example.com\n" +
                          "---\n" +
                          "name: Jane Smith\n" +
                          "age: 25\n" +
                          "email: janesmith@example.com\n" +
                          "---\n" +
                          "name: Alice Johnson\n" +
                          "age: 40\n" +
                          "email: alice@example.com\n";

        try (OutputCursor cursor = output.write(target))
        {
            cursor.write(records);
        }
        catch(Exception e)
        {
            throw new ConvirganceException(e);
        }
        
        assertEquals(expected, new String(target.getBytes(), "UTF-8")); 
    }
}
