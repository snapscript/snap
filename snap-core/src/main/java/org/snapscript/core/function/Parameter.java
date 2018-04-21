package org.snapscript.core.function;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;

public class Parameter {
   
   private final List<Annotation> annotations;
   private final Constraint constraint;
   private final String name;
   private final boolean constant;
   private final boolean variable;
   
   public Parameter(String name, Constraint constraint, boolean constant){
      this(name, constraint, constant, false);
   }
   
   public Parameter(String name, Constraint constraint, boolean constant, boolean variable){
      this.annotations = new ArrayList<Annotation>();
      this.constraint = constraint;
      this.variable = variable;
      this.constant = constant;
      this.name = name;
   }
   
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   public Constraint getConstraint() {
      return constraint;
   }
   
   public boolean isConstant() {
      return constant;
   }
   
   public boolean isVariable() { // var-arg ... 
      return variable;
   }
   
   public String getName() {
      return name;
   }
}