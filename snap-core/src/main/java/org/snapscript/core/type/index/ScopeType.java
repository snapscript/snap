package org.snapscript.core.type.index;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.common.LockProgress;
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

public class ScopeType implements Type {
   
   private final TypeDescription description;
   private final List<Annotation> annotations;
   private final List<Constraint> constraints;
   private final List<Property> properties;
   private final List<Function> functions;
   private final List<Constraint> types;
   private final Progress<Phase> progress;
   private final Category category;
   private final Module module;
   private final Scope scope;
   private final Type outer;
   private final String name;
   private final int order;
   
   public ScopeType(Module module, Type outer, Category category, String name, int order){
      this.description = new TypeDescription(this);
      this.annotations = new ArrayList<Annotation>();
      this.constraints = new ArrayList<Constraint>();
      this.properties = new ArrayList<Property>();
      this.functions = new ArrayList<Function>();
      this.types = new ArrayList<Constraint>();
      this.progress = new LockProgress<Phase>();
      this.scope = new TypeScope(this);
      this.category = category;
      this.module = module;
      this.outer = outer;
      this.order = order;
      this.name = name;
   }
   
   @Override
   public Progress<Phase> getProgress() {
      return progress;
   }
   
   @Override
   public List<Annotation> getAnnotations() {
      return annotations;
   }
   
   @Override
   public List<Constraint> getConstraints() {
      return constraints;
   }
   
   @Override
   public List<Property> getProperties() {
      return properties;
   }
   
   @Override
   public List<Function> getFunctions(){
      return functions;
   }
   
   @Override
   public List<Constraint> getTypes(){
      return types;
   }
   
   @Override
   public Category getCategory() {
      return category;
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
      return null;
   }
   
   @Override
   public Type getOuter(){
      return outer;
   }
   
   @Override
   public Type getEntry(){
      return null;
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