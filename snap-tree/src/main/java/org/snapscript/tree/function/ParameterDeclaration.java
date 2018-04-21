package org.snapscript.tree.function;

import static org.snapscript.core.constraint.Constraint.NONE;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Parameter;
import org.snapscript.tree.Modifier;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.annotation.AnnotationList;

public class ParameterDeclaration {
   
   private AnnotationList annotations;
   private ModifierChecker checker;
   private NameReference reference;
   private Constraint constraint;
   private Parameter parameter;
   private Modifier modifier;
   
   public ParameterDeclaration(AnnotationList annotations, ModifierList modifiers, Evaluation identifier){
      this(annotations, modifiers, identifier, null, null);
   }
   
   public ParameterDeclaration(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, Constraint constraint){
      this(annotations, modifiers, identifier, null, constraint);
   }
   
   public ParameterDeclaration(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, Modifier modifier){
      this(annotations, modifiers, identifier, modifier, null);
   }
   
   public ParameterDeclaration(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, Modifier modifier, Constraint constraint){
      this.reference = new NameReference(identifier);
      this.checker = new ModifierChecker(modifiers);
      this.annotations = annotations;
      this.constraint = constraint;
      this.modifier = modifier;
   }

   public Parameter get(Scope scope) throws Exception {
      if(parameter == null) {
         parameter = create(scope);
         
         if(parameter != null) {
            annotations.apply(scope, parameter);
         }
      }
      return parameter;
   }
   
   private Parameter create(Scope scope) throws Exception {
      String name = reference.getName(scope);
      boolean constant = checker.isConstant();
      
      if(constraint != null && name != null) {
         Type type = constraint.getType(scope);
         
         if(type == null) {
            throw new InternalStateException("Constraint for '" +name + "' has not been imported");
         }
         return new Parameter(name, constraint, constant, modifier != null);
      }
      return new Parameter(name, NONE, constant, modifier != null);
   }
}