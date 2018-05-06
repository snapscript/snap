package org.snapscript.core.constraint.transform;

import java.util.List;

import org.snapscript.common.Cache;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.type.TypeTree;

public class ConstraintTransformer {
   
   private final TypeTree<Integer, ConstraintTransform> tree;
   private final ConstraintIndexer indexer;
   private final TypeExtractor extractor;
   
   public ConstraintTransformer(TypeExtractor extractor){
      this.tree = new TypeTree<Integer, ConstraintTransform>();
      this.indexer = new ConstraintIndexer();
      this.extractor = extractor;
   }

   public ConstraintTransform transform(Type constraint, Type require) {
      int index = require.getOrder();
      Cache<Integer, ConstraintTransform> cache = tree.get(constraint);
      ConstraintTransform transform = cache.fetch(index);
      
      if(transform == null) {
         transform = create(constraint, require);
         cache.cache(index, transform);
      }
      return transform;
   }
   
   private ConstraintTransform create(Type constraint, Type require) {
      List<Constraint> constraints = require.getConstraints();
      ConstraintIndex index = indexer.index(require);      
      
      if(constraint.equals(require)) {
         return new IdentityTransform(index);
      }
      if(!constraints.isEmpty()) {
         List<Constraint> path = extractor.getTypes(constraint, require);
         Constraint original = Constraint.getConstraint(constraint);
         Scope scope = constraint.getScope();
         int count = path.size();
         
         if(count <= 0) {
            throw new InternalStateException("Type '" + require + "' not in hierarchy of '" + constraint +"'");
         }
         ConstraintTransform[] transforms = new ConstraintTransform[count];
         
         for(int i = 0; i < count; i++){
            Constraint base = path.get(i);      
            Type actual = base.getType(scope);
            
            transforms[i] = create(original, actual);
            original = base;
         }
         return new ChainTransform(transforms);
      }
      return new EmptyTransform(require);
   }
   
   private ConstraintTransform create(Constraint constraint, Type require) {
      Scope scope = require.getScope();
      Type actual = constraint.getType(scope);
      List<Constraint> hierarchy = actual.getTypes();
      
      for(Constraint base : hierarchy) {
         Type type = base.getType(scope);
         
         if(type == require) { // here we know how require was declared in the type
            return create(constraint, base, type);
         }
      } 
      throw new InternalStateException("Type '" + require + "' not in hierarchy of '" + actual +"'");
   }
   
   private ConstraintTransform create(Constraint constraint, Constraint require, Type destination) {
      Scope scope = destination.getScope();
      Type origin = constraint.getType(scope);
      ConstraintIndex originIndex = indexer.index(origin);
      ConstraintIndex requireIndex = indexer.index(destination);
      List<Constraint> generics = require.getGenerics(scope); // extends Map<T, Integer>
      int count = generics.size();
      
      if(count > 0) {
         ConstraintTransform[] transforms = new ConstraintTransform[count];
         
         for(int i = 0; i < count; i++){
            Constraint generic = generics.get(i);
            String name = generic.getName(scope);
            Constraint parameter = originIndex.resolve(constraint, name);
            
            if(parameter == null) {
               transforms[i] = new TypeTransform(generic, originIndex); // its already got a class
            }else {
               transforms[i] = new GenericParameterTransform(originIndex, name);
            }
         }
         return new GenericTransform(destination, requireIndex, transforms);
      }
      return new TypeTransform(require, requireIndex);
   }
}
