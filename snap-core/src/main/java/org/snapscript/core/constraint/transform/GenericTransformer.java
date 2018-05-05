package org.snapscript.core.constraint.transform;

import java.util.List;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class GenericTransformer {
   
   private final GenericParameterIndexer indexer;
   private final GenericTransform[] empty;
   private final GenericCache cache;
   private final TypeExtractor extractor;
   
   public GenericTransformer(TypeExtractor extractor){
      this.indexer = new GenericParameterIndexer();
      this.empty = new GenericTransform[]{};
      this.cache = new GenericCache();
      this.extractor = extractor;
   }

   public GenericTransform transform(Type constraint, Type require) {
      GenericTransformTable table = cache.fetch(constraint);
      GenericTransform transform = table.fetch(require);
      
      if(transform == null) {
         transform = create(constraint, require);
         table.cache(require, transform);
      }
      return transform;
   }
   
   private GenericTransform create(Type constraint, Type require) {
      List<Constraint> constraints = require.getConstraints();
      GenericIndex index = indexer.index(require);      
      
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
         GenericTransform[] transforms = new GenericTransform[count];
         
         for(int i = 0; i < count; i++){
            Constraint base = path.get(i);      
            Type actual = base.getType(scope);
            
            transforms[i] = create(original, actual);
            original = base;
         }
         return new TransformationList(transforms);
      }
      return new EmptyTransform(require);
   }
   
   private GenericTransform create(Constraint constraint, Type require) {
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
   
   private GenericTransform create(Constraint constraint, Constraint require, Type destination) {
      Scope scope = destination.getScope();
      Type origin = constraint.getType(scope);
      GenericIndex index = indexer.index(origin);
      List<Constraint> generics = require.getGenerics(scope); // extends Map<T, Integer>
      int count = generics.size();
      
      if(count > 0) {
         GenericTransform[] transforms = new GenericTransform[count];
         
         for(int i = 0; i < count; i++){
            Constraint generic = generics.get(i);
            String name = generic.getName(scope);
            Constraint parameter = index.getType(constraint, name);
            
            if(parameter == null) {
               transforms[i] = new TypeTransform(constraint, generic, index); // its already got a class
            }else {
               transforms[i] = new IndexTransform(index, name);
            }
         }
         return new ConstraintTransform(destination, index, transforms);
      }
      return new ConstraintTransform(destination, index, empty);
   }
}
