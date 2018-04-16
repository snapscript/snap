package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.DeclarationAllocator;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierData;

public class MemberFieldAssembler {
   
   private final ModifierChecker checker;

   public MemberFieldAssembler(ModifierData modifiers) {
      this.checker = new ModifierChecker(modifiers);
   }
   
   public TypeState assemble(MemberFieldData data) throws Exception {
      Evaluation declaration = create(data);
      
      if (checker.isStatic()) {
         return new StaticField(declaration);
      }
      return new InstanceField(declaration);
   }
   
   private Evaluation create(MemberFieldData data) throws Exception {
      int modifiers = checker.getModifiers();
      String name = data.getName();
      Constraint constraint = data.getConstraint();
      Evaluation declare = data.getValue();
      
      return new Declaration(name, constraint, declare, modifiers);
   }
   
   private static class Declaration extends Evaluation {
      
      private final DeclarationAllocator allocator;
      private final Constraint constraint;
      private final Evaluation declare;
      private final String name;
      private final int modifiers;
      
      public Declaration(String name, Constraint constraint, Evaluation declare, int modifiers) {
         this.allocator = new MemberFieldAllocator(constraint, declare);
         this.constraint = constraint;
         this.modifiers = modifiers;
         this.declare = declare;
         this.name = name;
      }  

      @Override
      public void define(Scope scope) throws Exception {
         if(declare != null) {
            declare.define(scope);
         }
      }

      @Override
      public Constraint compile(Scope scope, Constraint left) throws Exception {
         Value value = allocator.compile(scope, name, modifiers);
         State state = scope.getState();
         
         try { 
            state.add(name, value);
         }catch(Exception e) {
            throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
         }  
         return constraint;
      }
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         Value value = allocator.allocate(scope, name, modifiers);
         State state = scope.getState();
         
         try { 
            state.add(name, value);
         }catch(Exception e) {
            throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
         }  
         return value;
      }
   }
}