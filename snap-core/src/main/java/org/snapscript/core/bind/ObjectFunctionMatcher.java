/*
 * ObjectFunctionMatcher.java December 2016
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

package org.snapscript.core.bind;

import static org.snapscript.core.convert.Score.INVALID;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.stack.ThreadStack;

public class ObjectFunctionMatcher {
   
   private final FunctionCacheIndexer<Type> indexer;
   private final FunctionCacheTable<Type> table;
   private final FunctionKeyBuilder builder;
   private final FunctionPathFinder finder;
   private final TypeExtractor extractor;
   private final ThreadStack stack;
   private final Function invalid;
   
   public ObjectFunctionMatcher(TypeExtractor extractor, ThreadStack stack) {
      this.indexer = new TypeCacheIndexer();
      this.table = new FunctionCacheTable<Type>(indexer);
      this.builder = new FunctionKeyBuilder(extractor);
      this.finder = new FunctionPathFinder();
      this.invalid = new EmptyFunction(null);
      this.extractor = extractor;
      this.stack = stack;
   }

   public FunctionPointer match(Object value, String name, Object... values) throws Exception { 
      Type type = extractor.getType(value);
      Object key = builder.create(name, values);
      FunctionCache cache = table.get(type);
      Function function = cache.fetch(key); // all type functions
      
      if(function == null) {
         List<Type> path = finder.findPath(type, name); // should only provide non-abstract methods
         Score best = INVALID;
         
         for(Type entry : path) {
            List<Function> functions = entry.getFunctions();
            int size = functions.size();
            
            for(int i = size - 1; i >= 0; i--) {
               Function next = functions.get(i);
               int modifiers = next.getModifiers();
               
               if(!ModifierType.isAbstract(modifiers)) {
                  String method = next.getName();
                  
                  if(name.equals(method)) {
                     Signature signature = next.getSignature();
                     ArgumentConverter match = signature.getConverter();
                     Score score = match.score(values);
      
                     if(score.compareTo(best) > 0) {
                        function = next;
                        best = score;
                     }
                  }
               }
            }
         }
         if(best.isFinal()) {
            if(function == null) {
               function = invalid;
            }
            cache.cache(key, function);
         }
      }      
      if(function != invalid) {
         return new FunctionPointer(function, stack, values);
      }
      return null;
   }
}
