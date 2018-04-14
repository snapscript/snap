package org.snapscript.tree.script;

import static org.snapscript.core.result.Result.NORMAL;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.NoExecution;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.compile.FunctionScopeCompiler;
import org.snapscript.tree.compile.ScopeCompiler;
import org.snapscript.tree.constraint.DeclarationConstraint;
import org.snapscript.tree.function.FunctionBuilder;
import org.snapscript.tree.function.ParameterList;

public class ScriptFunction extends Statement {
   
   private final AtomicReference<FunctionBody> reference;
   private final ScopeCompiler compiler;
   private final ParameterList parameters;
   private final FunctionBuilder builder;
   private final NameReference identifier;
   private final Constraint constraint;
   private final Execution execution;
   
   public ScriptFunction(Evaluation identifier, ParameterList parameters, Statement body){  
      this(identifier, parameters, null, body);
   }
   
   public ScriptFunction(Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){  
      this.reference = new AtomicReference<FunctionBody>();
      this.constraint = new DeclarationConstraint(constraint);
      this.identifier = new NameReference(identifier);
      this.builder = new ScriptFunctionBuilder(body);
      this.compiler = new FunctionScopeCompiler();
      this.execution = new NoExecution(NORMAL);
      this.parameters = parameters;
   }  
   
   @Override
   public boolean define(Scope scope) throws Exception {
      Module module = scope.getModule();
      List<Function> functions = module.getFunctions();
      Signature signature = parameters.create(scope);
      String name = identifier.getName(scope);
      FunctionBody body = builder.create(signature, module, constraint, name);
      Function function = body.create(scope);
      Constraint constraint = function.getConstraint();
      
      functions.add(function);
      body.define(scope); // count stack
      constraint.getType(scope);
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