package org.snapscript.core.function.index;

import java.util.List;

import org.snapscript.core.Any;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Invocation;
import org.snapscript.core.function.Signature;
import org.snapscript.core.type.Type;

public class FunctionAdapter implements Any {
   
   private final Function function;
   
   public FunctionAdapter(Object function) {
      this.function = (Function)function;
   }
   
   public int getModifiers() {
      return function.getModifiers();
   }
   
   public Type getSource() {
      return function.getSource();
   }
   
   public Type getHandle() {
      return function.getHandle();
   }
   
   public Constraint getConstraint() {
      return function.getConstraint();
   }
   
   public String getName() {
      return function.getName();
   }
   
   public Signature getSignature() {
      return function.getSignature();
   }
   
   public List<Annotation> getAnnotations() {
      return function.getAnnotations();
   }
   
   public Invocation getInvocation() {
      return function.getInvocation();
   }
   
   public String getDescription() {
      return function.getDescription();
   }
   
   @Override
   public String toString() {
      return function.toString();
   }
}
