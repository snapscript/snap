package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Statement;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public abstract class MemberConstructor extends TypePart {
   
   private final ConstructorAssembler assembler;
   private final AnnotationList annotations;
   private final ModifierList list;
   
   public MemberConstructor(AnnotationList annotations, ModifierList list, ParameterList parameters, Statement body){  
      this(annotations, list, parameters, null, body);
   }  
   
   public MemberConstructor(AnnotationList annotations, ModifierList list, ParameterList parameters, TypePart part, Statement body){  
      this.assembler = new ConstructorAssembler(parameters, part, body);
      this.annotations = annotations;
      this.list = list;
   } 
   
   protected TypeState assemble(TypeBody parent, Type type, Scope scope, boolean compile) throws Exception {
      int modifiers = list.getModifiers();
      ConstructorBuilder builder = assembler.assemble(parent, type, scope);
      FunctionBody body = builder.create(parent, type, modifiers, compile);
      Function constructor = body.create(scope);
      Constraint constraint = constructor.getConstraint();
      List<Function> functions = type.getFunctions();
      
      annotations.apply(scope, constructor);
      functions.add(constructor);
      body.define(scope);
      constraint.getType(scope);
      
      return new FunctionBodyCompiler(body);
   }
}