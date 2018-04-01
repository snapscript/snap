package org.snapscript.core.function.match;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.type.Type;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.index.FunctionIndex;
import org.snapscript.core.function.index.FunctionIndexBuilder;
import org.snapscript.core.function.search.FunctionPathFinder;
import org.snapscript.core.function.search.FunctionPointer;
import org.snapscript.core.function.search.FunctionWrapper;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.TypeCache;
import org.snapscript.core.type.TypeExtractor;

public class TypeStaticFunctionMatcher {
   
   private final TypeCache<FunctionIndex> cache;
   private final FunctionIndexBuilder builder;
   private final FunctionPathFinder finder;
   private final FunctionWrapper wrapper;
   private final TypeInspector inspector;
   
   public TypeStaticFunctionMatcher(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new FunctionIndexBuilder(extractor, stack);
      this.cache = new TypeCache<FunctionIndex>();
      this.wrapper = new FunctionWrapper(stack);
      this.finder = new FunctionPathFinder();
      this.inspector = new TypeInspector();
   }

   public FunctionPointer match(Type type, String name, Type... values) throws Exception { 
      FunctionIndex match = cache.fetch(type);
      
      if(match == null) {
         List<Type> path = finder.findPath(type, name); 
         FunctionIndex table = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               int modifiers = function.getModifiers();
               
               if(ModifierType.isStatic(modifiers)) {
                  if(!inspector.isSuperConstructor(type, function)) {
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
   
   public FunctionPointer match(Type type, String name, Object... values) throws Exception { 
      FunctionIndex match = cache.fetch(type);
      
      if(match == null) {
         List<Type> path = finder.findPath(type, name); 
         FunctionIndex table = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               int modifiers = function.getModifiers();
               
               if(ModifierType.isStatic(modifiers)) {
                  if(!inspector.isSuperConstructor(type, function)) {
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