package org.snapscript.tree.construct;

import java.util.LinkedHashSet;
import java.util.Set;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.parse.StringToken;
import org.snapscript.tree.ArgumentList;

public class ConstructSet implements Compilation {
   
   private final Evaluation construct;
  
   public ConstructSet(StringToken token) {
      this(null, token);
   }
   
   public ConstructSet(ArgumentList arguments) {
      this(arguments, null);
   }
   
   public ConstructSet(ArgumentList arguments, StringToken token) {
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
      public void compile(Scope scope) throws Exception { 
         if(arguments != null) {
            arguments.compile(scope);      
         }   
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception { 
         Set result = new LinkedHashSet();
         
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