package org.snapscript.tree;

import java.util.Map;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;

public class InstructionResolver implements OperationResolver {   
   
   private volatile Map<String, Operation> operations;
   private volatile InstructionBuilder builder;

   public InstructionResolver(Context context) {
      this.builder = new InstructionBuilder(context);
   }

   public Operation resolve(String name) throws Exception {
      if(operations == null) {            
         Instruction[] instructions = Instruction.values();
         
         if(instructions.length < 1) {
            throw new InternalStateException("No instructions found");
         }
         operations = builder.create(instructions);         
      }      
      return operations.get(name);
   }
}
