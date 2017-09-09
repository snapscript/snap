package org.snapscript.core.bind;

import static org.snapscript.core.convert.Score.INVALID;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ArgumentConverter;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.EmptySignature;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;

public class FunctionResolver {
   
   private final FunctionCacheIndexer<Type> indexer;
   private final FunctionCacheTable<Type> table;
   private final FunctionKeyBuilder builder;
   private final FunctionPathFinder finder;
   private final Signature signature;
   private final Function invalid;
   
   public FunctionResolver(TypeExtractor extractor) {
      this.indexer = new TypeCacheIndexer();
      this.table = new FunctionCacheTable<Type>(indexer);
      this.builder = new FunctionKeyBuilder(extractor);
      this.finder = new FunctionPathFinder();
      this.signature = new EmptySignature();
      this.invalid = new EmptyFunction(signature);
   }
   
   public Function resolve(Type type, String name, Type... types) throws Exception { 
      Object key = builder.create(name, types);
      FunctionCache cache = table.get(type);
      Function function = cache.fetch(key); // all type functions
      
      if(function == null) {
         List<Type> path = finder.findPath(type, name); // should only provide non-abstract methods
         Function match = invalid;
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
                     ArgumentConverter converter = signature.getConverter();
                     Score score = converter.score(types);
      
                     if(score.compareTo(best) > 0) {
                        match = next;
                        best = score;
                     }
                  }
               }
            }
         }
         Signature signature = match.getSignature();
         
         if(signature.isAbsolute()) {
            cache.cache(key, match);
         }  
         function = match;
      }     
      return function == invalid ? null : function;
   }

   public Function resolve(Type type, String name, Object... values) throws Exception { 
      Object key = builder.create(name, values);
      FunctionCache cache = table.get(type);
      Function function = cache.fetch(key); // all type functions
      
      if(function == null) {
         List<Type> path = finder.findPath(type, name); // should only provide non-abstract methods
         Function match = invalid;
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
                     ArgumentConverter converter = signature.getConverter();
                     Score score = converter.score(values);
      
                     if(score.compareTo(best) > 0) {
                        match = next;
                        best = score;
                     }
                  }
               }
            }
         }
         Signature signature = match.getSignature();
         
         if(signature.isAbsolute()) {
            cache.cache(key, match);
         }  
         function = match;
      }      
      return function == invalid ? null : function;
   }
}