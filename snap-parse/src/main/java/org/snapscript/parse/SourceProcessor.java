/*
 * SourceProcessor.java December 2016
 *
 * Copyright (C) 2016, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.snapscript.parse;

import java.util.Map;

import org.snapscript.common.LeastRecentlyUsedMap;

public class SourceProcessor {
   
   private final Map<String, SourceCode> cache;
   private final int limit;
   
   public SourceProcessor(int limit) {
      this.cache = new LeastRecentlyUsedMap<String, SourceCode>();
      this.limit = limit;
   }
   
   public SourceCode process(String source) {
      char[] text = source.toCharArray();
      
      if(text.length == 0) {
         throw new SourceException("Source text is empty");
      }
      SourceCompressor compressor = new SourceCompressor(text);
      
      if(text.length < limit) {
         SourceCode code = cache.get(source);
         
         if(code == null) {
            code = compressor.compress();
            cache.put(source, code); // cache small sources
         }
         return code;
      }
      return compressor.compress();
   }
}
