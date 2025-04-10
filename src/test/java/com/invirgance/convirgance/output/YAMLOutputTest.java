/*
 * The MIT License
 *
 * Copyright 2025 Invirgance LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.invirgance.convirgance.output;

import com.invirgance.convirgance.json.JSONArray;
import com.invirgance.convirgance.json.JSONObject;
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

        String expected = "name: John Doe\n" +
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
        
        assertEquals(expected, new String(target.getBytes(), "UTF-8")); 
    }
    
    
    /**
     * Test for calling write() multiple times.
     */
    @Test
    void testCursorWriteMultipleTimes() throws Exception
    {
        JSONArray<JSONObject> records = new JSONArray("[{\"name\":\"John Doe\",\"age\":30,\"email\":\"johndoe@example.com\"}," +
                                          "{\"name\":\"Jane Smith\",\"age\":25,\"email\":\"janesmith@example.com\"}," +
                                          "{\"name\":\"Alice Johnson\",\"age\":40,\"email\":\"alice@example.com\"}]");

        String expected = "name: John Doe\n" +
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
            for (JSONObject record : records)
            {
                cursor.write(record);  
            }   
        }
        
        assertEquals(expected, new String(target.getBytes(), "UTF-8")); 
    }
    
    /**
     * Test for writing a non-empty object followed by two empty ones.
     */
    @Test
    void testCursorNonEmptyThenEmpty() throws Exception
    {
        JSONObject empty = new JSONObject();
        JSONObject nonempty = new JSONObject("{\"name\":\"John Doe\",\"age\":30,\"email\":\"johndoe@example.com\"}");
        String expected = "name: John Doe\n" +
                          "age: 30\n" +
                          "email: johndoe@example.com\n" +
                          "---\n" +
                          "---\n";

        try (OutputCursor cursor = output.write(target))
        {
            cursor.write(nonempty);
            cursor.write(empty);
            cursor.write(empty);
        }
        
        assertEquals(expected, new String(target.getBytes(), "UTF-8")); 
    }
    
    
    
    /**
     * Test for writing a nested YAML object.
     */
    @Test
    void testNestedRecord() throws Exception
    {
        JSONObject record = new JSONObject("{\"users\":[{\"name\":\"Alice\",\"contact\":{\"email\":\"alice@example.com\",\"phone\":\"+111111111\"}},{\"name\":\"Bob\",\"contact\":{\"email\":\"bob@example.com\",\"phone\":\"+222222222\"}}]}");
        
        System.out.println(record);
        String expected = "users:\n" +
                          "- name: Alice\n" +
                          "  contact:\n" +
                          "    email: alice@example.com\n" +
                          "    phone: \'+111111111\'\n" +
                          "- name: Bob\n" +
                          "  contact:\n" +
                          "    email: bob@example.com\n" +
                          "    phone: \'+222222222\'\n";
        
        try (OutputCursor cursor = output.write(target))
        {
            cursor.write(record);
        }
        
        assertEquals(new String(target.getBytes(), "UTF-8"), expected); 
    }
    
    @Test
    public void testBlankRecord() throws Exception
    {
        JSONArray array = new JSONArray();
        YAMLOutput output = new YAMLOutput();
        ByteArrayTarget target = new ByteArrayTarget();
        
        array.add(new JSONObject());
        output.write(target, array);
        
        assertEquals("---\n", new String(target.getBytes(), "UTF-8"));
    }
}
