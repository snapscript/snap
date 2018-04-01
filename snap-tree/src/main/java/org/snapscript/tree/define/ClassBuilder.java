package org.snapscript.tree.define;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.ConstantPropertyBuilder;
import org.snapscript.core.property.Property;
import org.snapscript.tree.annotation.AnnotationList;

public class ClassBuilder {   
   
   private final AtomicReference<Type> reference;
   private final ClassConstantGenerator generator;
   private final ConstantPropertyBuilder builder;
   private final AnnotationList annotations;
   private final TypeHierarchy hierarchy;
   private final TypeName name;
   private final Category category;
   
   public ClassBuilder(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy, Category category) {
      this.reference = new AtomicReference<Type>();
      this.generator = new ClassConstantGenerator();
      this.builder = new ConstantPropertyBuilder();
      this.annotations = annotations;
      this.hierarchy = hierarchy;
      this.category = category;
      this.name = name;
   }
   
   public Type create(Scope outer) throws Exception {
      Module module = outer.getModule();
      String alias = name.getName(outer);
      Type type = module.addType(alias, category); 
      
      reference.set(type);
      
      return type;
   }
   
   public Type define(Scope outer) throws Exception {
      Type type = reference.get();
      Type enclosing = outer.getType();
      Scope scope = type.getScope();
      
      if(enclosing != null) {
         String name = type.getName();
         String prefix = enclosing.getName();
         String key = name.replace(prefix + '$', ""); // get the class name
         Property property = builder.createConstant(key, type, enclosing, NONE);
         List<Property> properties = enclosing.getProperties();
         
         properties.add(property);
      }
      annotations.apply(scope, type);
      generator.generate(scope, type);
      hierarchy.extend(scope, type); 
      
      return type;
   }
   
   public Type compile(Scope outer) throws Exception {
      return reference.get();
   }
}