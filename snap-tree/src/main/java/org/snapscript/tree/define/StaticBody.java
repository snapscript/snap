package org.snapscript.tree.define;

import static org.snapscript.core.result.Result.NORMAL;

import java.util.concurrent.atomic.AtomicBoolean;

import org.snapscript.core.Execution;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.result.Result;
import org.snapscript.core.type.TypeBody;

public class StaticBody extends Execution {
   
   private final AtomicBoolean execute;
   private final TypeBody body;
   private final Type type;
   
   public StaticBody(TypeBody body, Type type) {
      this.execute = new AtomicBoolean(false);
      this.body = body;
      this.type = type;
   }

   @Override
   public Result execute(Scope scope) throws Exception {
      if(execute.compareAndSet(false, true)) {
         Scope outer = type.getScope();
         body.allocate(outer, type);
      }
      return NORMAL;
   } 
}