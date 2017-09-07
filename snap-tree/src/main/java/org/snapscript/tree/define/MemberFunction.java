package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionCompiler;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.function.ParameterList;

public class MemberFunction implements TypePart {
   
   protected final MemberFunctionAssembler assembler;
   protected final AnnotationList annotations;
   protected final Statement body;
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters){
      this(annotations, modifiers, identifier, parameters, null, null);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters, Constraint constraint){
      this(annotations, modifiers, identifier, parameters, constraint, null);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters, Statement body){  
      this(annotations, modifiers, identifier, parameters, null, body);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, Evaluation identifier, ParameterList parameters, Constraint constraint, Statement body){  
      this.assembler = new MemberFunctionAssembler(modifiers, identifier, parameters, constraint, body);
      this.annotations = annotations;
      this.body = body;
   } 
   
   @Override
   public TypeFactory define(TypeFactory factory, Type type) throws Exception {
      return null;
   }

   @Override
   public TypeFactory compile(TypeFactory factory, Type type) throws Exception {
      return assemble(factory, type, 0);
   }
   
   @Bug("crap")
   protected TypeFactory assemble(TypeFactory factory, Type type, int mask) throws Exception {
      Scope scope = type.getScope();
      MemberFunctionBuilder builder = assembler.assemble(type, mask);
      FunctionCompiler compiler = builder.create(factory, scope, type);
      Function function = compiler.create(scope);
      List<Function> functions = type.getFunctions();
      int modifiers = function.getModifiers();

      if(ModifierType.isStatic(modifiers)) {
         Module module = scope.getModule();
         List<Function> list = module.getFunctions();
         
         list.add(function); // This is VERY STRANGE!!! NEEDED BUT SHOULD NOT BE HERE!!!
      }
      annotations.apply(scope, function);
      functions.add(function);
      compiler.compile(scope); // count stacks
      
      return null; 
   }
}