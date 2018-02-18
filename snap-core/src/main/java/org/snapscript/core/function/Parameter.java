package org.snapscript.core.function;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.ConstantConstraint;
import org.snapscript.core.Constraint;
import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public class Parameter {
   
   private final List<Annotation> annotations;
   private final Constraint constraint;
   private final String name;
   private final boolean constant;
   private final boolean variable;
   
   public Parameter(String name, Type constraint, boolean constant){
      this(name, constraint, constant, false);
   }
   
   public Parameter(String name, Type constraint, boolean constant, boolean variable){
      this.constraint = new ConstantConstraint(constraint);
      this.annotations = new ArrayList<Annotation>();
      this.variable = variable;
      this.constant = constant;
      this.name = name;
   }
   
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   public Constraint getType() {
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