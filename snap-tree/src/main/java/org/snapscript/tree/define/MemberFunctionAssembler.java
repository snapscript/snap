package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintExtractor;
import org.snapscript.tree.function.ParameterList;

public class MemberFunctionAssembler {
   
   private final ConstraintExtractor constraint;
   private final ParameterList parameters;
   private final ModifierChecker checker;
   private final NameExtractor extractor;
   private final ModifierList list;
   private final Statement body;
   
   public MemberFunctionAssembler(ModifierList list, Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){ 
      this.constraint = new ConstraintExtractor(constraint);
      this.extractor = new NameExtractor(identifier);
      this.checker = new ModifierChecker(list);
      this.parameters = parameters;
      this.list = list;
      this.body = body;
   } 

   public MemberFunctionBuilder assemble(Type type, int mask) throws Exception {
      Scope scope = type.getScope();
      String name = extractor.extract(scope);
      Signature signature = parameters.create(scope);
      Type returns = constraint.extract(scope);
      int modifiers = mask | list.getModifiers();
      
      if(checker.isStatic()) {
         return new StaticFunctionBuilder(signature, body, returns, name, modifiers);
      }
      return new InstanceFunctionBuilder(signature, body, returns, name, modifiers);
      
   }
}
