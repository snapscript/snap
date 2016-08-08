package org.snapscript.tree.define;

import java.util.ArrayList;
import java.util.List;

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;

public class EnumBuilder extends Statement {

   private final EnumConstantInitializer builder;
   private final TypeHierarchy hierarchy;
   private final TypeName name;
   private final List values;
   
   public EnumBuilder(TypeName name, TypeHierarchy hierarchy) {
      this.builder = new EnumConstantInitializer();
      this.values = new ArrayList();
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
      
      hierarchy.update(scope, type); 
      builder.declare(scope, type, values);
      
      return ResultType.getNormal(type);
   }
}
