package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Scope;
import org.snapscript.core.Type;

public class TypeHierarchy {
   
   private final AnyDefinition definition;
   private final TraitName[] traits; 
   private final TypeName name;

   public TypeHierarchy(TraitName... traits) {
      this(null, traits);     
   }
   
   public TypeHierarchy(TypeName name, TraitName... traits) {
      this.definition = new AnyDefinition();
      this.traits = traits;
      this.name = name;
   }

   public void update(Scope scope, Type type) throws Exception {
      List<Type> types = type.getTypes();
      
      if(name != null) {
         Type base = name.getType(scope);
         
         if(base != null) {
            types.add(base);
         }
      }else {
         Type base = definition.create(scope);
         
         if(base != null) {
            types.add(base);
         }
      }
      for(int i = 0; i < traits.length; i++) {
         Type trait = traits[i].getType(scope);
         
         if(trait != null) {
            types.add(trait);
         }
      }
   }

}
