package org.snapscript.tree;

import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;

public class ImportStatic implements Compilation {   
   
   private final Qualifier qualifier;    
   
   public ImportStatic(Qualifier qualifier) {
      this.qualifier = qualifier;
   }

   @Override
   public Object compile(Module module, int line) throws Exception {
      String location = qualifier.getLocation();
      String target = qualifier.getTarget();
      
      return new CompileResult(location, target);
   }
   
   private static class CompileResult extends Statement {
      
      private final String location;
      private final String target;
      
      public CompileResult(String location, String target) {
         this.location = location;
         this.target = target;
      }
      
      @Override
      public Result compile(Scope scope) throws Exception {
         Module module = scope.getModule();
         Type type = module.getType(location); // this is a type name
         List<Function> methods = type.getFunctions();
         List<Function> functions = module.getFunctions();
         
         for(Function method : methods){
            int modifiers = method.getModifiers();
            
            if(ModifierType.isStatic(modifiers) && ModifierType.isPublic(modifiers)){
               String name = method.getName();
               
               if(target == null || target.equals(name)) {
                  functions.add(method);
               }
            }
         }
         return ResultType.getNormal();
      }
      
   }
}
