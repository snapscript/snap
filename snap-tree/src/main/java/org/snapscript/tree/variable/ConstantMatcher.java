package org.snapscript.tree.variable;

import org.snapscript.core.Module;
import org.snapscript.core.ModuleScopeBinder;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeTraverser;

public class ConstantMatcher {
   
   private final ModuleScopeBinder binder;
   private final TypeTraverser finder;
   private final String name;
   
   public ConstantMatcher(String name) {
      this.binder = new ModuleScopeBinder();
      this.finder = new TypeTraverser();
      this.name = name;
   }
   
   public Object match(Scope scope) {
      Scope current = binder.bind(scope); // this could be slow
      Module module = current.getModule();
      Type type = module.getType(name);
      Type parent = current.getType();
      
      if(type == null) {
         Object result = module.getModule(name);
         
         if(result == null && parent != null) {
            result = finder.findEnclosing(parent, name);
         }
         return result;
      }
      return type;
   }
}