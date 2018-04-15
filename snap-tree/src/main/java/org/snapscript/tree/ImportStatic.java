package org.snapscript.tree;

import static org.snapscript.core.result.Result.NORMAL;

import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.Execution;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.ModifierType;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.NameBuilder;
import org.snapscript.core.type.Type;

public class ImportStatic implements Compilation {   
   
   private final Qualifier qualifier;    
   
   public ImportStatic(Qualifier qualifier) {
      this.qualifier = qualifier;
   }

   @Override
   public Object compile(Module module, Path path, int line) throws Exception {
      String location = qualifier.getLocation();
      String target = qualifier.getTarget();
      String name = qualifier.getName();
      
      return new CompileResult(location, target, name);
   }
   
   private static class CompileResult extends Statement {
      
      private final Execution execution;
      private final NameBuilder builder;      
      private final String location;
      private final String target;
      private final String prefix;
      
      public CompileResult(String location, String target, String prefix) {
         this.execution = new NoExecution(NORMAL);
         this.builder = new NameBuilder();        
         this.location = location;
         this.target = target;
         this.prefix = prefix;
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         Module module = scope.getModule();
         String parent = builder.createFullName(location, target);
         Type type = module.getType(parent); // this is a type name
         
         if(type == null) {
            throw new InternalStateException("Could not import '" + parent + "'");
         }
         List<Function> methods = type.getFunctions();
         List<Function> functions = module.getFunctions();
         
         for(Function method : methods){
            int modifiers = method.getModifiers();
            
            if(ModifierType.isStatic(modifiers) && ModifierType.isPublic(modifiers)){
               String name = method.getName();
               
               if(prefix == null || prefix.equals(name)) {
                  functions.add(method);
               }
            }
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         return execution;
      }
      
   }
}