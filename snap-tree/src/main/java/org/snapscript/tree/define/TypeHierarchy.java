package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.AnyLoader;
import org.snapscript.core.type.Type;
import org.snapscript.tree.constraint.TraitConstraint;
import org.snapscript.tree.constraint.TypeConstraint;

public class TypeHierarchy {
   
   private final TraitConstraint[] traits; 
   private final TypeConstraint base;
   private final AnyLoader loader;

   public TypeHierarchy(TraitConstraint... traits) {
      this(null, traits);     
   }
   
   public TypeHierarchy(TypeConstraint base, TraitConstraint... traits) {
      this.loader = new AnyLoader();
      this.traits = traits;
      this.base = base;
   }

   public void extend(Scope scope, Type type) throws Exception {
      List<Constraint> types = type.getTypes();
      
      if(base != null) {
         Type match = base.getType(scope);
         
         if(match == null) {
            throw new InternalStateException("Type '" + type + "' could not resolve base");
         }
         types.add(base);  
      }else {
         Type match = loader.loadType(scope);
         Constraint constraint = Constraint.getConstraint(match);
         
         if(match == null) {
            throw new InternalStateException("Type '" + type + "' could not be defined");
         }
         types.add(constraint);
      }
      with(scope, type);
   }

   private void with(Scope scope, Type type) throws Exception {
      List<Constraint> types = type.getTypes();
      
      for(int i = 0; i < traits.length; i++) {
         Constraint constraint = traits[i];
         Type trait = constraint.getType(scope);
         
         if(trait != null) {
            Class base = trait.getType();
            
            if(base != null) {
               if(!base.isInterface()) {
                  throw new InternalStateException("Type '" + trait + "' is not a trait for '" + type + "'");
               }
            }
            types.add(constraint);
         }
      }
   }

}