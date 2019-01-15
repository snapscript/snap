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

public class ConstructList implements Compilation {
   
   private final Evaluation construct;
   
   public ConstructList(StringToken token) {
      this(null, token);
   }
   
   public ConstructList(ElementData elements) {
      this(elements, null);
   }
   
   public ConstructList(ElementData elements, StringToken token) {
      this.construct = new CompileResult(elements);
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }
   
   private static class CompileResult extends Evaluation {
      
      private final ElementData elements;
      
      public CompileResult(ElementData elements) {
         this.elements = elements;
      }
      
      @Override
      public void define(Scope scope) throws Exception { // this is rubbish
         if(elements != null) {
            elements.define(scope);      
         }   
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         if(elements != null) {
            elements.compile(scope);      
         }   
         return Constraint.LIST;
      }
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception { // this is rubbish
         List result = new ArrayList();
         
         if(elements != null) {
            Value value = elements.evaluate(scope);
            Iterable iterable = value.getValue();
            Module module = scope.getModule();
            Context context = module.getContext();
            ProxyWrapper wrapper = context.getWrapper();
            
            for(Object element : iterable) {
               Object proxy = wrapper.toProxy(element);
               result.add(proxy);
            }         
         }   
         return Value.getTransient(result);
      }
   }
}