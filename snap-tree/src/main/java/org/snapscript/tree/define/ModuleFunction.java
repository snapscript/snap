package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Module;
import org.snapscript.core.Result;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.Signature;
import org.snapscript.core.function.FunctionCompiler;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.NameReference;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.constraint.ConstraintReference;
import org.snapscript.tree.function.FunctionBuilder;
import org.snapscript.tree.function.ParameterList;

public class ModuleFunction extends Statement {
   
   private final ConstraintReference constraint;
   private final AnnotationList annotations;
   private final ParameterList parameters;
   private final FunctionBuilder builder;
   private final NameReference reference;
   
   public ModuleFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters, Statement body){  
      this(annotations, modifiers, identifier, parameters, null, body);
   }
   
   public ModuleFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){  
      this.constraint = new ConstraintReference(constraint);
      this.reference = new NameReference(identifier);
      this.builder = new FunctionBuilder(body);
      this.annotations = annotations;
      this.parameters = parameters;
   }  
   
   @Override
   public Result compile(Scope scope) throws Exception {
      Module module = scope.getModule();
      List<Function> functions = module.getFunctions();
      Signature signature = parameters.create(scope);
      String name = reference.getName(scope);
      Type returns = constraint.getConstraint(scope);
      FunctionCompiler compiler = builder.create(signature, module, returns, name);
      Function function = compiler.create(scope);
      
      annotations.apply(scope, function);
      functions.add(function);
      compiler.compile(scope); // count stack
      
      return Result.getNormal(function);
   }
}