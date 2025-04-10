/*
 * Copyright 2024 INVIRGANCE LLC

Permission is hereby granted, free of charge, to any person obtaining a copy 
of this software and associated documentation files (the “Software”), to deal 
in the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies 
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
 */
package com.invirgance.convirgance.input;


import com.invirgance.convirgance.json.JSONArray;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ClasspathSource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for YAMLInput class.
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
    
    /**
     * Test read method on nested YAML record
     */
    @Test
    public void testNested()
    {
        
        String expected = "[{\"users\":[{\"name\":\"Alice\",\"contact\":{\"email\":\"alice@example.com\",\"phone\":\"+111111111\"}},{\"name\":\"Bob\",\"contact\":{\"email\":\"bob@example.com\",\"phone\":\"+222222222\"}}]}]";
        JSONArray<JSONObject> output = new JSONArray();
        YAMLInput input = new YAMLInput();
        ClasspathSource source = new ClasspathSource("/nested.yaml");
        
        for (JSONObject record : input.read(source))
        {
            output.add(record);
        }
        
        assertEquals(expected, output.toString());
    }
    
    @Test
    public void testBlankRecord()
    {
        YAMLInput input = new YAMLInput();
        ClasspathSource source = new ClasspathSource("/blank.yaml");
        int count = 0;
        
        for(JSONObject record : input.read(source))
        {
            assertEquals(new JSONObject(), record);
            
            System.out.println(record);
            
            count++;
        }
        
        assertEquals(1, count);
    }
}
