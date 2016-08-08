package org.snapscript.tree.define;

import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Type;

public class StaticConstantInitializer extends StaticInitializer {
   
   private final StaticConstantCollector collector;
   
   public StaticConstantInitializer() {
      this.collector = new StaticConstantCollector();
   }

   @Override
   protected Result compile(Type type) throws Exception { 
      collector.collect(type);
      return ResultType.getNormal();
   }
}
