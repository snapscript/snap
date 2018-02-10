package org.snapscript.tree.define;

import org.snapscript.core.Result;
import org.snapscript.core.Type;

public class StaticConstantFactory extends StaticFactory {
   
   private final StaticConstantCollector collector;
   
   public StaticConstantFactory() {
      this.collector = new StaticConstantCollector();
   }

   @Override
   protected void compile(Type type) throws Exception { 
      collector.collect(type);
   }
}