package org.snapscript.tree.reference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Evaluation;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;

public class GenericReference implements Compilation {
   
   private final GenericArgumentList list;
   private final TypeReference type;
   
   public GenericReference(TypeReference type) {
      this(type, null);
   }
   
   public GenericReference(TypeReference type, GenericArgumentList list) {
      this.type = type;
      this.list = list;
   }

   @Override
   public Evaluation compile(Module module, Path path, int line) throws Exception {
      return new CompileResult(type, list, path, line);
   }
   
   private static class CompileResult extends ConstraintReference { 

      private final GenericDeclaration declaration;
      
      public CompileResult(TypeReference type, GenericArgumentList list, Path path, int line) {
         this.declaration = new GenericDeclaration(type, list, path, line);
      }

      @Override
      protected ConstraintValue create(Scope scope) throws Exception {
         Value value = declaration.declare(scope);
         Module module = scope.getModule();
         
         return new ConstraintValue(value, value, module);
      }      
   }
}