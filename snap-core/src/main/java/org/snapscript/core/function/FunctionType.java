package org.snapscript.core.function;

import static org.snapscript.core.Reserved.METHOD_CLOSURE;
import static org.snapscript.core.type.Category.FUNCTION;

import java.util.Collections;
import java.util.List;

import org.snapscript.common.CompleteProgress;
import org.snapscript.common.Progress;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Phase;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeDescription;
import org.snapscript.core.type.TypeScope;

public class FunctionType implements Type {
   
   private final TypeDescription description;
   private final Progress<Phase> progress;
   private final Function function;
   private final Module module;
   private final Scope scope;
   private final String name;
   
   public FunctionType(Signature signature, Module module) {
      this(signature, module, METHOD_CLOSURE); // poor name for hash?
   }
   
   public FunctionType(Signature signature, Module module, String name) {
      this.function = new EmptyFunction(signature, METHOD_CLOSURE);
      this.progress = new CompleteProgress<Phase>();
      this.description = new TypeDescription(this);
      this.scope = new TypeScope(this);
      this.module = module;
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
   public List<Constraint> getConstraints() {
      return Collections.emptyList();
   }

   @Override
   public List<Property> getProperties() {
      return Collections.emptyList();
   }

   @Override
   public List<Function> getFunctions() {
      return Collections.singletonList(function);
   }

   @Override
   public List<Constraint> getTypes() {
      return Collections.emptyList();
   }
   
   @Override
   public Category getCategory(){
      return FUNCTION;
   }

   @Override
   public Module getModule() {
      return module;
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