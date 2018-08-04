package org.snapscript.tree.reference;

import static org.snapscript.core.constraint.Constraint.OBJECT;
import static org.snapscript.core.variable.Value.NULL;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.snapscript.core.Entity;
import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.TypeParameterConstraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.constraint.TypeConstraint;

public class GenericDeclaration { 

   private final ConstraintCompilation compilation;
   private final GenericArgumentList list;
   private final TypeReference type; 
   private final Set<String> imports;
   
   public GenericDeclaration(TypeReference type, GenericArgumentList list, TraceInterceptor interceptor, Trace trace) {
      this.compilation = new ConstraintCompilation(type, list, interceptor, trace);
      this.imports = new HashSet<String>();
      this.list = list;
      this.type = type;
   }
   
   public Value declare(Scope scope) throws Exception {      
      Module module = scope.getModule();
      Scope outer = module.getScope();
      String name = type.qualify(scope, null);        
      
      if(list != null) {
         List<String> other = list.getImports(scope);
         
         if(other != null) {
            imports.addAll(other);
         }
      }         
      if(name != null) {
         imports.add(name);
      }
      return new ConstraintConstant(compilation, imports, outer);
   }
   
   private static class ConstraintCompilation extends Evaluation {

      private final TraceInterceptor interceptor;
      private final GenericArgumentList list;
      private final TypeReference type;
      private final Trace trace;

      public ConstraintCompilation(TypeReference type, GenericArgumentList list, TraceInterceptor interceptor, Trace trace) {
         this.interceptor = interceptor;
         this.trace = trace;
         this.type = type;
         this.list = list;
      }

      @Override
      public Constraint compile(Scope scope, Constraint left) { 
         try {
            Value value = type.evaluate(scope, NULL);
            Entity entity = value.getValue();
            int modifiers = entity.getModifiers();
                  
            if(!ModifierType.isModule(modifiers)) {
               Constraint constraint = value.getConstraint();
               String name = constraint.getName(scope);
               Type type = constraint.getType(scope);
               
               if(list != null) {
                  List<Constraint> generics = list.getConstraints(scope);    
                  
                  if(!generics.isEmpty()) {
                     return new TypeParameterConstraint(type, generics, name);
                  }
               }
               return new TypeParameterConstraint(type, name);
            }
            return value.getConstraint();
         }catch(Exception cause) {
            interceptor.traceCompileError(scope, trace, cause);
         }
         return OBJECT;
      }   
   }  
   
   private static class ConstraintConstant extends Value {
      
      private final Constraint constraint;
      private final Scope scope;
      
      public ConstraintConstant(Evaluation evaluation, Set<String> imports, Scope scope) {
         this.constraint = new ConstraintEvaluation(evaluation, imports);
         this.scope = scope;        
      }
      
      @Override
      public boolean isConstant() {
         return true;
      }
      
      @Override
      public Constraint getConstraint() {
         return constraint;
      }
      
      @Override
      public <T> T getValue() {
         return (T)constraint.getType(scope);            
      }
      
      @Override
      public void setValue(Object value){
         throw new InternalStateException("Illegal modification of literal '" + value + "'");
      } 
      
      @Override
      public String toString() {
         return String.valueOf(constraint);
      }
   }   
   
   private static class ConstraintEvaluation extends Constraint {
         
      private final Constraint constraint;
      private final List<String> imports; 
      
      public ConstraintEvaluation(Evaluation evaluation, Set<String> imports) {
         this.constraint = new TypeConstraint(evaluation);
         this.imports = new ArrayList<String>(imports);      
      }     
      
      @Override
      public boolean isConstant() {
         return true;
      }
      
      @Override
      public List<String> getImports(Scope scope) {
         return imports;
      }
      
      @Override
      public String getName(Scope scope) {
         return constraint.getName(scope);
      }

      @Override
      public List<Constraint> getGenerics(Scope scope) {
         return constraint.getGenerics(scope);
      }
      
      @Override
      public Type getType(Scope scope) {
         return constraint.getType(scope);
      }  
      
      @Override
      public String toString() {
         return String.valueOf(constraint);
      }
   }
}