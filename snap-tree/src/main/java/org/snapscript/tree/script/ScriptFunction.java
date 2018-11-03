package org.snapscript.tree.script;

import static org.snapscript.core.result.Result.NORMAL;
import static org.snapscript.core.scope.index.CaptureType.COMPILE_SCRIPT;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Execution;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.constraint.DeclarationConstraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.tree.compile.FunctionScopeCompiler;
import org.snapscript.tree.constraint.FunctionName;
import org.snapscript.tree.function.FunctionBuilder;
import org.snapscript.tree.function.ParameterList;

public class ScriptFunction extends Statement {
   
   private final AtomicReference<FunctionBody> reference;
   private final FunctionScopeCompiler compiler;
   private final ParameterList parameters;
   private final FunctionBuilder builder;
   private final FunctionName identifier;
   private final Constraint constraint;
   private final Execution execution;
   
   public ScriptFunction(FunctionName identifier, ParameterList parameters, Statement body){
      this(identifier, parameters, null, body);
   }
   
   public ScriptFunction(FunctionName identifier, ParameterList parameters, Constraint constraint, Statement body){
      this.reference = new AtomicReference<FunctionBody>();
      this.constraint = new DeclarationConstraint(constraint);
      this.compiler = new FunctionScopeCompiler(identifier, COMPILE_SCRIPT);
      this.builder = new ScriptFunctionBuilder(body);
      this.execution = new NoExecution(NORMAL);
      this.identifier = identifier;
      this.parameters = parameters;
   }  
   
   @Override
   public boolean define(Scope scope) throws Exception {
      Module module = scope.getModule();
      String name = identifier.getName(scope);
      Scope combined = compiler.define(scope, null);
      List<Function> functions = module.getFunctions();
      List<Constraint> generics = identifier.getGenerics(combined);
      Signature signature = parameters.create(combined, generics);
      FunctionBody body = builder.create(signature, module, constraint, name);
      Function function = body.create(combined);
      
      functions.add(function);
      body.define(combined); // count stack
      reference.set(body);
      
      return false;
   }
   
   @Override
   public Execution compile(Scope scope, Constraint returns) throws Exception {
      FunctionBody body = reference.get();
      String name = identifier.getName(scope);      
      
      if(body == null) {
         throw new InternalStateException("Function '" + name + "' was not compiled");
      }
      Function function = body.create(scope);
      Scope combined = compiler.compile(scope, null, function);
      
      body.compile(combined);
      
      return execution;
   }
}