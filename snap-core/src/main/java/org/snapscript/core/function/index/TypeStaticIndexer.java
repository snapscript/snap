package org.snapscript.core.function.index;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.type.Type;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.TypeCache;
import org.snapscript.core.type.TypeExtractor;

public class TypeStaticIndexer {
   
   private final TypeCache<FunctionIndex> indexes;
   private final FunctionPointerConverter converter;
   private final FunctionIndexBuilder builder;
   private final FunctionPathFinder finder;
   private final TypeInspector inspector;
   
   public TypeStaticIndexer(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new FunctionIndexBuilder(extractor, stack);
      this.indexes = new TypeCache<FunctionIndex>();
      this.converter = new FunctionPointerConverter(stack);
      this.finder = new FunctionPathFinder();
      this.inspector = new TypeInspector();
   }

   public FunctionPointer index(Type type, String name, Type... values) throws Exception { 
      FunctionIndex match = indexes.fetch(type);
      
      if(match == null) {
         List<Type> path = finder.findPath(type); 
         FunctionIndex table = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               int modifiers = function.getModifiers();
               
               if(ModifierType.isStatic(modifiers)) {
                  if(!inspector.isSuperConstructor(type, function)) {
                     FunctionPointer pointer = converter.convert(function);
                     table.index(pointer);
                  }
               }
            }
         }
         indexes.cache(type, table);
         return table.resolve(name, values);
      }
      return match.resolve(name, values);
   }
   
   public FunctionPointer index(Type type, String name, Object... values) throws Exception { 
      FunctionIndex match = indexes.fetch(type);
      
      if(match == null) {
         List<Type> path = finder.findPath(type); 
         FunctionIndex table = builder.create(type);
         int size = path.size();

         for(int i = size - 1; i >= 0; i--) {
            Type entry = path.get(i);
            List<Function> functions = entry.getFunctions();

            for(Function function : functions){
               int modifiers = function.getModifiers();
               
               if(ModifierType.isStatic(modifiers)) {
                  if(!inspector.isSuperConstructor(type, function)) {
                     FunctionPointer pointer = converter.convert(function);
                     table.index(pointer);
                  }
               }
            }
         }
         indexes.cache(type, table);
         return table.resolve(name, values);
      }
      return match.resolve(name, values);
   }
}