package org.snapscript.tree;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Context;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;

public class OperationResolver {
   
   private final Cache<String, Operation> registry;
   private final Context context;

   public OperationResolver(Context context) {
      this.registry = new CopyOnWriteCache<String, Operation>();
      this.context = context;
   }

   public Operation resolve(String name) throws Exception {
      Operation current = registry.fetch(name);
      
      if(current == null) {
         Instruction[] list = Instruction.values();       
         int size = registry.size();
         
         if(size < list.length) { // have they all been done?
            for(Instruction instruction :list){
               Operation operation = create(instruction);
               String grammar = instruction.getName();
               
               registry.cache(grammar, operation);
            }  
         } 
         return registry.fetch(name);
      }
      return current;
   }
   
   private Operation create(Instruction instruction) throws Exception{
      TypeLoader loader = context.getLoader();
      Class value = instruction.getType();
      Type type = loader.loadType(value);
      
      return new Operation(instruction, type);
   }
}