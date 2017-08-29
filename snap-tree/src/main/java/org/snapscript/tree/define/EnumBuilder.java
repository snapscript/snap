package org.snapscript.tree.define;

import static org.snapscript.core.Category.ENUM;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;

public class EnumBuilder extends Statement {

   private final AtomicReference<Type> reference;
   private final EnumConstantBuilder builder;
   private final TypeHierarchy hierarchy;
   private final TypeName name;
   private final List values;
   
   public EnumBuilder(TypeName name, TypeHierarchy hierarchy) {
      this.reference = new AtomicReference<Type>();
      this.builder = new EnumConstantBuilder();
      this.values = new ArrayList();
      this.hierarchy = hierarchy;
      this.name = name;
   }
   
   @Override
   public Result define(Scope outer) throws Exception {
      Module module = outer.getModule();
      String alias = name.getName(outer);
      Type type = module.addType(alias, ENUM);
      
      reference.set(type);
      
      return ResultType.getNormal(type);
   }
   
   @Override
   public Result compile(Scope outer) throws Exception {
      Type type = reference.get();
      Scope scope = type.getScope();
      
      builder.declare(scope, type, values);
      hierarchy.extend(scope, type); // this may throw exception if missing type
      
      return ResultType.getNormal(type);
   }
}