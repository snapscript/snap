package org.snapscript.core.function.match;

import java.util.List;

import org.snapscript.core.type.Type;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.convert.proxy.Delegate;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.index.FunctionIndex;
import org.snapscript.core.function.index.FunctionIndexBuilder;
import org.snapscript.core.function.search.FunctionPathFinder;
import org.snapscript.core.function.search.FunctionPointer;
import org.snapscript.core.function.search.FunctionWrapper;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.TypeCache;
import org.snapscript.core.type.TypeExtractor;

public class DelegateFunctionMatcher {
   
   private final TypeCache<FunctionIndex> cache;
   private final FunctionIndexBuilder builder;
   private final FunctionPathFinder finder;
   private final FunctionWrapper wrapper;
   private final TypeExtractor extractor;
   private final TypeInspector checker;
   
   public DelegateFunctionMatcher(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new FunctionIndexBuilder(extractor, stack);
      this.wrapper = new FunctionWrapper(stack);
      this.cache = new TypeCache<FunctionIndex>();
      this.finder = new FunctionPathFinder();
      this.checker = new TypeInspector();
      this.extractor = extractor;
   }
   
   public FunctionPointer match(Type type, String name, Type... values) throws Exception { 
      FunctionIndex match = cache.fetch(type);
      
      if(match == null) {
         List<Type> path = finder.findPath(type, name); 
         FunctionIndex table = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            
            if(!checker.isProxy(entry)) {
               List<Function> functions = entry.getFunctions();
   
               for(Function function : functions){
                  if(!checker.isSuperConstructor(type, function)) {
                     FunctionPointer call = wrapper.toCall(function);
                     table.index(call);
                  }
               }
            }
         }
         cache.cache(type, table);
         return table.resolve(name, values);
      }
      return match.resolve(name, values);
   }
   
   public FunctionPointer match(Delegate value, String name, Object... values) throws Exception { 
      Type type = extractor.getType(value);
      FunctionIndex match = cache.fetch(type);
      
      if(match == null) {
         List<Type> path = finder.findPath(type, name); 
         FunctionIndex table = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            
            if(!checker.isProxy(entry)) {
               List<Function> functions = entry.getFunctions();
   
               for(Function function : functions){
                  if(!checker.isSuperConstructor(type, function)) {
                     FunctionPointer call = wrapper.toCall(function);
                     table.index(call);
                  }
               }
            }
         }
         cache.cache(type, table);
         return table.resolve(name, values);
      }
      return match.resolve(name, values);
   }
}