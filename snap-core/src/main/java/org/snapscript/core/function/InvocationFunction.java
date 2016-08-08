package org.snapscript.core.function;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public class InvocationFunction<T> implements Function<T> {

   private final FunctionDescription description;
   private final List<Annotation> annotations;
   private final Invocation<T> invocation;
   private final Signature signature;
   private final Type constraint;
   private final Type parent;
   private final String name;
   private final int modifiers;

   public InvocationFunction(Signature signature, Invocation<T> invocation, Type parent, Type constraint, String name){
      this(signature, invocation, parent, constraint, name, 0);
   }
   
   public InvocationFunction(Signature signature, Invocation<T> invocation, Type parent, Type constraint, String name, int modifiers){
      this(signature, invocation, parent, constraint, name, modifiers, 0);
   }
   
   public InvocationFunction(Signature signature, Invocation<T> invocation, Type parent, Type constraint, String name, int modifiers, int start){
      this.description = new FunctionDescription(signature, parent, name, start);
      this.annotations = new ArrayList<Annotation>();
      this.invocation = invocation;
      this.constraint = constraint;
      this.signature = signature;
      this.modifiers = modifiers;
      this.parent = parent;
      this.name = name;
   }
   
   @Override
   public int getModifiers(){
      return modifiers;
   }
   
   @Override
   public Type getType() {
      return parent;
   }
   
   @Override
   public Type getDefinition() {
      return signature.getDefinition();
   }
   
   @Override
   public Type getConstraint() {
      return constraint;
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public Signature getSignature(){
      return signature;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   @Override
   public Invocation<T> getInvocation(){
      return invocation;
   }
   
   @Override
   public String getDescription() {
      return description.getDescription();
   }
   
   @Override
   public String toString(){
      return description.toString();
   }
}
