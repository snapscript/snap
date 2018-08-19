package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Statement;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionBody;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypePart;
import org.snapscript.core.type.TypeState;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.compile.FunctionScopeCompiler;
import org.snapscript.tree.constraint.ConstructorName;
import org.snapscript.tree.constraint.FunctionName;
import org.snapscript.tree.function.ParameterList;

public abstract class MemberConstructor extends TypePart {
   
   private final FunctionScopeCompiler compiler;
   private final ConstructorAssembler assembler;
   private final AnnotationList annotations;
   private final FunctionName identifier;
   private final ModifierList list;
   
   public MemberConstructor(AnnotationList annotations, ModifierList list, ParameterList parameters, Statement body){  
      this(annotations, list, parameters, null, body);
   }  
   
   public MemberConstructor(AnnotationList annotations, ModifierList list, ParameterList parameters, TypePart part, Statement body){  
      this.assembler = new ConstructorAssembler(parameters, part, body);
      this.identifier = new ConstructorName();
      this.compiler = new FunctionScopeCompiler(identifier);
      this.annotations = annotations;
      this.list = list;
   } 
   
   protected TypeState assemble(TypeBody parent, Type type, Scope scope, boolean compile) throws Exception {
      int modifiers = list.getModifiers();
      ConstructorBuilder builder = assembler.assemble(parent, type, scope);
      FunctionBody body = builder.create(parent, type, modifiers, compile);
      Function constructor = body.create(scope);
      Scope composite = compiler.define(scope, type);
      List<Function> functions = type.getFunctions();
      
      annotations.apply(composite, constructor);
      functions.add(constructor);
      body.define(composite);
      
      return new FunctionBodyCompiler(identifier, body);
   }
}