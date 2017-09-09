package org.snapscript.tree.reference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Scope;
import org.snapscript.core.Type;
import org.snapscript.core.TypeExtractor;
import org.snapscript.core.TypeTraverser;
import org.snapscript.core.Value;
import org.snapscript.tree.NameReference;

public class TypeReferencePart implements Compilation {

   private final Evaluation type;  
   
   public TypeReferencePart(Evaluation type) {
      this.type = type;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      
      return new CompileResult(extractor, type, module);
   }
   
   private static class CompileResult extends Evaluation {

      private final TypeExtractor extractor;
      private final NameReference reference;
      private final Module source;
   
      public CompileResult(TypeExtractor extractor, Evaluation type, Module source) {
         this.reference = new NameReference(type);
         this.extractor = extractor;
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
         Module parent = scope.getModule();
         Object result = parent.getType(name);
         Type type = scope.getType();
         
         if(result == null && parent != module) {
            result = module.getType(name); // find classes declared in the Scope module
         }
         if(result == null) {
            result = module.getModule(name); 
         }
         if(result == null && type != null) {
            result = extractor.getType(type, name);
         }
         if(result == null) {
            throw new InternalStateException("No type found for '" + name + "' in '" + module + "'"); // class not found
         }
         return Value.getTransient(result);
      }
      
      private Value create(Scope scope, Type type) throws Exception {
         String name = reference.getName(scope);
         Module module = type.getModule();
         String parent = type.getName();
         Type result = module.getType(parent + "$"+name);
         
         if(result == null) {
            throw new InternalStateException("No type found for '" + parent + "." + name + "' in '" + module + "'"); // class not found
         }
         return Value.getTransient(result);
      }
   }
}