package org.snapscript.core.bind;

import java.util.List;

import org.snapscript.core.ModifierType;
import org.snapscript.core.Type;
import org.snapscript.core.TypeCache; 
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.convert.TypeInspector;
import org.snapscript.core.function.Function;
import org.snapscript.core.stack.ThreadStack;

public class FunctionResolver {
   
   private final TypeCache<TypeStructure> cache;
   private final FunctionTableBuilder builder;
   private final FunctionPathFinder finder;
   private final FunctionWrapper wrapper;
   private final TypeInspector inspector;
   
   public FunctionResolver(TypeExtractor extractor, ThreadStack stack) {
      this.builder = new FunctionTableBuilder(extractor, stack);
      this.cache = new TypeCache<TypeStructure>();
      this.wrapper = new FunctionWrapper(stack);
      this.finder = new FunctionPathFinder();
      this.inspector = new TypeInspector();
   }
   
   public FunctionCall resolve(Type type, String name, Type... types) throws Exception { 
      TypeStructure match = cache.fetch(type);

      if(match == null) {
         TypeStructure structure = build(type, name);
         
         cache.cache(type, structure);
         return structure.get(name, types);
      }
      return match.get(name, types);
   }

   public FunctionCall resolve(Type type, String name, Object... values) throws Exception { 
      TypeStructure match = cache.fetch(type);

      if(match == null) {
         TypeStructure structure = build(type, name);
         
         cache.cache(type, structure);
         return structure.get(name, values);
      }
      return match.get(name, values);
   }
   
   private TypeStructure build(Type type, String name) throws Exception { 
      List<Type> path = finder.findPath(type, name); 
      FunctionTable implemented = builder.create(type);
      FunctionTable abstracts = builder.create(type);
      int size = path.size();

      for(int i = size - 1; i >= 0; i--) {
         Type entry = path.get(i);
         List<Function> functions = entry.getFunctions();

         for(Function function : functions){
            int modifiers = function.getModifiers();
            
            if(!inspector.isSuperConstructor(type, function)) {
               FunctionCall call = wrapper.toCall(function);
               
               if(!ModifierType.isAbstract(modifiers)) {  
                  implemented.update(call);
               } else {
                  abstracts.update(call);
               }
            }
         }
      }
      return new TypeStructure(implemented, abstracts);
   }
   
   private static class TypeStructure {
      
      private final FunctionTable implemented;
      private final FunctionTable abstracts;
      
      public TypeStructure(FunctionTable implemented, FunctionTable abstracts) {
         this.implemented = implemented;
         this.abstracts = abstracts;
      }
      
      public FunctionCall get(String name, Type... types) throws Exception {
         FunctionCall call = implemented.resolve(name, types);
         
         if(call == null) {
            return abstracts.resolve(name, types);
         }
         return call;
      }
      
      public FunctionCall get(String name, Object... values) throws Exception {
         return implemented.resolve(name, values);
      }
   }
}