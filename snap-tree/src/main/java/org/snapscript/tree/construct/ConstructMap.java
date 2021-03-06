package org.snapscript.tree.construct;

import java.util.LinkedHashMap;
import java.util.Map;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.variable.Value;
import org.snapscript.parse.StringToken;

public class ConstructMap implements Compilation {
   
   private final Evaluation construct;
   
   public ConstructMap(StringToken token) {
      this(null, token);
   }
   
   public ConstructMap(MapEntryData list) {
      this(list, null);
   }
   
   public ConstructMap(MapEntryData list, StringToken token) {
      this.construct = new CompileResult(list);
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }
   
   private static class CompileResult extends Evaluation {

      private final MapEntryData entries;
      
      public CompileResult(MapEntryData entries) {
         this.entries = entries;
      }   
      
      @Override
      public void define(Scope scope) throws Exception {
         if(entries != null) {
            entries.define(scope);
         }
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         if(entries != null) {
            entries.compile(scope, null);
         }
         return Constraint.MAP;
      }
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception { 
         Map map = new LinkedHashMap();
         
         if(entries != null) {
            return entries.evaluate(scope, left);
         }
         return Value.getTransient(map);
      }
   }
}