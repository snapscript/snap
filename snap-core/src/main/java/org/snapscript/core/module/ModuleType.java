package org.snapscript.core.module;

import static org.snapscript.core.type.Category.MODULE;

import java.util.Collections;
import java.util.List;

import org.snapscript.common.CompleteProgress;
import org.snapscript.common.Progress;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Phase;
import org.snapscript.core.type.Type;

public class ModuleType implements Type {
   
   private final Progress<Phase> progress;
   private final Module module;
   
   public ModuleType(Module module) {
      this.progress = new CompleteProgress<Phase>();
      this.module = module;
   }

   @Override
   public Progress<Phase> getProgress() {
      return progress;
   }

   @Override
   public List<Annotation> getAnnotations() {
      return module.getAnnotations();
   }
   
   @Override
   public List<Constraint> getConstraints() {
      return Collections.emptyList();
   }

   @Override
   public List<Property> getProperties() {
      return module.getProperties();
   }

   @Override
   public List<Function> getFunctions() {
      return module.getFunctions();
   }

   @Override
   public List<Constraint> getTypes() {
      return Collections.emptyList();
   }

   @Override
   public Category getCategory() {
      return MODULE;
   }

   @Override
   public Module getModule() {
      return module;
   }

   @Override
   public Scope getScope() {
      return module.getScope();
   }

   @Override
   public Class getType() {
      return Module.class;
   }

   @Override
   public Type getOuter() {
      return null;
   }

   @Override
   public Type getEntry() {
      return null;
   }

   @Override
   public String getName() {
      return module.getName();
   }

   @Override
   public int getOrder() {
      return 0;
   }
   
   @Override
   public String toString() {
      return module.toString();
   }

}
