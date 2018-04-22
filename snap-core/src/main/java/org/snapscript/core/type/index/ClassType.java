package org.snapscript.core.type.index;

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

public class ClassType implements Type {

   private final TypeDescription description;
   private final Progress<Phase> progress;
   private final ClassIndex index;
   private final Scope scope;
   private final Class type;
   private final String name;
   private final int order;
   
   public ClassType(ClassIndexer indexer, Class type, String name, int order) {
      this.progress = new CompleteProgress<Phase>();
      this.description = new TypeDescription(this);
      this.index = new ClassIndex(indexer, this);
      this.scope = new TypeScope(this);
      this.name = name;
      this.type = type;
      this.order = order;
   }
   
   @Override
   public Progress<Phase> getProgress() {
      return progress;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return index.getAnnotations();
   }
   
   @Override
   public List<Constraint> getConstraints() {
      return index.getConstraints();
   }
   
   @Override
   public List<Property> getProperties() {
      return index.getProperties();
   }

   @Override
   public List<Function> getFunctions() {
      return index.getFunctions();
   }

   @Override
   public List<Constraint> getTypes() {
      return index.getTypes();
   }
   
   @Override
   public Category getCategory() {
      return index.getCategory();
   }
   
   @Override
   public Module getModule() {
      return index.getModule();
   }
   
   @Override
   public Type getOuter() {
      return index.getOuter();
   }

   @Override
   public Type getEntry() {
      return index.getEntry();
   }
   
   @Override
   public Scope getScope() {
      return scope;
   }

   @Override
   public String getName() {
      return name;
   }

   @Override
   public Class getType() {
      return type;
   }
   
   @Override
   public int getOrder(){
      return order;
   }
   
   @Override
   public String toString() {
      return description.toString();
   }

}