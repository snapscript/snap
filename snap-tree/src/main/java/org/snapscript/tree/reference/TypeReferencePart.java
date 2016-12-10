package org.snapscript.tree.reference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.NameExtractor;

public class TypeReferencePart implements Evaluation {

   private final NameExtractor extractor;

   public TypeReferencePart(Evaluation type) {
      this.extractor = new NameExtractor(type);
   }   
   
   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      Module module = scope.getModule();
      
      if(left != null) {
         String name = extractor.extract(scope);
         
         if(Module.class.isInstance(left)) {
            return create(scope, (Module)left);
         }
         if(Type.class.isInstance(left)) {
            return create(scope, (Type)left);
         }
         throw new InternalStateException("No type found for '" + name + "' in '" + module + "'"); // class not found
      }
      return create(scope, module);
   }
   
   private Value create(Scope scope, Module module) throws Exception {
      String name = extractor.extract(scope);
      Object result = module.getModule(name);
      Type parent = scope.getType();
      
      if(result == null) {
         result = module.getType(name); 
      }
      while(result == null && parent != null){ // search outer classes
         String prefix = parent.getName();
         
         if(prefix != null) {
            result = module.getType(prefix + "$"+name);
         }
         parent = parent.getOuter();
      }
      if(result == null) {
         throw new InternalStateException("No type found for '" + name + "' in '" + module + "'"); // class not found
      }
      return ValueType.getTransient(result);
   }
   
   private Value create(Scope scope, Type type) throws Exception {
      String name = extractor.extract(scope);
      Module module = type.getModule();
      String parent = type.getName();
      Type result = module.getType(parent + "$"+name);
      
      if(result == null) {
         throw new InternalStateException("No type found for '" + parent + "." + name + "' in '" + module + "'"); // class not found
      }
      return ValueType.getTransient(result);
   }
}
