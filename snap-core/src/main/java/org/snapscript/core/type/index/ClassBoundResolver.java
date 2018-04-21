package org.snapscript.core.type.index;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

import org.snapscript.core.PrimitivePromoter;

public class ClassBoundResolver {
   
   private final PrimitivePromoter promoter;
   private final ClassBound[] empty;
   
   public ClassBoundResolver(){
      this.promoter = new PrimitivePromoter();
      this.empty = new ClassBound[]{};
   }
   
   public ClassBound[] resolveType(Class type) {
      TypeVariable[] variables = type.getTypeParameters();
      
      if(variables.length > 0) {
         ClassBound[] bounds = new ClassBound[variables.length];
         
         for(int i = 0; i < variables.length; i++) {
            TypeVariable variable = variables[i];            
            ClassBound bound = resolveBound(variable);
            
            bounds[i] = bound;
         }
         return bounds;
      }
      return empty;
   }  
   
   public ClassBound resolveField(Field field) {
      Type type = field.getGenericType();
      Class actual = field.getType();
      
      if(TypeVariable.class.isInstance(type)) {
         return resolveBound((TypeVariable)type);
      }      
      return new ClassBound(actual);
   }
   
   public ClassBound resolveMethod(Method method) {
      Type type = method.getGenericReturnType();
      Class returns = method.getReturnType();
      
      if(TypeVariable.class.isInstance(type)) {
         return resolveBound((TypeVariable)type);
      }      
      return new ClassBound(returns);
   }
   
   public ClassBound[] resolveParameters(Method method) {
      Type[] parameters = method.getGenericParameterTypes(); 
      Class[] types = method.getParameterTypes();
      
      if(types.length > 0) {
         ClassBound[] bounds = new ClassBound[types.length];
         
         for(int i = 0; i < parameters.length; i++) {
            Type parameter = parameters[i];
            Class type = types[i];
         
            if(TypeVariable.class.isInstance(parameter)) {
               bounds[i] = resolveBound((TypeVariable)parameter);
            } else {
               bounds[i] = new ClassBound(type);
            }
         }
         return bounds;
      }
      return empty;
   }
   
   public ClassBound[] resolveParameters(Constructor constructor) {
      Type[] parameters = constructor.getGenericParameterTypes();
      Class[] types = constructor.getParameterTypes();

      if(types.length > 0) {
         ClassBound[] bounds = new ClassBound[types.length];
         
         for(int i = 1; i <= parameters.length; i++) {
            Type parameter = parameters[parameters.length - i];
            Class type = types[types.length - i];
         
            if(TypeVariable.class.isInstance(parameter)) {
               bounds[bounds.length - i] = resolveBound((TypeVariable)parameter);
            } else {
               bounds[bounds.length - i] = new ClassBound(type);
            }
         }
         int difference = types.length - parameters.length; // inner class constructor 
         
         for(int i = 0; i < difference; i++) {
            ClassBound bound = bounds[i];
            Class type = types[i];
            
            if(bound == null) {
               bounds[i] = new ClassBound(type);
            }
         }
         return bounds;
      }
      return empty;
   }
   
   private ClassBound resolveBound(TypeVariable variable) {
      Type[] bounds = variable.getBounds();
      
      if(bounds.length > 0) {
         String name = variable.getName();
         
         for(int i = 0; i < bounds.length; i++) {
            Type bound = bounds[i];
            
            if(Class.class.isInstance(bound)) {
               Class first = (Class)bound; // match first only
               Class match = promoter.promote(first); // unlikely
               
               return new ClassBound(match, name);
            }
         }
         return new ClassBound(Object.class, name);
      }
      return new ClassBound(Object.class);
   }
}
