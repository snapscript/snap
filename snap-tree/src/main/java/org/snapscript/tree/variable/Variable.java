package org.snapscript.tree.variable;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Compilation;
import org.snapscript.core.Constraint;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Index;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Table;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;
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
      ProxyWrapper wrapper = context.getWrapper();
      String name = reference.getName(scope);
      
      return new CompileResult(wrapper, name);
   }
   
   private static class CompileResult extends Evaluation {
   
      private final VariableBinder binder;
      private final AtomicInteger offset;
      private final String name;
      
      public CompileResult(ProxyWrapper wrapper, String name) {
         this.binder = new VariableBinder(wrapper, name);
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
                  Type t= value.getConstraint();
                  return Constraint.getInstance(t);
               }
            }else {
               Table table = scope.getTable();
               Value value = table.get(depth);
   
               if(value != null) { 
                  Type t= value.getConstraint();
                  return Constraint.getInstance(t);
               }
            }
            return binder.check(scope);
         }
         Type t = left.getType(scope);
         if(t!=null) {
            return binder.check(scope, left);
         }
         return Constraint.getNone();
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