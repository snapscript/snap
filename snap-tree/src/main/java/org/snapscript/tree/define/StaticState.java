package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;

public class StaticState extends StaticBlock {
   
   private final StaticConstantCollector collector;
   
   public StaticState() {
      this.collector = new StaticConstantCollector();
   }

   @Override
   protected void compile(Scope scope) throws Exception { 
      Type type = scope.getType();
      collector.collect(type);
   }
}