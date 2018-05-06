package org.snapscript.tree;

import java.util.Map;

import org.snapscript.core.Context;

public class InstructionResolver implements OperationResolver {
   
   private static final Instruction[] INSTRUCTIONS = Instruction.values();  
   
   private volatile InstructionBuilder builder;
   private volatile Map<String, Operation> table;

   public InstructionResolver(Context context) {
      this.builder = new InstructionBuilder(context);
   }

   public Operation resolve(String name) throws Exception {
      if(table == null) {            
         table = builder.create(INSTRUCTIONS);
      }      
      return table.get(name);
   }
}
