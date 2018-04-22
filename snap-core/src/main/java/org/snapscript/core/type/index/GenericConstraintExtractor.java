package org.snapscript.core.type.index;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.DeclarationConstraint;
import org.snapscript.core.type.Type;

public class GenericConstraintExtractor {
   
   private final ClassConstraintMapper mapper;
   private final ClassBoundResolver resolver;
   private final TypeIndexer indexer;
   private final Constraint[] empty;
   
   public GenericConstraintExtractor(TypeIndexer indexer){
      this.mapper = new ClassConstraintMapper(indexer);
      this.resolver = new ClassBoundResolver();
      this.empty = new Constraint[]{};
      this.indexer = indexer;
   }

   public Constraint extractType(Class type, String name) {
      try {         
         if(type != null) {
            Type match = indexer.loadType(type);            
            
            if(match != null) {
               return new DeclarationConstraint(match, name);
            }
         }
         return new DeclarationConstraint(null, name);
      } catch(Exception e) {
         throw new InternalStateException("Could not create constraint for " + type, e);
      }
   }

   public Constraint extractField(Field field,int modifiers) {
      try {
         ClassBound bound = resolver.resolveField(field);
         String name = bound.getName();
         Class actual = bound.getBound();
         Type match = mapper.map(actual);  
         
         return new DeclarationConstraint(match, name, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create constraint for " + field, e);
      }
   }
   
   public Constraint extractMethod(Method method,int modifiers) {
      try {
         ClassBound bound = resolver.resolveMethod(method);
         String name = bound.getName();
         Class actual = bound.getBound();
         Type match = mapper.map(actual);  
         
         return new DeclarationConstraint(match, name, modifiers);
      } catch(Exception e) {
         throw new InternalStateException("Could not create constraint for " + method, e);
      }
   }
   
   public Constraint[] extractParameters(Method method) {
      try {
         ClassBound[] bounds = resolver.resolveParameters(method);
         
         if(bounds.length > 0) {
            Constraint[] constraints = new Constraint[bounds.length];
            
            for(int i = 0; i < bounds.length; i++) {
               ClassBound bound = bounds[i];
               Class type = bound.getBound();
               String name = bound.getName();
                  
               constraints[i] = extractType(type, name);                  
            }
            return constraints;
         }
         return empty;
      } catch(Exception e) {
         throw new InternalStateException("Could not create parameter constraints for " + method, e);
      }
   }
   
   public Constraint[] extractParameters(Constructor constructor) {
      try {
         ClassBound[] bounds = resolver.resolveParameters(constructor);
         
         if(bounds.length > 0) {
            Constraint[] constraints = new Constraint[bounds.length];
            
            for(int i = 0; i < bounds.length; i++) {
               ClassBound bound = bounds[i];
               Class type = bound.getBound();
               String name = bound.getName();
                  
               constraints[i] = extractType(type, name);                  
            }
            return constraints;
         }
         return empty;
      } catch(Exception e) {
         throw new InternalStateException("Could not create parameter constraints for " + constructor, e);
      }
   }
}
