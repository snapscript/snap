package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_VALUES;

import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.convert.proxy.ProxyWrapper;
import org.snapscript.core.function.resolve.FunctionCall;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
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
      State state = scope.getState();
      FunctionCall call = binder.bind(scope, type);
      Module module = scope.getModule();
      Context context = module.getContext();
      ProxyWrapper wrapper = context.getWrapper();
      
      if(call == null){
         throw new InternalStateException("No constructor for enum '" + name + "' in '" + type+ "'");
      }
      Value result = call.call();
      Instance instance = result.getValue();
      Object object = wrapper.toProxy(instance);
      Value value = Value.getConstant(instance);      
      Value values = state.get(ENUM_VALUES);
      List list = values.getValue();
      
      generator.generate(instance, type);
      state.add(name, value);
      list.add(object);
   }
}