package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.Value;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.Table;
import org.snapscript.tree.NameReference;

public class Variable implements Compilation {
   
   private final NameReference reference;
   
   public Variable(Evaluation identifier) {
      this.reference = new NameReference(identifier);
   }
   
   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      ProxyWrapper wrapper = context.getWrapper();
      String name = reference.getName(scope);
      
      return new CompileResult(handler, wrapper, name);
   }
   
   private static class CompileResult extends Evaluation {
   
      private final VariableBinder binder;
      private final AtomicInteger offset;
      private final String name;
      
      public CompileResult(ErrorHandler handler, ProxyWrapper wrapper, String name) {
         this.binder = new VariableBinder(handler, wrapper, name);
         this.offset = new AtomicInteger(-1);
         this.name = name;
      }
   
      @Override
      public void define(Scope scope) throws Exception{
         Index index = scope.getIndex();
         int depth = index.get(name);
   
         offset.set(depth);
      }
      
      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception{
         if(left == null) {
            int depth = offset.get();
            
            if(depth == -1){
               State state = scope.getState();
               Value value = state.get(name);
               
               if(value != null) { 
                  return value;
               }
            }else {
               Table table = scope.getTable();
               Value value = table.get(depth);
   
               if(value != null) { 
                  return value;
               }
            }
            return binder.check(scope);
         }
         return binder.check(scope, left);
      } 
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception{
         if(left == null) {
            int depth = offset.get();
            
            if(depth == -1){
               State state = scope.getState();
               Value value = state.get(name);
               
               if(value != null) { 
                  return value;
               }
            }else {
               Table table = scope.getTable();
               Value value = table.get(depth);
   
               if(value != null) { 
                  return value;
               }
            }
            return binder.bind(scope);
         }
         return binder.bind(scope, left);
      } 
   }
}