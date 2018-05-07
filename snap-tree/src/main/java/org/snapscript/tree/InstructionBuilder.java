package org.snapscript.tree;

import java.util.HashMap;
import java.util.Map;

import org.snapscript.core.Context;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class InstructionBuilder {
   
   private final Instruction[] instructions;
   private final Context context;
   
   public InstructionBuilder(Context context) {
      this.instructions = Instruction.values();
      this.context = context;
   }

   public Map<String, Operation> create() throws Exception{
      Map<String, Operation> table = new HashMap<String, Operation>();   
      
      for(Instruction instruction : instructions){
         Operation operation = create(instruction);
         String grammar = instruction.getName();
         
         table.put(grammar, operation);
      } 
      return table;
   }
   
   private Operation create(Instruction instruction) throws Exception{
      TypeLoader loader = context.getLoader();
      Class value = instruction.getType();
      Type type = loader.loadType(value);
      String name = instruction.getName();
      
      return new Operation(type, name);
   }
}