/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.invirgance.convirgance.yaml;

import com.invirgance.convirgance.input.InputCursor;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.ClasspathSource;
import com.invirgance.convirgance.source.Source;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
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
        YAMLInput input = new YAMLInput();
        ClasspathSource source = new ClasspathSource("/data.yaml");
        
        for (JSONObject record : input.read(source))
        {
            System.out.println(record);
        }
        
    }
    
    // clean up github: target and .DS_Store with gitignore
    
    // empty yaml file
    
    // only one object in file
    
    // multiple files
    

    
}
