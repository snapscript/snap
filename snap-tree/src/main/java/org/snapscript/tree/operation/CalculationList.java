package org.snapscript.tree.operation;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;

public class CalculationList implements Compilation { 
   
   private CalculationPart[] parts; 

   public CalculationList(CalculationPart... parts) {
      this.parts = parts;
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Calculator calculator = new Calculator();
      
      for(CalculationPart part : parts) {
         calculator.update(part);
      }
      return calculator.create();
   }
}