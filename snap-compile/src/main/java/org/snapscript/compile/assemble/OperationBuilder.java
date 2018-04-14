package org.snapscript.compile.assemble;

import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;
import static org.snapscript.core.Reserved.DEFAULT_RESOURCE;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.concurrent.Executor;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.module.ContextModule;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.function.resolve.FunctionResolver;
import org.snapscript.parse.Line;

public class OperationBuilder {
   
   private final OperationProcessor processor;
   private final Module module;
   private final Path path;

   public OperationBuilder(Context context, Executor executor) {
      this.path = new Path(DEFAULT_RESOURCE);
      this.module = new ContextModule(context, executor, path, DEFAULT_PACKAGE, "", 0);
      this.processor = new OperationProcessor(context);
   }
   
   public Object create(Type type, Object[] arguments, Line line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      FunctionResolver resolver = context.getResolver();
      FunctionCall call = resolver.resolveStatic(scope, type, TYPE_CONSTRUCTOR, arguments);
      
      if(call == null) {
         throw new InternalStateException("No constructor for '" + type + "' at line " + line);
      }
      Value value = call.call();
      Object result = value.getValue();
      
      return processor.process(result, line);
   }


}