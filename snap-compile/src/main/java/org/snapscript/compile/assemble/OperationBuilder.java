package org.snapscript.compile.assemble;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.core.Reserved.DEFAULT_RESOURCE;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.ContextModule;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.parse.Line;

public class OperationBuilder {
   
   private final OperationProcessor processor;
   private final Module module;
   private final Path path;

   public OperationBuilder(Context context, Executor executor) {
      this.path = new Path(DEFAULT_RESOURCE);
      this.module = new ContextModule(context, executor, path, DEFAULT_PACKAGE, 0);
      this.processor = new OperationProcessor(context);
   }
   
   public Object create(Type type, Object[] arguments, Line line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      FunctionBinder binder = context.getBinder();
      Callable<Value> callable = binder.bind(scope, type, TYPE_CONSTRUCTOR, arguments);
      
      if(callable == null) {
         throw new InternalStateException("No constructor for '" + type + "' at line " + line);
      }
      Value value = callable.call();
      Object result = value.getValue();
      
      return processor.process(result, line);
   }


}