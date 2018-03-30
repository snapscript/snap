package org.snapscript.tree.variable;

import static org.snapscript.core.ModifierType.PUBLIC;
import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.Bug;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleScopeBinder;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.property.ConstantPropertyBuilder;
import org.snapscript.core.property.MapProperty;
import org.snapscript.core.property.Property;

public class VariableFinder {
   
   private final ConstantPropertyBuilder builder;
   private final ModuleScopeBinder binder;
   
   public VariableFinder() {
      this.builder = new ConstantPropertyBuilder();
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
      
      if(match != null) {
         return match;
      }
      return findConstantFromType(scope, type, name);
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
   
   @Bug("what about constraints?")
   public Property findConstantFromType(Scope scope, Type type, String name) {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> list = extractor.getTypes(type);
      
      for(Type base : list) {
         Scope outer = base.getScope();
         Object value = findTypes(outer, name); // this is really slow
   
         if(value != null) {
            return builder.createConstant(name, value, null, NONE);
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