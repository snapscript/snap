package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.ModifierType;
import org.snapscript.core.ModifierValidator;
import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.module.Module;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.core.type.TypeState;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.compile.FunctionScopeCompiler;
import org.snapscript.tree.compile.ScopeCompiler;
import org.snapscript.tree.constraint.FunctionName;
import org.snapscript.tree.function.ParameterList;

public class MemberFunction extends TypePart {
   
   protected final MemberFunctionAssembler assembler;
   protected final FunctionScopeCompiler compiler;
   protected final ModifierValidator validator;
   protected final AnnotationList annotations;
   protected final FunctionName identifier;
   protected final Statement statement;
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, FunctionName identifier, ParameterList parameters){
      this(annotations, modifiers, identifier, parameters, null, null);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, FunctionName identifier, ParameterList parameters, Constraint constraint){
      this(annotations, modifiers, identifier, parameters, constraint, null);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, FunctionName identifier, ParameterList parameters, Statement body){
      this(annotations, modifiers, identifier, parameters, null, body);
   }
   
   public MemberFunction(AnnotationList annotations, ModifierList modifiers, FunctionName identifier, ParameterList parameters, Constraint constraint, Statement statement){
      this.assembler = new MemberFunctionAssembler(modifiers, identifier, parameters, constraint, statement);
      this.compiler = new FunctionScopeCompiler(identifier);
      this.validator = new ModifierValidator();
      this.annotations = annotations;
      this.identifier = identifier;
      this.statement = statement;
   } 

   @Override
   public TypeState define(TypeBody parent, Type type, Scope scope) throws Exception {
      return assemble(parent, type, scope, 0);
   }
   
   @Bug
   protected TypeState assemble(TypeBody parent, Type type, Scope scope, int mask) throws Exception {
      Scope composite = compiler.define(scope, type);
      MemberFunctionBuilder builder = assembler.assemble(composite, mask);
      FunctionBody body = builder.create(parent, composite, type);
      Function function = body.create(composite);
      List<Function> functions = type.getFunctions();
      int modifiers = function.getModifiers();

      if(ModifierType.isStatic(modifiers)) {
         Module module = scope.getModule();
         List<Function> list = module.getFunctions();
         
         list.add(function); // This is VERY STRANGE!!! NEEDED BUT SHOULD NOT BE HERE!!!
      }      
      validator.validate(type, function, modifiers);
      annotations.apply(composite, function);
      functions.add(function);
      body.define(composite); // count stacks
      
      return new FunctionBodyCompiler(identifier, body);
   }
}