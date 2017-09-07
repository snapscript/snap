package org.snapscript.tree.define;

import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.define.ObjectInstance;
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