package org.snapscript.tree.define;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.tree.literal.TextLiteral;

public class TypeName {
   
   protected final TextLiteral literal;
   
   public TypeName(TextLiteral literal) {
      this.literal = literal;
   }
   
   public String getName(Scope scope) throws Exception{
      Value value = literal.evaluate(scope, null);
      return value.getValue();
   }
   
   public Type getType(Scope scope) throws Exception{
      Value value = literal.evaluate(scope, null);
      String name = value.getValue();
      Module module = scope.getModule();
      Type base = module.getType(name);
      
      if(base == null) {
         throw new InternalStateException("Type '" + name + "' could not be resolved");
      }
      return base;
   }  
}
