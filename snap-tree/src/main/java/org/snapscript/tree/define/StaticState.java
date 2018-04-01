package org.snapscript.tree.define;

import org.snapscript.core.Type;

public class StaticState extends StaticBlock {
   
   private final StaticConstantCollector collector;
   
   public StaticState() {
      this.collector = new StaticConstantCollector();
   }

   @Override
   protected void compile(Type type) throws Exception { 
      collector.collect(type);
   }
}