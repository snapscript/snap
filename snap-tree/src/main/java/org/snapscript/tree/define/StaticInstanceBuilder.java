package org.snapscript.tree.define;

import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.instance.Instance;
import org.snapscript.core.scope.instance.StaticInstance;
import org.snapscript.core.type.Type;

public class StaticInstanceBuilder {
   
   private final Type type;
   
   public StaticInstanceBuilder(Type type) {
      this.type = type;
   }

   public Instance create(Scope scope, Instance base, Type real) throws Exception {
      if(base == null) {
         Module module = type.getModule();
         Scope inner = real.getScope();
         
         return new StaticInstance(module, inner, real); // create the first instance
      }
      return base;
   }
}