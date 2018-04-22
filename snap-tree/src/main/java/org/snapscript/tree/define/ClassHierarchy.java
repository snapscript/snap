package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.ConstraintVerifier;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.AnyLoader;
import org.snapscript.core.type.Type;
import org.snapscript.tree.constraint.TraitConstraint;
import org.snapscript.tree.constraint.TypeConstraint;

public class ClassHierarchy implements Compilation {
   
   private final TraitConstraint[] traits; 
   private final TypeConstraint base;

   public ClassHierarchy(TraitConstraint... traits) {
      this(null, traits);     
   }
   
   public ClassHierarchy(TypeConstraint base, TraitConstraint... traits) {
      this.traits = traits;
      this.base = base;
   }

   @Override
   public TypeHierarchy compile(Module module, Path path, int line) throws Exception {
      return new CompileResult(base, traits, path, line);
   }
   
   private static class CompileResult implements TypeHierarchy {
      
      private final ConstraintVerifier verifier;
      private final TraitConstraint[] traits; 
      private final TypeConstraint base;
      private final AnyLoader loader;
      private final Path path;
      private final int line;
      
      public CompileResult(TypeConstraint base, TraitConstraint[] traits, Path path, int line) {
         this.verifier = new ConstraintVerifier();
         this.loader = new AnyLoader();
         this.traits = traits;
         this.base = base;
         this.path = path;
         this.line = line;
      }

      public void define(Scope scope, Type type) throws Exception {
         List<Constraint> types = type.getTypes();
         
         if(base == null) {
            Type match = loader.loadType(scope);
            Constraint base = Constraint.getConstraint(match);
            
            types.add(base);
         } else {
            Type match = base.getType(scope);
            
            if(match == null) {
               throw new InternalStateException("Invalid super class for type '" + type + "' in " + path + " at line " + line);
            }
            types.add(base);  
         }
         for(int i = 0; i < traits.length; i++) {
            Constraint trait = traits[i];
            Type match = trait.getType(scope);
            
            if(match == null) {
               throw new InternalStateException("Invalid trait for type '" + type + "' in " + path + " at line " + line);
            }
            types.add(trait);
         }
      }
      
      public void compile(Scope scope, Type type) throws Exception {
         List<Constraint> types = type.getTypes();
         
         for(Constraint base : types) {
            try {
               verifier.verify(scope, base);
            } catch(Exception e) {
               throw new InternalStateException("Invalid constraint for '" + type + "' in " + path + " at line " + line, e); 
            }
         }
         for(int i = 0; i < traits.length; i++) {
            Constraint trait = traits[i];
            Type match = trait.getType(scope);
            Class real = match.getType();
            
            if(real != null) {
               if(!real.isInterface()) {
                  throw new InternalStateException("Invalid trait '" + trait + "' in " + path + " at line " + line);
               }
            }
         }
      }
   }
}