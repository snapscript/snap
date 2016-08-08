package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Evaluation;
import org.snapscript.core.ModifierType;
import org.snapscript.core.Module;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Function;
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
   public Initializer compile(Initializer initializer, Type type) throws Exception {
      return define(initializer, type, 0);
   }
   
   protected Initializer define(Initializer initializer, Type type, int mask) throws Exception {
      Scope scope = type.getScope();
      MemberFunctionBuilder builder = assembler.assemble(type, mask);
      Function function = builder.create(scope, initializer, type);
      List<Function> functions = type.getFunctions();
      int modifiers = function.getModifiers();

      if(ModifierType.isStatic(modifiers)) {
         Module module = scope.getModule();
         List<Function> list = module.getFunctions();
         
         list.add(function); // This is VERY STRANGE!!! NEEDED BUT SHOULD NOT BE HERE!!!
      }
      annotations.apply(scope, function);
      functions.add(function);
      
      if(body != null) {
         body.compile(scope);
      }
      return null; 
   }
}