package org.snapscript.tree.reference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.snapscript.core.Entity;
import org.snapscript.core.ModifierType;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.TypeParameterConstraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;

public class GenericDeclaration { 

   private final ConstraintCompilation compilation;
   private final GenericArgumentList list;
   private final TypeReference type;   
   
   public GenericDeclaration(TypeReference type, GenericArgumentList list) {
      this.compilation = new ConstraintCompilation(type, list);
      this.list = list;
      this.type = type;
   }
   
   public Value declare(Scope scope) throws Exception {
      List<String> imports = new ArrayList<String>();
      
      try {
         Module module = scope.getModule();
         Scope outer = module.getScope();
         String name = type.qualify(scope, null);         

         if(list != null) {
            List<String> other = list.getReferences(scope);
            
            if(other != null) {
               imports.addAll(other);
            }
         }         
         if(name != null) {
            imports.add(name);
         }
         return new ConstraintConstant(compilation, imports, outer);
      }catch(Exception e) {
         throw new InternalStateException("Invalid import", e);
      }
   }
   
   private static class ConstraintCompilation {

      private final GenericArgumentList list;
      private final TypeReference type;

      public ConstraintCompilation(TypeReference type, GenericArgumentList list) {
         this.type = type;
         this.list = list;
      }

      public Constraint compile(Scope scope) { 
         try {
            Value value = type.evaluate(scope, null);
            Entity entity = value.getValue();
            int modifiers = entity.getModifiers();
                  
            if(!ModifierType.isModule(modifiers)) {
               String name = value.getName(scope);
               Type type = value.getType(scope);
               
               if(list != null) {
                  List<Constraint> generics = list.getConstraints(scope);    
                  
                  if(!generics.isEmpty()) {
                     return new TypeParameterConstraint(type, generics, name);
                  }
               }
               return new TypeParameterConstraint(type, name);
            }
            return value;
         }catch(Exception e) {
            throw new InternalStateException("Import not found", e);
         }
      }   
   }   
   
   private static class ConstraintConstant extends Value {
      
      private ConstraintCompilation compilation;
      private Constraint constraint;
      private List<String> imports; 
      private Scope scope;
      
      public ConstraintConstant(ConstraintCompilation compilation, List<String> imports, Scope scope) {
         this.imports = Collections.unmodifiableList(imports);
         this.compilation = compilation;
         this.scope = scope;        
      }
      
      @Override
      public boolean isProperty() {
         return false;
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
         if(constraint == null) {
            constraint = compilation.compile(scope);
         }
         return constraint.getName(scope);
      }
      
      @Override
      public <T> T getValue() {
         if(constraint == null) {            
            constraint = compilation.compile(scope);
         }
         return (T)constraint.getType(scope);            
      }
      
      @Override
      public List<Constraint> getGenerics(Scope scope) {
         if(constraint == null) {            
            constraint = compilation.compile(scope);
         }
         return constraint.getGenerics(scope);
      }
      
      @Override
      public Type getType(Scope scope) {
         if(constraint == null) {
            constraint = compilation.compile(scope);
         }
         return constraint.getType(scope);
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
}