/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.invirgance.convirgance.yaml;


import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ClasspathSource;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "{\"name\":\"John Doe\",\"age\":30,\"email\":\"johndoe@example.com\"}\n" +
                          "{\"name\":\"Jane Smith\",\"age\":25,\"email\":\"janesmith@example.com\"}\n" +
                          "{\"name\":\"Alice Johnson\",\"age\":40,\"email\":\"alice@example.com\"}\n";
        YAMLInput input = new YAMLInput();
        ClasspathSource source = new ClasspathSource("/data.yaml");
        
        for (JSONObject record : input.read(source))
        {
            System.out.println(record);
        }
        
        assertEquals(expected, outContent.toString());
        
    }
    
    /**
     * Test read method on empty YAML file.
     */
    @Test
    public void testEmpty()
    {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "";
        YAMLInput input = new YAMLInput();
        ClasspathSource source = new ClasspathSource("/empty.yaml");
        
        for (JSONObject record : input.read(source))
        {
            System.out.println(record);
        }
        
        assertEquals(expected, outContent.toString());
        
    }
        
    /**
     * Test read method on a YAML file with a single object.
     */
    @Test
    public void testSingle()
    {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String expected = "{\"name\":\"John Doe\",\"age\":30,\"email\":\"johndoe@example.com\"}\n";
        
        YAMLInput input = new YAMLInput();
        ClasspathSource source = new ClasspathSource("/single.yaml");
        
        for (JSONObject record : input.read(source))
        {
            System.out.println(record);
        }
        
        assertEquals(expected, outContent.toString());
        
    }
    
    
}
