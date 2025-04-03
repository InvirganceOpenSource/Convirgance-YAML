/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.invirgance.convirgance.yaml;

import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.output.Output;
import com.invirgance.convirgance.output.OutputCursor;
import com.invirgance.convirgance.target.Target;
import java.io.PrintWriter;
import java.util.Iterator;
import org.yaml.snakeyaml.Yaml;

/**
 * Provides support for writing a stream of data as YAML formatted objects
 * @author timur
 */
public class YAMLOutput implements Output
{
    /**
     * Creates a new YAMLOutput instance. 
     */
    public YAMLOutput()
    {
    }
    
    @Override
    public String getContentType()
    {
        return "application/yaml";
    }
    
    @Override 
    public OutputCursor write(Target target)
    {
        return new YAMLOutputCursor(target);
    }
    
    private class YAMLOutputCursor implements OutputCursor
    {
        private final Yaml yaml = new Yaml();
        final PrintWriter out;
        
        public YAMLOutputCursor(Target target)
        {
            this.out = new PrintWriter(target.getOutputStream(), false);
        }

        @Override
        public void write(JSONObject record)
        {
            if (!record.isEmpty()) 
            {
                out.println("---");
                out.print(yaml.dumpAsMap(record));
            }
        } 
        
        @Override
        public void write(Iterator<JSONObject> iterator)
        {
            while (iterator.hasNext())
            {
                out.println("---");
                out.print(yaml.dumpAsMap(iterator.next()));
            }
        }

        @Override
        public void close()
        {
            if (out != null) out.close();
        }      
    }
}
