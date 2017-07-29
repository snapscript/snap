
package org.snapscript.tree.define;

import org.snapscript.core.Model;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.define.Instance;
import org.snapscript.core.define.ObjectInstance;

public class ObjectInstanceBuilder {
 
   private final Type type;
   
   public ObjectInstanceBuilder(Type type) {
      this.type = type;
   }

   public Instance create(Scope scope, Instance base, Type real) throws Exception {
      Class actual = base.getClass();
      
      if(actual != ObjectInstance.class) { // false if this(...) is called
         Model model = scope.getModel();
         Module module = type.getModule();
         Object object = base.getBridge();
         
         return new ObjectInstance(module, model, base, object, real); // create the first instance
      }
      return base;
   }
}
