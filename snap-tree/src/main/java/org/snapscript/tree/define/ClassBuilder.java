package org.snapscript.tree.define;

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.tree.annotation.AnnotationList;

public class ClassBuilder extends Statement {   
   
   private final ClassConstantInitializer builder;
   private final AnnotationList annotations;
   private final TypeHierarchy hierarchy;
   private final TypeName name;
   
   public ClassBuilder(AnnotationList annotations, TypeName name, TypeHierarchy hierarchy) {
      this.builder = new ClassConstantInitializer();
      this.annotations = annotations;
      this.hierarchy = hierarchy;
      this.name = name;
   }
   
   @Override
   public Result define(Scope outer) throws Exception {
      Module module = outer.getModule();
      String alias = name.getName(outer);
      Type type = module.addType(alias);
      
      return ResultType.getNormal(type);
   }
   
   @Override
   public Result compile(Scope outer) throws Exception {
      Module module = outer.getModule();
      String alias = name.getName(outer);
      Type type = module.getType(alias);
      Scope scope = type.getScope();
      
      annotations.apply(scope, type);
      hierarchy.update(scope, type); 
      builder.declare(scope, type);
      
      return ResultType.getNormal(type);
   }
}
