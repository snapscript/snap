package org.snapscript.core.variable.bind;

import static org.snapscript.core.ModifierType.CONSTANT;
import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleScopeBinder;
import org.snapscript.core.property.ConstantProperty;
import org.snapscript.core.property.MapProperty;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class VariableFinder {
   
   private final ModuleScopeBinder binder;
   
   public VariableFinder() {
      this.binder = new ModuleScopeBinder();
   }
   
   public Object findTypes(Scope scope, String name) {
      Scope current = binder.bind(scope); // this could be slow
      Module module = current.getModule();
      Type type = module.getType(name); // this is super slow if a variable is referenced
      Type parent = current.getType();
      
      if(type == null) {
         Object result = module.getModule(name);
         
         if(result == null && parent != null) {
            Context context = module.getContext();
            TypeExtractor extractor = context.getExtractor();
            
            return extractor.getType(parent, name);
         }
         return result;
      }
      return type;
   }
   
   public Property findAnyFromObject(Scope scope, Object left, String name) {
      Class type = left.getClass();
      Module module = scope.getModule();
      Type source = module.getType(type);
      
      if(source != null) {
         Property match = findAnyFromType(scope, source, name);
         
         if(match != null) {
            return match;
         }
      }
      return null;
   }
   
   public Property findPropertyFromObject(Scope scope, Object left, String name) {
      Class type = left.getClass();
      Module module = scope.getModule();
      Type source = module.getType(type);
      
      if(source != null) {
         Property match = findPropertyFromType(scope, source, name);
         
         if(match != null) {
            return match;
         }
      }
      return null;
   }
   
   public Property findAnyFromType(Scope scope, Type type, String name) {
      Property match = findPropertyFromType(scope, type, name);
      
      if(match == null) {
         return findConstantFromType(scope, type, name);
      }
      return match;
   }
   
   public Property findPropertyFromType(Scope scope, Type type, String name) {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> list = extractor.getTypes(type);
      
      for(Type base : list) {
         List<Property> properties = base.getProperties();
         
         for(Property property : properties){
            String field = property.getName();
            
            if(field.equals(name)) {
               return property;
            }
         }
      }
      return null;
   }
   
   public Property findConstantFromType(Scope scope, Type type, String name) {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> list = extractor.getTypes(type);
      
      for(Type base : list) {
         Scope outer = base.getScope();
         Object value = findTypes(outer, name); // this is really slow
   
         if(value != null) {
            return new ConstantProperty(name, base, NONE, value, CONSTANT.mask);
         }
      }
      return null;
   }
   
   public Property findAnyFromModule(Scope scope, Module left, String name) {
      List<Property> properties = left.getProperties();
      
      for(Property property : properties){
         String field = property.getName();
         
         if(field.equals(name)) {
            return property;
         }
      }
      return findAnyFromObject(scope, left, name);
   }   

   public Property findPropertyFromMap(Scope scope, Type left, String name) {
      Property property = findPropertyFromType(scope, left, name);
   
      if(property == null) {
         return new MapProperty(name, left, PUBLIC.mask);
      }
      return property;
   }
   
   public Property findPropertyFromMap(Scope scope, Map left, String name) {
      Property property = findPropertyFromObject(scope, left, name);
   
      if(property == null) {
         Module module = scope.getModule();
         Class type = left.getClass();
         String alias = type.getName();
         Type source = module.getType(alias);
         
         return new MapProperty(name, source, PUBLIC.mask);
      }
      return property;
   }
}