/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.invirgance.convirgance.yaml;


import com.invirgance.convirgance.json.JSONArray;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ClasspathSource;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author timur
 */
public class YAMLInputTest
{
    /**
     * Test of read method, of class YAMLInput.
     */
    @Test
    public void testRead()
    {
        String expected = "[{\"name\":\"John Doe\",\"age\":30,\"email\":\"johndoe@example.com\"}," +
                          "{\"name\":\"Jane Smith\",\"age\":25,\"email\":\"janesmith@example.com\"}," +
                          "{\"name\":\"Alice Johnson\",\"age\":40,\"email\":\"alice@example.com\"}]";
        JSONArray<JSONObject> output = new JSONArray();
        YAMLInput input = new YAMLInput();
        ClasspathSource source = new ClasspathSource("/data.yaml");
        
        for (JSONObject record : input.read(source))
        {
            output.add(record);
        }
        
        assertEquals(expected, output.toString());      
    }
    
    /**
     * Test read method on empty YAML file.
     */
    @Test
    public void testEmpty()
    {
        String expected = "[]";
        JSONArray<JSONObject> output = new JSONArray();
        YAMLInput input = new YAMLInput();
        ClasspathSource source = new ClasspathSource("/empty.yaml");
        
        for (JSONObject record : input.read(source))
        {
            output.add(record);
        }
        
        assertEquals(expected, output.toString());
    }
        
    /**
     * Test read method on a YAML file with a single object.
     */
    @Test
    public void testSingle()
    {
        String expected = "[{\"name\":\"John Doe\",\"age\":30,\"email\":\"johndoe@example.com\"}]";
        JSONArray<JSONObject> output = new JSONArray();
        YAMLInput input = new YAMLInput();
        ClasspathSource source = new ClasspathSource("/single.yaml");
        
        for (JSONObject record : input.read(source))
        {
            output.add(record);
        }
        
        assertEquals(expected, output.toString());
    }
    
    /**
     * Test read method on multiple YAML files
     */
    @Test
    public void testMultipleFiles()
    {
        JSONArray<JSONObject> output = new JSONArray();
        String expected = "[{\"name\":\"John Doe\",\"age\":30,\"email\":\"johndoe@example.com\"}," +
                          "{\"name\":\"Jane Smith\",\"age\":25,\"email\":\"janesmith@example.com\"}," +
                          "{\"name\":\"Alice Johnson\",\"age\":40,\"email\":\"alice@example.com\"}," +
                          "" +
                          "{\"name\":\"John Doe\",\"age\":30,\"email\":\"johndoe@example.com\"}]";
        
        YAMLInput input = new YAMLInput();
        ClasspathSource source1 = new ClasspathSource("/data.yaml");
        ClasspathSource source2 = new ClasspathSource("/empty.yaml");
        ClasspathSource source3 = new ClasspathSource("/single.yaml");
        
        for (JSONObject record : input.read(source1))
        {
            output.add(record);
        }
        
        for (JSONObject record : input.read(source2))
        {
            output.add(record);
        }
        
        for (JSONObject record : input.read(source3))
        {
            output.add(record);
        }
        
        assertEquals(expected, output.toString());
    }
}
