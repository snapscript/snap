package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintReference;
import org.snapscript.tree.function.ParameterList;

public class MemberFunctionAssembler {
   
   private final ConstraintReference constraint;
   private final ParameterList parameters;
   private final ModifierChecker checker;
   private final NameReference identifier;
   private final ModifierList list;
   private final Statement body;
   
   public MemberFunctionAssembler(ModifierList list, Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){ 
      this.constraint = new ConstraintReference(constraint);
      this.identifier = new NameReference(identifier);
      this.checker = new ModifierChecker(list);
      this.parameters = parameters;
      this.list = list;
      this.body = body;
   } 

   public MemberFunctionBuilder assemble(Type type, int mask) throws Exception {
      Scope scope = type.getScope();
      String name = identifier.getName(scope);
      Signature signature = parameters.create(scope);
      Type returns = constraint.getConstraint(scope);
      int modifiers = mask | list.getModifiers();
      
      if(checker.isStatic()) {
         return new StaticFunctionBuilder(signature, body, returns, name, modifiers);
      }
      return new InstanceFunctionBuilder(signature, body, returns, name, modifiers);
      
   }
}