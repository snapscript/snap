package org.snapscript.tree.define;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.TypePart;
import org.snapscript.core.ValidationHelper;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionHandle;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public abstract class MemberConstructor extends TypePart {
   
   private final AtomicReference<FunctionHandle> reference;
   private final ConstructorAssembler assembler;
   private final AnnotationList annotations;
   private final ModifierList list;
   
   public MemberConstructor(AnnotationList annotations, ModifierList list, ParameterList parameters, Statement body){  
      this(annotations, list, parameters, null, body);
   }  
   
   public MemberConstructor(AnnotationList annotations, ModifierList list, ParameterList parameters, TypePart part, Statement body){  
      this.assembler = new ConstructorAssembler(parameters, part, body);
      this.reference = new AtomicReference<FunctionHandle>();
      this.annotations = annotations;
      this.list = list;
   } 
   Exception e;
   @Override
   public TypeFactory compile(TypeFactory factory, Type type) throws Exception {
      if(e!=null){
         System.err.println();
      } else {
         e=new Exception();
      }
      FunctionHandle handle = reference.get();
      Scope scope = type.getScope();
      Function function = handle.create(scope);
      Scope outer = ValidationHelper.create(type, function);
      
      handle.compile(outer);
      
      return null;
   }
   
   protected TypeFactory assemble(TypeFactory factory, Type type, boolean compile) throws Exception {
      int modifiers = list.getModifiers();
      Scope scope = type.getScope();
      ConstructorBuilder builder = assembler.assemble(factory, type);
      FunctionHandle handle = builder.create(factory, type, modifiers, compile);
      Function constructor = handle.create(scope);
      List<Function> functions = type.getFunctions();
      
      annotations.apply(scope, constructor);
      functions.add(constructor);
      handle.define(scope);
      reference.set(handle);
      
      return null;
   }
}