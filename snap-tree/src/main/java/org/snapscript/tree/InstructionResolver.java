package org.snapscript.tree;

import org.snapscript.common.Cache;
import org.snapscript.common.CopyOnWriteCache;
import org.snapscript.core.Context;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class InstructionResolver implements OperationResolver {

   private final Cache<String, Operation> registry;
   private final Context context;

   public InstructionResolver(Context context) {
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
      String name = instruction.getName();
      
      return new Operation(type, name);
   }
}
