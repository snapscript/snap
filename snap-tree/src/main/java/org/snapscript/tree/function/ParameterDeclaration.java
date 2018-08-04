package org.snapscript.tree.function;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.DeclarationConstraint;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.scope.Scope;
import org.snapscript.tree.Modifier;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.annotation.AnnotationList;

public class ParameterDeclaration {
   
   private DeclarationConstraint constraint;
   private AnnotationList annotations;
   private ModifierChecker checker;
   private NameReference reference;
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
      this.constraint = new DeclarationConstraint(constraint);
      this.reference = new NameReference(identifier);
      this.checker = new ModifierChecker(modifiers);
      this.annotations = annotations;
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
      boolean constant = checker.isConstant();
      int modifiers = checker.getModifiers();
      Constraint declare = constraint.getConstraint(scope, modifiers);
      String name = reference.getName(scope);

      return new Parameter(name, declare, constant, modifier != null);
   }
}