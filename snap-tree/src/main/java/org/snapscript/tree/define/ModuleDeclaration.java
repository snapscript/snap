package org.snapscript.tree.define;

import static org.snapscript.core.ModifierType.STATIC;
import static org.snapscript.core.result.Result.NORMAL;

import java.util.List;

import org.snapscript.core.Compilation;
import org.snapscript.core.Context;
import org.snapscript.core.Execution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.property.Property;
import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.trace.Trace;
import org.snapscript.core.trace.TraceInterceptor;
import org.snapscript.core.trace.TraceStatement;
import org.snapscript.tree.ModifierChecker;
import org.snapscript.tree.ModifierData;
import org.snapscript.tree.ModifierList;

public class ModuleDeclaration implements Compilation {
   
   private final ModuleProperty[] properties;
   private final ModifierData modifiers;
   
   public ModuleDeclaration(ModifierList modifiers, ModuleProperty... properties) {
      this.modifiers = new ModifierChecker(modifiers);
      this.properties = properties;    
   }
   
   @Override
   public ModulePart compile(Module module, Path path, int line) throws Exception {
      return new CompileResult(modifiers, properties, module, path, line);
   }
   
   private static class CompileResult implements ModulePart {

      private final ModuleProperty[] properties;
      private final ModifierData modifiers;
      private final Module module;
      private final Path path;
      private final int line;
      
      public CompileResult(ModifierData modifiers, ModuleProperty[] properties, Module module, Path path, int line) {
         this.properties = properties;
         this.modifiers = modifiers;
         this.module = module;
         this.path = path;
         this.line = line;
      }  
      
      @Override
      public Statement define(ModuleBody body, Module module) throws Exception {
         Context context = module.getContext();
         ErrorHandler handler = context.getHandler();
         TraceInterceptor interceptor = context.getInterceptor();
         Trace trace = Trace.getNormal(module, path, line);
         Statement statement = create(body);
         
         return new TraceStatement(interceptor, handler, statement, trace);
      }
      
      private Statement create(ModuleBody body) throws Exception {
         return new CompileStatement(modifiers, properties, body);
      }
   }
   
   private static class CompileStatement extends Statement {
   
      private final ModuleProperty[] properties;
      private final ModifierData modifiers;
      private final ModuleBody body;
      
      public CompileStatement(ModifierData modifiers, ModuleProperty[] properties, ModuleBody body) {
         this.properties = properties;
         this.modifiers = modifiers;
         this.body = body;
      }  
      
      @Override
      public boolean define(Scope scope) throws Exception {
         Module module = scope.getModule();
         List<Property> list = module.getProperties();
         int mask = modifiers.getModifiers();
         
         for(ModuleProperty declaration : properties) {
            Property property = declaration.define(body, scope, mask | STATIC.mask); 
            list.add(property);
         }
         return true;
      }
      
      @Override
      public Execution compile(Scope scope, Constraint returns) throws Exception {
         int mask = modifiers.getModifiers();
         
         for(ModuleProperty declaration : properties) {
            declaration.compile(body, scope, mask | STATIC.mask); 
         }
         return new CompileExecution(modifiers, properties, body);
      }
   }
   
   private static class CompileExecution extends Execution {
      
      private final ModuleProperty[] properties;
      private final ModifierData modifiers;
      private final ModuleBody body;
      
      public CompileExecution(ModifierData modifiers, ModuleProperty[] properties, ModuleBody body) {
         this.properties = properties;
         this.modifiers = modifiers;
         this.body = body;
      }  
      
      @Override
      public Result execute(Scope scope) throws Exception {
         Module module = scope.getModule();
         Scope outer = module.getScope(); // use the module scope
         int mask = modifiers.getModifiers();
         
         for(ModuleProperty declaration : properties) {
            declaration.execute(body, outer, mask | STATIC.mask); 
         }
         return NORMAL;
      }
   }
}