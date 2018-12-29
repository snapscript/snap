package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_VALUES;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.ScopeState;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;

public class EnumInstance extends StaticBlock {
   
   private final EnumConstantGenerator generator;
   private final EnumConstructorBinder binder;
   private final String name;
   
   public EnumInstance(String name, ArgumentList arguments, int index) {
      this.generator = new EnumConstantGenerator(name, index);
      this.binder = new EnumConstructorBinder(arguments);
      this.name = name;
   }

   @Override
   protected void allocate(Scope scope) throws Exception {
      Type type = scope.getType();
      ScopeState state = scope.getState();
      Instance instance = binder.bind(scope, type);
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      Object object = wrapper.toProxy(instance);
      Value value = Value.getConstant(instance);      
      Value values = state.getValue(ENUM_VALUES);
      List list = values.getValue();
      
      generator.generate(instance, type);
      state.addValue(name, value);
      list.add(object);
   }
}