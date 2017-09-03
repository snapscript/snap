package org.snapscript.tree.construct;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalArgumentException;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceEvaluation;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceType;
import org.snapscript.tree.Argument;
import org.snapscript.tree.collection.ArrayBuilder;

public class ConstructArray implements Compilation {
   
   private final Evaluation construct;
   
   public ConstructArray(Evaluation type, Argument... arguments) {
      this.construct = new CompileResult(type, arguments);
   }  
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getConstruct(module, path, line);
      
      return new TraceEvaluation(interceptor, construct, trace);
   }
   
   private static class CompileResult extends Evaluation {
   
      private final ArrayBuilder builder;
      private final Argument[] arguments;
      private final Evaluation reference;
   
      public CompileResult(Evaluation reference, Argument... arguments) {
         this.builder = new ArrayBuilder();
         this.reference = reference;
         this.arguments = arguments;
      }      

      @Override
      public Value compile(Scope scope, Object left) throws Exception { // this is rubbish
         reference.compile(scope, null);
         
         for(int i = 0; i < arguments.length; i++) {
            arguments[i].compile(scope, null);
         }
         return Value.getTransient(null);
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception { // this is rubbish
         Value value = reference.evaluate(scope, null);
         Type type = value.getValue();
         Class entry = type.getType();
         
         if(arguments.length > 0) {
            int[] dimensions = new int[] {0,0,0};
            
            for(int i = 0; i < arguments.length; i++){
               Argument argument = arguments[i];
               Value index = argument.evaluate(scope, left);
               Integer number = index.getInteger();
            
               dimensions[i] = number;
            }
            if(arguments.length == 1) {
               int size = dimensions[0];   
               Object array = builder.create(entry, size);
               
               return Value.getTransient(array);
            }
            if(arguments.length == 2) {
               int first = dimensions[0]; 
               int second = dimensions[1];
               Object array = builder.create(entry, first, second);
               
               return Value.getTransient(array);
            }
            if(arguments.length == 3) {
               int first = dimensions[0]; 
               int second = dimensions[1];
               int third = dimensions[2];
               Object array = builder.create(entry, first, second, third);
               
               return Value.getTransient(array);
            }
            throw new InternalArgumentException("Maximum of three dimensions exceeded");
         }
         Object array = builder.create(entry, 0);
         return Value.getTransient(array);
      }
   }
}