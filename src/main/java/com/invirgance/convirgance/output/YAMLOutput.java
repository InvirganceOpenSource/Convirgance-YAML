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

import com.invirgance.convirgance.json.JSONObject;
import com.invirgance.convirgance.output.Output;
import com.invirgance.convirgance.output.OutputCursor;
import com.invirgance.convirgance.target.Target;
import java.io.PrintWriter;
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
        private final PrintWriter out;
        private boolean first = true;
        
        public YAMLOutputCursor(Target target)
        {
            this.out = new PrintWriter(target.getOutputStream(), false);
        }

        @Override
        public void write(JSONObject record)
        {
            if (!first || record.size() < 1) out.println("---");
            
            if (!record.isEmpty()) 
            {
                out.print(yaml.dumpAsMap(record));
            }
            
            first = false;
        } 

        @Override
        public void close()
        {
            out.close();
        }
    }
}
