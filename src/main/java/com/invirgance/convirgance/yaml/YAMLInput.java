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
package com.invirgance.convirgance.yaml;

import com.invirgance.convirgance.CloseableIterator;
import com.invirgance.convirgance.ConvirganceException;
import com.invirgance.convirgance.input.CSVInput;
import com.invirgance.convirgance.input.Input;
import com.invirgance.convirgance.input.InputCursor;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.FileSource;
import com.invirgance.convirgance.source.Source;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author timur
 */
public class YAMLInput implements Input<JSONObject>
{
    public YAMLInput()
    {
    }
    
    @Override
    public InputCursor<JSONObject> read(Source source)
    {
        return new YAMLInput.YAMLInputCursor(source);
    }
    
    private class YAMLInputCursor implements InputCursor<JSONObject>
    {
        private final Source source; 
        private Iterable<Object> records;
        private Iterator<Object> iterator;
               
        public YAMLInputCursor(Source source)
        {
            this.source = source;
        }
        
        @Override
        public CloseableIterator<JSONObject> iterator()
        {
           return new CloseableIterator<JSONObject>() { 
               // set up yaml parser      
               Yaml yaml = new Yaml();
               {
                    String path = ((FileSource) source).getFile().getAbsolutePath();
                    System.out.println(path);
                    try (InputStream input = new FileInputStream(path)) 
                    {
                        // get an iterable access to records
                        records = yaml.loadAll(input); 
                        iterator = records.iterator();  
                    } catch (Exception e) 
                    {
                        e.printStackTrace();
                    }
               }
               
               @Override
                public boolean hasNext()
               {
                  return iterator.hasNext();
               }
                
               @Override
               public JSONObject next()
               {
                   try
                   {
//                       System.out.println(iterator.next());
                       return new JSONObject((Map) iterator.next());
                   }
                   catch (Exception e)
                   {
                        throw new ConvirganceException("Error reading YAML record", e);
                   }
               }
               
               @Override
               public void close() throws Exception
               {
                // how to close? Cast to Closeable iterator?
               } 
           };
        }              
    }
}
