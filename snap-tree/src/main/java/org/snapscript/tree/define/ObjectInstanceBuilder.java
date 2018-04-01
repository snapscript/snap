package org.snapscript.tree.define;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.scope.instance.ObjectInstance;
import org.snapscript.core.type.Type;
import org.snapscript.core.module.Module;
import org.snapscript.core.platform.Bridge;

public class ObjectInstanceBuilder {
 
   private final Type type;
   
   public ObjectInstanceBuilder(Type type) {
      this.type = type;
   }

   public Instance create(Scope scope, Instance base, Type real) throws Exception {
      Class actual = base.getClass();
      
      if(actual != ObjectInstance.class) { // false if this(...) is called
         Module module = type.getModule();
         Bridge object = base.getBridge();
         
         return new ObjectInstance(module, base, object, real); // create the first instance
      }
      return base;
   }
}