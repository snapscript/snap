package org.snapscript.tree.define;

import org.snapscript.core.ModifierType;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.literal.TextLiteral;

public class MemberFieldReference {
   
   private final NameReference identifier;
   
   public MemberFieldReference(TextLiteral identifier) {
      this.identifier = new NameReference(identifier);
   }
   
   public String getName(Type type, int modifiers) throws Exception {
      Scope scope = type.getScope();
      String name = identifier.getName(scope);
      Class real = type.getType();
      
      if(real == null) {
         int order = type.getOrder();
         
         if(ModifierType.isPrivate(modifiers)) {
            if(order > 0) {
               return "_" + order + "_" + name; // private name
            }
         }
      }
      return name;
   }
}
