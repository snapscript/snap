package org.snapscript.tree.reference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;

public class ReferencePart implements Compilation {
   
   private final Evaluation evaluation;
   
   public ReferencePart(Evaluation evaluation) {
      this.evaluation = evaluation;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      return evaluation;
   }
}