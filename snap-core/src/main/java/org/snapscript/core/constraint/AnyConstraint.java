package org.snapscript.core.constraint;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.DEFAULT_PACKAGE;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Context;
import org.snapscript.core.NameFormatter;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeLoader;

public class AnyConstraint extends Constraint {

   private final AtomicReference<Type> reference;
   private final NameFormatter formatter;
   
   public AnyConstraint(){
      this.reference = new AtomicReference<Type>();
      this.formatter = new NameFormatter();
   }

   @Override
   public Type getType(Scope scope) {
      Type type  = reference.get();
      
      if(type == null) {
         Module module = scope.getModule();
         Context context = module.getContext();
         TypeLoader loader = context.getLoader();
         String name = formatter.formatFullName(DEFAULT_PACKAGE, ANY_TYPE);
         Type base = loader.loadType(name);
         
         reference.set(base); // any is very last
         
         return base;
      }
      return type;
   }   
   
   @Override
   public String toString() {
      return String.format("%s.%s",  DEFAULT_PACKAGE, ANY_TYPE);
   }
}
