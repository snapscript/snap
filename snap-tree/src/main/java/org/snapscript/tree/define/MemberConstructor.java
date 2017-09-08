package org.snapscript.tree.define;

import java.util.List;

import org.snapscript.core.Bug;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionHandle;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public abstract class MemberConstructor implements TypePart {
   
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
   
   @Override
   public TypeFactory define(TypeFactory factory, Type type) throws Exception {
      return null;
   }
   
   protected TypeFactory compile(TypeFactory factory, Type type, boolean compile) throws Exception {
      int modifiers = list.getModifiers();
      Scope scope = type.getScope();
      ConstructorBuilder builder = assembler.assemble(factory, type);
      FunctionHandle compiler = builder.create(factory, type, modifiers, compile);
      Function handle = compiler.compile(scope);
      List<Function> functions = type.getFunctions();
      
      annotations.apply(scope, handle);
      functions.add(handle);
      compiler.create(scope);
      
      return null;
   }
}