package org.snapscript.core.function.index;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeCache;
import org.snapscript.core.type.TypeExtractor;

public class FunctionIndexer {
   
   private final TypeCache<TypeIndex> indexes;
   private final FunctionPointerConverter converter;
   private final FunctionIndexBuilder builder;
   private final FunctionPathFinder finder;
   private final TypeInspector inspector;
   
   public FunctionIndexer(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new FunctionIndexBuilder(extractor, stack);
      this.converter = new FunctionPointerConverter(stack);
      this.indexes = new TypeCache<TypeIndex>();
      this.finder = new FunctionPathFinder();
      this.inspector = new TypeInspector();
   }
   
   public FunctionPointer index(Type type, String name, Type... types) throws Exception { 
      TypeIndex match = indexes.fetch(type);

      if(match == null) {
         TypeIndex structure = build(type, name);
         
         indexes.cache(type, structure);
         return structure.get(name, types);
      }
      return match.get(name, types);
   }

   public FunctionPointer index(Type type, String name, Object... values) throws Exception { 
      TypeIndex match = indexes.fetch(type);

      if(match == null) {
         TypeIndex structure = build(type, name);
         
         indexes.cache(type, structure);
         return structure.get(name, values);
      }
      return match.get(name, values);
   }
   
   private TypeIndex build(Type type, String name) throws Exception { 
      List<Type> path = finder.findPath(type); 
      FunctionIndex implemented = builder.create(type);
      FunctionIndex abstracts = builder.create(type);
      int size = path.size();

      for(int i = size - 1; i >= 0; i--) {
         Type entry = path.get(i);
         List<Function> functions = entry.getFunctions();

         for(Function function : functions){
            int modifiers = function.getModifiers();
            
            if(!inspector.isSuperConstructor(type, function)) {
               FunctionPointer call = converter.convert(function);
               
               if(!ModifierType.isAbstract(modifiers)) {  
                  implemented.index(call);
               } else {
                  abstracts.index(call);
               }
            }
         }
      }
      return new TypeIndex(implemented, abstracts);
   }
   
   private static class TypeIndex {
      
      private final FunctionIndex implemented;
      private final FunctionIndex abstracts;
      
      public TypeIndex(FunctionIndex implemented, FunctionIndex abstracts) {
         this.implemented = implemented;
         this.abstracts = abstracts;
      }
      
      public FunctionPointer get(String name, Type... types) throws Exception {
         FunctionPointer call = implemented.resolve(name, types);
         
         if(call == null) {
            return abstracts.resolve(name, types);
         }
         return call;
      }
      
      public FunctionPointer get(String name, Object... values) throws Exception {
         return implemented.resolve(name, values);
      }
   }
}