package org.snapscript.tree.variable;

import static org.snapscript.core.ModifierType.PUBLIC;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.ModuleScopeBinder;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.property.ConstantPropertyBuilder;
import org.snapscript.core.property.MapProperty;
import org.snapscript.core.property.Property;

public class ConstantResolver {
   
   private final ConstantPropertyBuilder builder;
   private final ModuleScopeBinder binder;
   
   public ConstantResolver() {
      this.builder = new ConstantPropertyBuilder();
      this.binder = new ModuleScopeBinder();
   }
   
   public Object resolve(Scope scope, String name) {
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
   
   public Property matchFromObject(Scope scope, Object left, String name) {
      Class type = left.getClass();
      Module module = scope.getModule();
      Type source = module.getType(type);
      
      if(source != null) {
         Property match = matchFromType(scope, source, name);
         
         if(match != null) {
            return match;
         }
      }
      return null;
   }
   
   public Property matchPropertyFromObject(Scope scope, Object left, String name) {
      Class type = left.getClass();
      Module module = scope.getModule();
      Type source = module.getType(type);
      
      if(source != null) {
         Property match = matchPropertyFromType(scope, source, name);
         
         if(match != null) {
            return match;
         }
      }
      return null;
   }
   
   public Property matchFromType(Scope scope, Type type, String name) {
      Property match = matchPropertyFromType(scope, type, name);
      
      if(match != null) {
         return match;
      }
      return matchConstantFromType(scope, type, name);
   }
   
   public Property matchPropertyFromType(Scope scope, Type type, String name) {
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
   
   public Property matchConstantFromType(Scope scope, Type type, String name) {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> list = extractor.getTypes(type);
      
      for(Type base : list) {
         Scope outer = base.getScope();
         Object value = resolve(outer, name); // this is really slow
   
         if(value != null) {
            return builder.createConstant(name, value);
         }
      }
      return null;
   }
   
   public Property matchFromModule(Scope scope, Module left, String name) {
      List<Property> properties = left.getProperties();
      
      for(Property property : properties){
         String field = property.getName();
         
         if(field.equals(name)) {
            return property;
         }
      }
      return matchFromObject(scope, left, name);
   }
   
   public Property matchFromMap(Scope scope, Type left, String name) {
      Property property = matchPropertyFromType(scope, left, name);
   
      if(property == null) {
         return new MapProperty(name, left, PUBLIC.mask);
      }
      return property;
   }
   
   public Property matchFromMap(Scope scope, Map left, String name) {
      Property property = matchPropertyFromObject(scope, left, name);
   
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