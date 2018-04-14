package org.snapscript.compile.validate;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;
import java.util.Set;

import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.index.FunctionIndexer;
import org.snapscript.core.module.Module;
import org.snapscript.core.property.Property;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;

public class TypeValidator {
   
   private static final String[] PROPERTIES = { TYPE_THIS, TYPE_CLASS };
   private static final String[] TYPES = { ANY_TYPE };
   
   private final PropertyValidator properties;
   private final FunctionValidator functions;
   private final TypeExtractor extractor;
   
   public TypeValidator(ConstraintMatcher matcher, TypeExtractor extractor, FunctionIndexer indexer) {
      this.functions = new FunctionValidator(matcher, extractor, indexer);
      this.properties = new PropertyValidator(matcher);
      this.extractor = extractor;
   }
   
   public void validate(Type type) throws Exception {
      Class real = type.getType();
      
      if(real == null) {
         validateModule(type);
         validateHierarchy(type);
         validateFunctions(type);
         validateProperties(type);
      }
   }
   
   private void validateModule(Type type) throws Exception {
      Module module = type.getModule();
      
      if(module == null) {
         throw new ValidateException("Type '" + type + "' has no module");
      }
   }
   
   private void validateHierarchy(Type type) throws Exception {
      Set<Type> types = extractor.getTypes(type);
      
      for(int i = 0; i < TYPES.length; i++) {
         String require = TYPES[i];
         int matches = 0;
         
         for(Type base : types) {
            String name = base.getName();
            
            if(name.equals(require)) {
               matches++;
            }
         }
         if(matches == 0) {
            throw new ValidateException("Type '" + type + "' has an invalid hierarchy");
         }
      }
   }

   private void validateProperties(Type type) throws Exception {
      List<Property> list = type.getProperties();
      
      for(int i = 0; i < PROPERTIES.length; i++) {
         String require = PROPERTIES[i];
         int matches = 0;
         
         for(Property property : list) {
            String name = property.getName();
            
            if(name.equals(require)) {
               matches++;
            }
            properties.validate(property);
         }
         if(matches == 0) {
            throw new ValidateException("Type '" + type + "' has no property '" + require + "'");
         }
      }
   }

   private void validateFunctions(Type type) throws Exception {
      List<Function> list = type.getFunctions();
         
      for(Function function : list) {
         functions.validate(function);
      }
   }
}