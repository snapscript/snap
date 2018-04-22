package org.snapscript.tree.define;

import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.type.Category.ENUM;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.variable.Value;

public class EnumBuilder {

   private final AtomicReference<Type> reference;
   private final EnumPropertyGenerator generator;
   private final ConstantPropertyBuilder builder;
   private final TypeHierarchy hierarchy;
   private final TypeName name;
   private final List values;
   
   public EnumBuilder(TypeName name, TypeHierarchy hierarchy) {
      this.reference = new AtomicReference<Type>();
      this.generator = new EnumPropertyGenerator();
      this.builder = new ConstantPropertyBuilder();
      this.values = new ArrayList();
      this.hierarchy = hierarchy;
      this.name = name;
   }
   
   public Type create(TypeBody body, Scope outer) throws Exception {
      Module module = outer.getModule();
      String alias = name.getName(outer);
      Type type = module.addType(alias, ENUM);
      
      reference.set(type);
      
      return type;
   }
   
   public Type define(TypeBody body, Scope outer) throws Exception {
      Type type = reference.get();
      Type enclosing = outer.getType();
      Scope scope = type.getScope();
      
      if(enclosing != null) {
         String name = type.getName();
         String prefix = enclosing.getName();
         String key = name.replace(prefix + '$', ""); // get the class name
         Value value = Value.getConstant(type);
         State state = outer.getState();
         
         builder.createStaticProperty(body, key, enclosing, NONE);
         state.add(key, value);
      }
      generator.generate(body, scope, type, values);
      hierarchy.define(scope, type); // this may throw exception if missing type
      
      return type;
   }
   
   public Type compile(TypeBody body, Scope outer) throws Exception {
      Type type = reference.get();
      
      hierarchy.compile(outer, type);
      
      return type;
   }
}