package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;
import static org.snapscript.core.Reserved.ENUM_VALUES;
import static org.snapscript.core.constraint.Constraint.INTEGER;
import static org.snapscript.core.constraint.Constraint.STRING;

import java.util.List;
import java.util.concurrent.Callable;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
import org.snapscript.core.scope.Value;
import org.snapscript.core.type.Allocation;
import org.snapscript.core.type.Type;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;

public class EnumState extends Allocation {
   
   private final EnumConstantGenerator generator;
   private final EnumConstructorBinder binder;
   private final NameReference reference;
   private final int index;
   
   public EnumState(EnumKey key, ArgumentList arguments, int index) {
      this.generator = new EnumConstantGenerator();
      this.binder = new EnumConstructorBinder(arguments);
      this.reference = new NameReference(key);
      this.index = index;
   }

   @Override
   public void compile(Scope scope, Type type) throws Exception {
      String name = reference.getName(scope);
      State state = scope.getState();

      if(type == null) {
         throw new InternalStateException("No type found for enum " + name); // class not found
      }
      Callable<Value> call = binder.bind(scope, type);
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      
      if(call == null){
         throw new InternalStateException("No constructor for enum '" + name + "' in '" + type+ "'");
      }
      Value result = call.call();
      Scope instance = result.getValue();
      Value value = state.get(ENUM_VALUES);
      List values = value.getValue();
      Object object = wrapper.toProxy(instance);
      Constraint constraint = Constraint.getVariable(type);
            
      generator.generateConstant(scope, name, type, instance, constraint);
      generator.generateConstant(instance, ENUM_NAME, type, name, STRING); // might declare name as property many times
      generator.generateConstant(instance, ENUM_ORDINAL, type, index, INTEGER);
      values.add(object);
   }
}