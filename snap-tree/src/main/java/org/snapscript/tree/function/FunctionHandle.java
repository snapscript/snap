package org.snapscript.tree.function;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.bind.FunctionBinder;
import org.snapscript.core.function.bind.FunctionMatcher;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.NameReference;

public class FunctionHandle implements Compilation {   
   
   private final NameReference reference;
   
   public FunctionHandle(Evaluation identifier) {
      this.reference = new NameReference(identifier);
   }
   
   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      String name = reference.getName(scope);  
      FunctionBinder binder = context.getBinder(); 
      FunctionMatcher matcher = binder.bind(name); 
      
      if(name.equals(TYPE_CONSTRUCTOR)) {
         return new CompileResult(matcher, name, true);
      }
      return new CompileResult(matcher, name);
   }

   private static class CompileResult extends Evaluation {
      
      private final FunctionHandleBuilder builder;
      private final String name;

      public CompileResult(FunctionMatcher matcher, String name) {
         this(matcher, name, false);         
      }
      
      public CompileResult(FunctionMatcher matcher, String name, boolean constructor) {
         this.builder = new FunctionHandleBuilder(matcher, constructor);
         this.name = name;
      }
   
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         Module module = scope.getModule(); // is this the correct module?
         Function function = builder.create(module, left, name); 
         
         return Value.getTransient(function);
      }
   }
}