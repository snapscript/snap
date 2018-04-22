package org.snapscript.core.type.index;

import static org.snapscript.core.type.Category.ARRAY;

import java.util.Collections;
import java.util.List;

import org.snapscript.common.CompleteProgress;
import org.snapscript.common.Progress;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Phase;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeDescription;
import org.snapscript.core.type.TypeScope;

public class ScopeArrayType implements Type {
   
   private static final Class[] ARRAYS = {
      Object.class,
      Object[].class,
      Object[][].class,
      Object[][][].class,
   };
   
   private final TypeDescription description;
   private final Progress<Phase> progress;
   private final Module module;
   private final Scope scope;
   private final Type entry;
   private final String name;
   private final int order;
   private final int size;
   
   public ScopeArrayType(Module module, String name, Type entry, int size, int order){
      this.progress = new CompleteProgress<Phase>();
      this.description = new TypeDescription(this);
      this.scope = new TypeScope(this);
      this.module = module;
      this.order = order;
      this.entry = entry;
      this.name = name;
      this.size = size;
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
   public List<Function> getFunctions(){
      return Collections.emptyList();
   }
   
   @Override
   public List<Constraint> getTypes(){
      return Collections.emptyList();
   }
   
   @Override
   public Category getCategory() {
      return ARRAY;
   }
   
   @Override
   public Module getModule(){
      return module;
   }
   
   @Override
   public Scope getScope(){
      return scope;
   }
   
   @Override
   public String getName(){
      return name;
   }
   
   @Override
   public Class getType() {
      return ARRAYS[size];
   }
   
   @Override
   public Type getOuter(){
      return null;
   }
   
   @Override
   public Type getEntry(){
      return entry;
   }
   
   @Override
   public int getOrder() {
      return order;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }
}