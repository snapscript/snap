package org.snapscript.tree.define;

import org.snapscript.core.ConstantConstraint;
import org.snapscript.core.Constraint;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Local;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Table;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.Value;
import org.snapscript.tree.DeclarationAllocator;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierData;

public class MemberFieldAssembler {
   
   private final ModifierChecker checker;

   public MemberFieldAssembler(ModifierData modifiers) {
      this.checker = new ModifierChecker(modifiers);
   }
   
   public TypeFactory assemble(MemberFieldData data) throws Exception {
      Evaluation declaration = create(data);
      
      if (checker.isStatic()) {
         return new StaticFieldFactory(declaration);
      }
      return new InstanceFieldFactory(declaration);
   }
   
   private Evaluation create(MemberFieldData data) throws Exception {
      int modifiers = checker.getModifiers();
      String name = data.getName();
      Type type = data.getConstraint();
      Evaluation declare = data.getValue();
      
      return new Declaration(name, type, declare, modifiers);
   }
   
   private static class Declaration extends Evaluation {
      
      private final DeclarationAllocator allocator;
      private final Constraint constraint;
      private final String name;
      private final int modifiers;
      
      public Declaration(String name, Type type, Evaluation declare, int modifiers) {
         this.constraint = new ConstantConstraint(type);
         this.allocator = new MemberFieldAllocator(constraint, declare);
         this.modifiers = modifiers;
         this.name = name;
      }  

      @Override
      public Constraint validate(Scope scope, Constraint left) throws Exception {
         Value value = allocator.validate(scope, name, modifiers);
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