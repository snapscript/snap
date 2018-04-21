package org.snapscript.tree.reference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.NameReference;

public class TypeReferencePart implements Compilation {

   private final NameReference reference;
   
   public TypeReferencePart(Evaluation type) {
      this.reference = new NameReference(type);
   }

   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      Scope scope = module.getScope();
      Context context = module.getContext();
      TypeExtractor extractor = context.getExtractor();
      String name = reference.getName(scope);
      
      return new CompileResult(extractor, module, name);
   }
   
   private static class CompileResult extends Evaluation {

      private final TypeExtractor extractor;
      private final Module source;
      private final String name;
   
      public CompileResult(TypeExtractor extractor, Module source, String name) {
         this.extractor = extractor;
         this.source = source;
         this.name = name;
      }   
      
      @Override
      public Value evaluate(Scope scope, Object left) throws Exception {
         if(left != null) {
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
         return Local.getConstant(result, name);
      }
      
      private Value create(Scope scope, Type type) throws Exception {
         Module module = type.getModule();
         String parent = type.getName();
         Type result = module.getType(parent + "$"+name);
         
         if(result == null) {
            throw new InternalStateException("No type found for '" + parent + "." + name + "' in '" + module + "'"); // class not found
         }
         return Local.getConstant(result, parent + "$"+name);
      }
   }
}