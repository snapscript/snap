package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Statement;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.type.TypeBody;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class TraitFunction extends MemberFunction {
 
   public TraitFunction(AnnotationList annotations, ModifierList list, Evaluation identifier, ParameterList parameters){
      super(annotations, list, identifier, parameters);
   }
   
   public TraitFunction(AnnotationList annotations, ModifierList list, Evaluation identifier, ParameterList parameters, Constraint constraint){
      super(annotations, list, identifier, parameters, constraint);
   }
   
   public TraitFunction(AnnotationList annotations, ModifierList list, Evaluation identifier, ParameterList parameters, Statement body){  
      super(annotations, list, identifier, parameters, body);
   }
   
   public TraitFunction(AnnotationList annotations, ModifierList list, Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){  
      super(annotations, list, identifier, parameters, constraint, body);
   } 
   
   @Override
   protected TypeState assemble(TypeBody body, Type type, Scope scope, int mask) throws Exception {
      if(statement == null) {
         return super.assemble(body, type, scope, ModifierType.ABSTRACT.mask);
      }  
      return super.assemble(body, type, scope, 0);
   }
}