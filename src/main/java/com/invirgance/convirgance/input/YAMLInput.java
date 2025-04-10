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
package com.invirgance.convirgance.input;

import com.invirgance.convirgance.CloseableIterator;
import com.invirgance.convirgance.ConvirganceException;
import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.source.Source;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 * Provides support for taking in a YAML formatted stream of data into JSONObjects
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
               
        public YAMLInputCursor(Source source)
        {
            this.source = source;
        }
        
        @Override
        public CloseableIterator<JSONObject> iterator()
        {
            Yaml yaml = new Yaml();
            InputStream in = source.getInputStream();
            Iterator iterator = yaml.loadAll(in).iterator();
            
            return new CloseableIterator<JSONObject>() {
                private boolean closed = false;
               
                @Override
                public boolean hasNext()
                {
                    if (!iterator.hasNext()) close();
                    
                    return iterator.hasNext();
                }

                @Override
                public JSONObject next()
                {
                    JSONObject object = new JSONObject(true); // ordered JSONObject
                    Map next = (Map)iterator.next();
                    
                    if(next != null) object.putAll(next);
                    
                    return object;  
                }

                @Override
                public void close()
                {
                    if(closed) return;

                    try
                    {
                        in.close();
                    }
                    catch(IOException e) { throw new ConvirganceException(e); }
                    
                    closed = true;
               } 
           };
        }              
    }
}
