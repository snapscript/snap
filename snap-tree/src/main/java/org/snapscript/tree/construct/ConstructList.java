package org.snapscript.tree.construct;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.ArgumentList;

public class ConstructList implements Compilation {
   
   private final Evaluation construct;
   
   public ConstructList(StringToken token) {
      this(null, token);
   }
   
   public ConstructList(ArgumentList arguments) {
      this(arguments, null);
   }
   
   public ConstructList(ArgumentList arguments, StringToken token) {
      this.construct = new CompileResult(arguments);
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }
   
   private static class CompileResult extends Evaluation {
      
      private final ArgumentList arguments;
      
      public CompileResult(ArgumentList arguments) {
         this.arguments = arguments;
      }
      
      @Override
      public void define(Scope scope) throws Exception { // this is rubbish
         if(arguments != null) {
            arguments.define(scope);      
         }   
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         if(arguments != null) {
            arguments.compile(scope);      
         }   
         return Constraint.LIST;
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception { // this is rubbish
         List result = new ArrayList();
         
         if(arguments != null) {
            Object[] array = arguments.create(scope);
            Module module = scope.getModule();
            Context context = module.getContext();
            ProxyWrapper wrapper = context.getWrapper();
            
            for(Object value : array) {
               Object proxy = wrapper.toProxy(value);
               result.add(proxy);
            }         
         }   
         return Value.getTransient(result);
      }
   }
}