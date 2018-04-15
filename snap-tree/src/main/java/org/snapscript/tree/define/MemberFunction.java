package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class MemberFunction extends TypePart {
   
   protected final MemberFunctionAssembler assembler;
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
      this.annotations = annotations;
      this.statement = statement;
   } 

   @Override
   public TypeState define(TypeBody parent, Type type, Scope scope) throws Exception {
      return assemble(parent, type, scope, 0);
   }
   
   protected TypeState assemble(TypeBody parent, Type type, Scope scope, int mask) throws Exception {
      MemberFunctionBuilder builder = assembler.assemble(type, mask);
      FunctionBody body = builder.create(parent, scope, type);
      Function function = body.create(scope);
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
      body.define(scope); // count stacks
      constraint.getType(scope);
      
      return new FunctionBodyCompiler(body);
   }
}