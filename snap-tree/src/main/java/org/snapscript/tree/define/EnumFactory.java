package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;
import static org.snapscript.core.Reserved.ENUM_VALUES;

import java.util.List;
import java.util.concurrent.Callable;

import org.snapscript.core.Bug;
import org.snapscript.core.Constraint;
import org.snapscript.core.Context;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.Value;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameReference;

public class EnumFactory extends TypeFactory {
   
   private final EnumConstantGenerator generator;
   private final EnumConstructorBinder binder;
   private final NameReference reference;
   private final int index;
   
   public EnumFactory(EnumKey key, ArgumentList arguments, int index) {
      this.generator = new EnumConstantGenerator();
      this.binder = new EnumConstructorBinder(arguments);
      this.reference = new NameReference(key);
      this.index = index;
   }

   @Bug("fix constants")
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
      
      generator.generateConstant(scope, name, type, instance, Constraint.getInstance(type));
      generator.generateConstant(instance, ENUM_NAME, type, name, Constraint.getInstance(String.class)); // might declare name as property many times
      generator.generateConstant(instance, ENUM_ORDINAL, type, index, Constraint.getInstance(Integer.class));
      values.add(object);
   }
}