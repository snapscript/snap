package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.AnyLoader;
import org.snapscript.core.type.Type;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.reference.TraitReference;
import org.snapscript.tree.reference.TypeReference;

public class TypeHierarchy {
   
   private final TraitReference[] traits; 
   private final TypeReference name;
   private final AnyLoader loader;

   public TypeHierarchy(TraitReference... traits) {
      this(null, traits);     
   }
   
   public TypeHierarchy(TypeReference name, TraitReference... traits) {
      this.loader = new AnyLoader();
      this.traits = traits;
      this.name = name;
   }

   public void extend(Scope scope, Type type) throws Exception {
      List<Type> types = type.getTypes();
      
      if(name != null) {
         Value value = name.evaluate(scope, null);
         Type base = value.getValue();
         
         if(base == null) {
            throw new InternalStateException("Type '" + type + "' could not resolve base");
         }
         types.add(base);  
      }else {
         Type base = loader.loadType(scope);
         
         if(base == null) {
            throw new InternalStateException("Type '" + type + "' could not be defined");
         }
         types.add(base);
      }
      with(scope, type);
   }

   private void with(Scope scope, Type type) throws Exception {
      List<Type> types = type.getTypes();
      
      for(int i = 0; i < traits.length; i++) {
         Value value = traits[i].evaluate(scope, null);
         Type trait = value.getValue();
         
         if(trait != null) {
            Class base = trait.getType();
            
            if(base != null) {
               if(!base.isInterface()) {
                  throw new InternalStateException("Type '" + trait + "' is not a trait for '" + type + "'");
               }
            }
            types.add(trait);
         }
      }
   }

}