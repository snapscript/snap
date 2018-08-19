package org.snapscript.tree.reference;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Entity;
import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.State;
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
   
   private static class CompileResult extends TypeNavigation {

      private final TypeExtractor extractor;
      private final Module source;
      private final String name;
   
      public CompileResult(TypeExtractor extractor, Module source, String name) {
         this.extractor = extractor;
         this.source = source;
         this.name = name;
      }   
      
      @Override
      public String qualify(Scope scope, String left) throws Exception {
         if(left != null) {
            return left + '$' +name;
         }
         return name;
      }
      
      @Override
      public Value evaluate(Scope scope, Value left) throws Exception {
         Object object = left.getValue();
         
         if(object != null) {
            if(Module.class.isInstance(object)) {
               return create(scope, (Module)object);
            }
            if(Type.class.isInstance(object)) {
               return create(scope, (Type)object);
            }
            throw new InternalStateException("No type found for '" + name + "' in '" + source + "'"); // class not found
         }
         return create(scope);
      }
      
      private Value create(Scope scope) throws Exception {
         Module parent = scope.getModule();
         Entity result = parent.getType(name);
         Type type = scope.getType();

         if(result == null) {
            result = source.getModule(name); 
         }
         if(result == null && type != null) {
            result = extractor.getType(type, name);
         }
         if(result == null) {
            State state = scope.getState();
            Constraint constraint = state.getConstraint(name);
            
            if(constraint == null) {                         
               throw new InternalStateException("No type found for '" + name + "' in '" + source + "'"); // class not found
            }
            return Local.getConstant(constraint, name);
         }
         return Local.getConstant(result, name);
      }
      
      private Value create(Scope scope, Module module) throws Exception {
         Entity result = module.getType(name);
         
         if(result == null) {
            throw new InternalStateException("No type found for '" + name + "' in '" + module + "'"); // class not found
         }
         return Local.getConstant(result, name);
      }
      
      private Value create(Scope scope, Type type) throws Exception {
         Module module = type.getModule();
         String parent = type.getName();
         Entity result = module.getType(parent + "$"+name);
         
         if(result == null) {
            throw new InternalStateException("No type found for '" + parent + "." + name + "' in '" + module + "'"); // class not found
         }
         return Local.getConstant(result, parent + "$"+name);
      }
   }
}