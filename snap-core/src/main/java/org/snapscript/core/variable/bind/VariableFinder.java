package org.snapscript.core.variable.bind;

import static org.snapscript.core.constraint.Constraint.NONE;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.snapscript.core.Context;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.ModuleScopeBinder;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class VariableFinder {
   
   private final ModuleScopeBinder binder;
   
   public VariableFinder() {
      this.binder = new ModuleScopeBinder();
   }
   
   public VariableResult findAll(Scope scope, Object left, String name) {
      Class type = left.getClass();
      Module module = scope.getModule();
      Type source = module.getType(type);
      
      if(source != null) {
         VariableResult match = findAll(scope, source, name);
         
         if(match != null) {
            return match;
         }
      }
      return null;
   }
   
   public VariableResult findAll(Scope scope, Type type, String name) {
      VariableResult match = findProperty(scope, type, name);
      
      if(match == null) {
         return findConstant(scope, type, name);
      }
      return match;
   }
   
   public VariableResult findAll(Scope scope, Module left, String name) {
      List<Property> properties = left.getProperties();
      
      for(Property property : properties){
         String field = property.getName();
         
         if(field.equals(name)) {
            return new PropertyResult(property, left, name);
         }
      }
      return findAll(scope, (Object)left, name);
   }
   
   public VariableResult findProperty(Scope scope, Object left, String name) {
      Class type = left.getClass();
      Module module = scope.getModule();
      Type source = module.getType(type);
      
      if(source != null) {
         VariableResult match = findProperty(scope, source, name);
         
         if(match != null) {
            return match;
         }
      }
      return null;
   }
   
   public VariableResult findProperty(Scope scope, Type type, String name) {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> list = extractor.getTypes(type);
      
      for(Type base : list) {
         List<Property> properties = base.getProperties();
         
         for(Property property : properties){
            String field = property.getName();
            
            if(field.equals(name)) {
               return new PropertyResult(property, base, name);
            }
         }
      }
      return null;
   }
   
   public VariableResult findProperty(Scope scope, Map left, String name) {
      VariableResult property = findProperty(scope, (Object)left, name);
   
      if(property == null) {
         return new MapResult(name, NONE);
      }
      return property;
   }
   
   public VariableResult findConstant(Scope scope, Type type, String name) {
      Module module = scope.getModule();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      Set<Type> list = extractor.getTypes(type);
      
      for(Type base : list) {
         Scope outer = base.getScope();
         Object value = findTypes(outer, name); // this is really slow
   
         if(value != null) {
            return new ConstantResult(value, NONE);
         }
      }
      return null;
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
}