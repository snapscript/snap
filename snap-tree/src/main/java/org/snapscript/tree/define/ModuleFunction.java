package org.snapscript.tree.define;

import static org.snapscript.core.result.Result.NORMAL;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Statement;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.compile.TypeScopeCompiler;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionHandle;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.DeclarationConstraint;
import org.snapscript.tree.function.ParameterList;

public class ModuleFunction implements ModulePart {
   
   private final TypeScopeCompiler compiler;
   private final AnnotationList annotations;
   private final ParameterList parameters;
   private final NameReference reference;
   private final Constraint constraint;
   private final Statement statement;
   private final Execution execution;
   
   public ModuleFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters, Statement statement){  
      this(annotations, modifiers, identifier, parameters, null, statement);
   }
   
   public ModuleFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters, Constraint constraint, Statement statement){  
      this.constraint = new DeclarationConstraint(constraint);
      this.reference = new NameReference(identifier);
      this.execution = new NoExecution(NORMAL);
      this.compiler = new TypeScopeCompiler();
      this.annotations = annotations;
      this.parameters = parameters;
      this.statement = statement;
   }  
   
   @Override
   public Statement define(ModuleBody body) throws Exception {
      return new DefineResult(body, statement);
   }
   
   private class DefineResult extends Statement {
   
      private final AtomicReference<FunctionHandle> cache;
      private final ModuleFunctionBuilder builder;
      
      public DefineResult(ModuleBody body, Statement statement) {
         this.builder = new ModuleFunctionBuilder(body, statement);
         this.cache = new AtomicReference<FunctionHandle>();
      }
      
      @Override
      public boolean define(Scope scope) throws Exception {
         Module module = scope.getModule();
         List<Function> functions = module.getFunctions();
         Signature signature = parameters.create(scope);
         String name = reference.getName(scope);
         FunctionHandle handle = builder.create(signature, module, constraint, name);
         Function function = handle.create(scope);
         Constraint constraint = function.getConstraint();         

         annotations.apply(scope, function);
         functions.add(function);
         handle.define(scope); // count stack
         constraint.getType(scope);
         cache.set(handle);
         
         return false;
      }      
      
      @Override
      public Execution compile(Scope scope) throws Exception {
         FunctionHandle handle = cache.get();
         Module module = scope.getModule();
         Type type = module.getType(); // ???
         Function function = handle.create(scope);
         Scope outer = compiler.compile(scope, type, function);

         handle.compile(outer);
         
         return execution;
      }
   }
}