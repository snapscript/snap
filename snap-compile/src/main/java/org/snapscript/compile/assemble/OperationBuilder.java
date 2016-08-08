package org.snapscript.compile.assemble;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.ContextModule;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.parse.Line;

public class OperationBuilder {
   
   private final OperationProcessor processor;
   private final Module module;

   public OperationBuilder(Context context) {
      this.module = new ContextModule(context, DEFAULT_PACKAGE, DEFAULT_PACKAGE, 0);
      this.processor = new OperationProcessor(context);
   }
   
   public Object create(Type type, Object[] arguments, Line line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Callable<Result> callable = binder.bind(scope, type, TYPE_CONSTRUCTOR, arguments);
      
      if(callable == null) {
         throw new InternalStateException("No constructor for " + type + " at line " + line);
      }
      Result result = callable.call();
      Object value = result.getValue();
      
      return processor.process(value, line);
   }


}
