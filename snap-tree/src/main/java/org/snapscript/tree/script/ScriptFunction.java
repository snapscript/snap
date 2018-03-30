package org.snapscript.tree.script;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Constraint;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Execution;
import org.snapscript.core.FunctionScopeCompiler;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.ScopeCompiler;
import org.snapscript.core.Statement;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionHandle;
import org.snapscript.core.function.Signature;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.constraint.SafeConstraint;
import org.snapscript.tree.function.FunctionBuilder;
import org.snapscript.tree.function.ParameterList;

public class ScriptFunction extends Statement {
   
   private final AtomicReference<FunctionHandle> reference;
   private final ScopeCompiler compiler;
   private final ParameterList parameters;
   private final FunctionBuilder builder;
   private final NameReference identifier;
   private final Constraint constraint;
   
   public ScriptFunction(Evaluation identifier, ParameterList parameters, Statement body){  
      this(identifier, parameters, null, body);
   }
   
   public ScriptFunction(Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){  
      this.reference = new AtomicReference<FunctionHandle>();
      this.constraint = new SafeConstraint(constraint);
      this.identifier = new NameReference(identifier);
      this.builder = new ScriptFunctionBuilder(body);
      this.compiler = new FunctionScopeCompiler();
      this.parameters = parameters;
   }  
   
   @Override
   public void define(Scope scope) throws Exception {
      Module module = scope.getModule();
      List<Function> functions = module.getFunctions();
      Signature signature = parameters.create(scope);
      String name = identifier.getName(scope);
      FunctionHandle handle = builder.create(signature, module, constraint, name);
      Function function = handle.create(scope);
      
      functions.add(function);
      handle.define(scope); // count stack
      reference.set(handle);
   }
   
   @Override
   public Execution compile(Scope scope) throws Exception {
      FunctionHandle handle = reference.get();
      String name = identifier.getName(scope);      
      
      if(handle == null) {
         throw new InternalStateException("Function '" + name + "' was not compiled");
      }
      Function function = handle.create(scope);
      Constraint constraint = function.getConstraint();
      Scope combined = compiler.compile(scope, null, function);
      
      constraint.getType(scope);
      handle.compile(combined);
      
      return Execution.getNone();
   }
}