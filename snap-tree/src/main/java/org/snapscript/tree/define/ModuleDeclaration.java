package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.STATIC;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierData;
import org.snapscript.tree.ModifierList;

public class ModuleDeclaration implements Compilation {
   
   private final Statement declaration;   
   
   public ModuleDeclaration(ModifierList modifiers, ModuleProperty... properties) {
      this.declaration = new CompileResult(modifiers, properties);     
   }
   
   @Override
   public Statement compile(Module module, Path path, int line) throws Exception {
      Context context = module.getContext();
      ErrorHandler handler = context.getHandler();
      TraceInterceptor interceptor = context.getInterceptor();
      Trace trace = Trace.getNormal(module, path, line);
      
      return new TraceStatement(interceptor, handler, declaration, trace);
   }
   
   private static class CompileResult extends Statement {

      private final ModuleProperty[] properties;
      private final ModifierData modifiers;
      
      public CompileResult(ModifierList modifiers, ModuleProperty... properties) {
         this.modifiers = new ModifierChecker(modifiers);
         this.properties = properties;
      }  
      
      @Override
      public Result execute(Scope scope) throws Exception {
         int mask = modifiers.getModifiers();
         
         for(ModuleProperty declaration : properties) {
            declaration.create(scope, mask | STATIC.mask); 
         }
         return Result.getNormal();
      }
   }
}