package org.snapscript.tree.define;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Statement;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.compile.TypeScopeCompiler;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionHandle;
import org.snapscript.core.type.Allocation;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class MemberFunction extends TypePart {
   
   protected final AtomicReference<FunctionHandle> reference;
   protected final MemberFunctionAssembler assembler;
   protected final TypeScopeCompiler compiler;
   protected final AnnotationList annotations;
   protected final Statement statement;
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters){
      this(annotations, modifiers, identifier, parameters, null, null);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters, Constraint constraint){
      this(annotations, modifiers, identifier, parameters, constraint, null);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters, Statement body){  
      this(annotations, modifiers, identifier, parameters, null, body);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters, Constraint constraint, Statement statement){  
      this.assembler = new MemberFunctionAssembler(modifiers, identifier, parameters, constraint, statement);
      this.reference = new AtomicReference<FunctionHandle>();
      this.compiler = new TypeScopeCompiler();
      this.annotations = annotations;
      this.statement = statement;
   } 

   @Override
   public Allocation define(TypeBody factory, Type type, Scope scope) throws Exception {
      return assemble(factory, type, scope, 0);
   }
   
   @Override
   public void compile(TypeBody body, Type type, Scope scope) throws Exception {
      FunctionHandle handle = reference.get();
      Function function = handle.create(scope);
      Scope outer = compiler.compile(scope, type, function);

      handle.compile(outer);
   }
   
   protected Allocation assemble(TypeBody body, Type type, Scope scope, int mask) throws Exception {
      MemberFunctionBuilder builder = assembler.assemble(type, mask);
      FunctionHandle handle = builder.create(body, scope, type);
      Function function = handle.create(scope);
      Constraint constraint = function.getConstraint();
      List<Function> functions = type.getFunctions();
      int modifiers = function.getModifiers();

      if(ModifierType.isStatic(modifiers)) {
         Module module = scope.getModule();
         List<Function> list = module.getFunctions();
         
         list.add(function); // This is VERY STRANGE!!! NEEDED BUT SHOULD NOT BE HERE!!!
      }      
      annotations.apply(scope, function);
      functions.add(function);
      handle.define(scope); // count stacks
      constraint.getType(scope);
      reference.set(handle);
      
      return null; 
   }
}