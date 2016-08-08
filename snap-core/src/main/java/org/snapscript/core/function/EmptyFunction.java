package org.snapscript.core.function;

import static org.snapscript.core.ModifierType.ABSTRACT;
import static org.snapscript.core.Reserved.METHOD_CLOSURE;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Type;
import org.snapscript.core.annotation.Annotation;

public class EmptyFunction<T> implements Function<T> {

   private final FunctionDescription description;
   private final List<Annotation> annotations;
   private final Signature signature;
   private final String name;
   private final int modifiers;

   public EmptyFunction(Signature signature){
      this(signature, METHOD_CLOSURE);
   }
   
   public EmptyFunction(Signature signature, String name){
      this(signature, name, ABSTRACT.mask);
   }
   
   public EmptyFunction(Signature signature, String name, int modifiers){
      this.description = new FunctionDescription(signature, null, name);
      this.annotations = new ArrayList<Annotation>();
      this.signature = signature;
      this.modifiers = modifiers;
      this.name = name;
   }
   
   @Override
   public int getModifiers(){
      return modifiers;
   }
   
   @Override
   public Type getDefinition() {
      return null;
   }
   
   @Override
   public Type getConstraint() {
      return null;
   }
   
   @Override
   public Type getType() {
      return null;
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
      return null;
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
