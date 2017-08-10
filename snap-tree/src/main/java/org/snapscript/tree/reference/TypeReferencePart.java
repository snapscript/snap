package org.snapscript.tree.reference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.NameReference;

public class TypeReferencePart implements Compilation {

   private final Evaluation type;  
   
   public TypeReferencePart(Evaluation type) {
      this.type = type;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      return new CompileResult(type, module);
   }
   
   private static class CompileResult implements Evaluation {
      
      private final TypeTraverser traverser;
      private final NameReference reference;
      private final Module source;
   
      public CompileResult(Evaluation type, Module source) {
         this.reference = new NameReference(type);
         this.traverser = new TypeTraverser();
         this.source = source;
      }   
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         if(left != null) {
            String name = reference.getName(scope);
            
            if(Module.class.isInstance(left)) {
               return create(scope, (Module)left);
            }
            if(Type.class.isInstance(left)) {
               return create(scope, (Type)left);
            }
            throw new InternalStateException("No type found for '" + name + "' in '" + source + "'"); // class not found
         }
         return create(scope, source);
      }
      
      private Value create(Scope scope, Module module) throws Exception {
         String name = reference.getName(scope);
         Object result = module.getModule(name);
         Type type = scope.getType();
         
         if(result == null) {
            result = module.getType(name); 
         }
         if(result == null && type != null) {
            result = traverser.findEnclosing(type, name);
         }
         if(result == null) {
            throw new InternalStateException("No type found for '" + name + "' in '" + module + "'"); // class not found
         }
         return ValueType.getTransient(result);
      }
      
      private Value create(Scope scope, Type type) throws Exception {
         String name = reference.getName(scope);
         Module module = type.getModule();
         String parent = type.getName();
         Type result = module.getType(parent + "$"+name);
         
         if(result == null) {
            throw new InternalStateException("No type found for '" + parent + "." + name + "' in '" + module + "'"); // class not found
         }
         return ValueType.getTransient(result);
      }
   }
}