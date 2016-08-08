package org.snapscript.tree.function;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.function.Parameter;
import org.snapscript.tree.Modifier;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.Constraint;

public class ParameterDeclaration {
   
   private AnnotationList annotations;
   private NameExtractor extractor;
   private Constraint constraint;
   private Parameter parameter;
   private Modifier modifier;
   
   public ParameterDeclaration(AnnotationList annotations, Evaluation identifier){
      this(annotations, identifier, null, null);
   }
   
   public ParameterDeclaration(AnnotationList annotations, Evaluation identifier, Constraint constraint){
      this(annotations, identifier, null, constraint);
   }
   
   public ParameterDeclaration(AnnotationList annotations, Evaluation identifier, Modifier modifier){
      this(annotations, identifier, modifier, null);
   }
   
   public ParameterDeclaration(AnnotationList annotations, Evaluation identifier, Modifier modifier, Constraint constraint){
      this.extractor = new NameExtractor(identifier);
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
      String name = extractor.extract(scope);
      
      if(constraint != null && name != null) {
         Value value = constraint.evaluate(scope, null);
         Type type = value.getValue();
         
         if(type == null) {
            throw new InternalStateException("Constraint for '" +name + "' has not been imported");
         }
         return new Parameter(name, type, modifier != null);
      }
      return new Parameter(name, null, modifier != null);
   }
}  