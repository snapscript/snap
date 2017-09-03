package org.snapscript.tree.construct;

import java.util.LinkedHashMap;
import java.util.Map;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Value;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceType;
import org.snapscript.parse.StringToken;

public class ConstructMap implements Compilation {
   
   private final Evaluation construct;
   
   public ConstructMap(StringToken token) {
      this(null, token);
   }
   
   public ConstructMap(MapEntryList list) {
      this(list, null);
   }
   
   public ConstructMap(MapEntryList list, StringToken token) {
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

      private final MapEntryList list;
      
      public CompileResult(MapEntryList list) {
         this.list = list;
      }   
      
      @Override
      public Value compile(Scope scope, Object context) throws Exception { // this is rubbish
         if(list != null) {
            return list.compile(scope, context);
         }
         return Value.getTransient(null);
      }
      
      @Override
      public Value evaluate(Scope scope, Object context) throws Exception { // this is rubbish
         Map map = new LinkedHashMap();
         
         if(list != null) {
            return list.evaluate(scope, context);
         }
         return Value.getTransient(map);
      }
   }
}