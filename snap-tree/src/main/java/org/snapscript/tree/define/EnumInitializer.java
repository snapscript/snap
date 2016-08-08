package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.ENUM_NAME;
import static org.snapscript.core.Reserved.ENUM_ORDINAL;
import static org.snapscript.core.Reserved.ENUM_VALUES;

import java.util.List;
import java.util.concurrent.Callable;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.define.Initializer;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.NameExtractor;

public class EnumInitializer extends Initializer {
   
   private final EnumConstantInitializer initializer;
   private final EnumConstructorBinder binder;
   private final NameExtractor extractor;
   private final int index;
   
   public EnumInitializer(EnumKey key, ArgumentList list, int index) {
      this.initializer = new EnumConstantInitializer();
      this.binder = new EnumConstructorBinder(list);
      this.extractor = new NameExtractor(key);
      this.index = index;
   }
   
   @Override
   public Result execute(Scope scope, Type type) throws Exception {
      String name = extractor.extract(scope);
      State state = scope.getState();
      
      if(type == null) {
         throw new InternalStateException("No type found for enum " + name); // class not found
      }
      Callable<Result> call = binder.bind(scope, type);
           
      if(call == null){
         throw new InternalStateException("No constructor for enum " + name);
      }

      Result result = call.call();
      Scope instance = result.getValue();
      Value value = state.getValue(ENUM_VALUES);
      List values = value.getValue();
      
      initializer.declareConstant(scope, name, type, instance);
      initializer.declareConstant(instance, ENUM_NAME, type, name); // might declare name as property many times
      initializer.declareConstant(instance, ENUM_ORDINAL, type, index);
      values.add(instance);
      
      return ResultType.getNormal(instance);
   }

}
