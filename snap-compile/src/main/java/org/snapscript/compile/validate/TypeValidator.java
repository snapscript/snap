package org.snapscript.compile.validate;

import static org.snapscript.core.Reserved.ANY_TYPE;
import static org.snapscript.core.Reserved.TYPE_CLASS;
import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;
import static org.snapscript.core.Reserved.TYPE_SUPER;
import static org.snapscript.core.Reserved.TYPE_THIS;

import java.util.List;
import java.util.Set;

import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Type;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.function.Function;
import org.snapscript.core.property.Property;

public class TypeValidator {
   
   private static final String[] PROPERTIES = { TYPE_THIS, TYPE_SUPER, TYPE_CLASS };
   private static final String[] TYPES = { ANY_TYPE };
   
   private final FunctionValidator validator;
   private final TypeTraverser traverser;
   
   public TypeValidator(ConstraintMatcher matcher) {
      this.traverser = new TypeTraverser();
      this.validator = new FunctionValidator(matcher, traverser);
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
         throw new InternalStateException("Type '" + type + "' has no module");
      }
   }
   
   private void validateHierarchy(Type type) throws Exception {
      Set<Type> types = traverser.traverse(type);
      
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
            Module module = type.getModule();
            String resource = module.getName();
            String name = type.getName();
            
            throw new InternalStateException("Type '" + resource + "." + name + "' not defined");
         }
      }
   }

   private void validateProperties(Type type) throws Exception {
      List<Property> properties = type.getProperties();
      
      for(int i = 0; i < PROPERTIES.length; i++) {
         String require = PROPERTIES[i];
         int matches = 0;
         
         for(Property property : properties) {
            String name = property.getName();
            
            if(name.equals(require)) {
               matches++;
            }
         }
         if(matches == 0) {
            Module module = type.getModule();
            String resource = module.getName();
            String name = type.getName();
            
            throw new InternalStateException("Type '" + resource + "." + name + "' have property '" + require + "'");
         }
      }
   }

   private void validateFunctions(Type type) throws Exception {
      List<Function> functions = type.getFunctions();
         
      for(Function function : functions) {
         String name = function.getName();
         
         if(!name.equals(TYPE_CONSTRUCTOR)) {
            validator.validate(function);
         }
      }
   }
}
