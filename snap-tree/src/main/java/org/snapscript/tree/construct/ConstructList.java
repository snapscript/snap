package org.snapscript.tree.construct;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceType;
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
   public Evaluation compile(Module module, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = TraceType.getConstruct(module, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }
   
   private static class CompileResult implements Evaluation {
      
      private final ArgumentList arguments;
      
      public CompileResult(ArgumentList arguments) {
         this.arguments = arguments;
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception { // this is rubbish
         List result = new ArrayList();
         
         if(arguments != null) {
            Value reference = arguments.evaluate(scope, left);
            Module module = scope.getModule();
            Context context = module.getContext();
            ProxyWrapper wrapper = context.getWrapper();
            Object[] array = reference.getValue();
            
            for(Object value : array) {
               Object proxy = wrapper.toProxy(value);
               result.add(proxy);
            }         
         }   
         return ValueType.getTransient(result);
      }
   }
}