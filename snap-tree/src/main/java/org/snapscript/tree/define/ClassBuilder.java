package org.snapscript.tree.define;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Category;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.tree.annotation.AnnotationList;

public class ClassBuilder extends Statement {   
   
   private final AtomicReference<Type> reference;
   private final ClassConstantBuilder builder;
   private final AnnotationList annotations;
   private final TypeHierarchy hierarchy;
   private final TypeName name;
   private final Category category;
   
   public ClassBuilder(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy, Category category) {
      this.reference = new AtomicReference<Type>();
      this.builder = new ClassConstantBuilder();
      this.annotations = annotations;
      this.hierarchy = hierarchy;
      this.category = category;
      this.name = name;
   }
   
   @Override
   public Result define(Scope outer) throws Exception {
      Module module = outer.getModule();
      String alias = name.getName(outer);
      Type type = module.addType(alias, category); 
      
      reference.set(type);
      
      return Result.getNormal(type);
   }
   
   @Override
   public Result compile(Scope outer) throws Exception {
      Type type = reference.get();
      Scope scope = type.getScope();
      
      annotations.apply(scope, type);
      builder.declare(scope, type);
      hierarchy.extend(scope, type); 
      
      return Result.getNormal(type);
   }
}