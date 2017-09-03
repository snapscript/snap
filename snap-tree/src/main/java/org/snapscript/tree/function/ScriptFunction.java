package org.snapscript.tree.function;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.ResultType;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.StatementFunction;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintReference;

public class ScriptFunction extends Statement {
   
   private final ConstraintReference constraint;
   private final ParameterList parameters;
   private final FunctionBuilder builder;
   private final NameReference identifier;
   
   public ScriptFunction(Evaluation identifier, ParameterList parameters, Statement body){  
      this(identifier, parameters, null, body);
   }
   
   public ScriptFunction(Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){  
      this.constraint = new ConstraintReference(constraint);
      this.identifier = new NameReference(identifier);
      this.builder = new FunctionBuilder(body);
      this.parameters = parameters;
   }  
   
   @Bug("clean me")
   @Override
   public Result compile(Scope scope) throws Exception {
      Module module = scope.getModule();
      List<Function> functions = module.getFunctions();
      Signature signature = parameters.create(scope);
      String name = identifier.getName(scope);
      Type returns = constraint.getConstraint(scope);
      StatementFunction f = builder.create(signature, module, returns, name);
      Function function = f.getFunction(scope);
      //Scope inner = scope.getInner();
      
      functions.add(function);
      f.compile(scope); // count stack
      
      return Result.getNormal(function);
   }
}