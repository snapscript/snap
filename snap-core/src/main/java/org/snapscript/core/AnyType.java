package org.snapscript.core;

import static org.snapscript.core.Category.CLASS;
import static org.snapscript.core.Reserved.ANY_TYPE;

import java.util.Collections;
import java.util.List;

import org.snapscript.common.CompleteProgress;
import org.snapscript.common.Progress;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.property.Property;

public class AnyType implements Type {

   private final TypeDescription description;
   private final Progress<Phase> progress;
   private final Scope scope;
   private final String name;

   private AnyType(Scope scope) {
      this(scope, ANY_TYPE);
   }
   
   private AnyType(Scope scope, String name) {
      this.progress = new CompleteProgress<Phase>();
      this.description = new TypeDescription(this);
      this.scope = new TypeScope(this);
      this.name = name;
   }
   
   @Override
   public Progress<Phase> getProgress() {
      return progress;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return Collections.emptyList();
   }

   @Override
   public List<Property> getProperties() {
      return Collections.emptyList();
   }

   @Override
   public List<Function> getFunctions() {
      return Collections.emptyList();
   }

   @Override
   public List<Type> getTypes() {
      return Collections.emptyList();
   }
   
   @Override
   public Category getCategory(){
      return CLASS;
   }

   @Override
   public Module getModule() {
      return null;
   }
   
   @Override
   public Scope getScope(){
      return scope;
   }

   @Override
   public Class getType() {
      return null;
   }
   
   @Override
   public Type getOuter(){
      return null;
   }

   @Override
   public Type getEntry() {
      return null;
   }

   @Override
   public String getName() {
      return name; 
   }
   
   @Override
   public int getOrder() {
      return 0;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}
