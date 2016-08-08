package org.snapscript.tree;

import org.snapscript.core.Type;

public class Operation {
   
   private final Instruction instruction;
   private final Type type;
   
   public Operation(Instruction instruction, Type type) {
      this.instruction = instruction;
      this.type = type;
   }
   
   public Instruction getInstruction() {
      return instruction;
   }
   
   public Type getType() {
      return type;
   }
}
