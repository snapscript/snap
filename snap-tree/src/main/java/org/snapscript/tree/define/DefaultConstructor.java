package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.NoStatement;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeFactory;
import org.snapscript.core.TypePart;
import org.snapscript.core.function.Function;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class DefaultConstructor extends TypePart {
   
   private final AtomicReference<ClassConstructor> reference;
   private final AnnotationList annotations;
   private final ParameterList parameters;
   private final ModifierList modifiers;
   private final boolean compile;

   public DefaultConstructor(){
      this(true);
   }
   
   public DefaultConstructor(boolean compile) {
      this.reference = new AtomicReference<ClassConstructor>();
      this.annotations = new AnnotationList();
      this.parameters = new ParameterList();
      this.modifiers = new ModifierList();
      this.compile = compile;
   } 

   @Override
   public TypeFactory define(TypeFactory factory, Type type, Scope scope) throws Exception {
      List<Function> functions = type.getFunctions();
      
      for(Function function : functions) {
         String name = function.getName();
         
         if(name.equals(TYPE_CONSTRUCTOR)) {
            return null;
         }
      }
      return assemble(factory, type, scope, compile);
   }
   
   @Override
   public TypeFactory compile(TypeFactory factory, Type type, Scope scope) throws Exception {
      ClassConstructor constructor = reference.get();
      
      if(constructor != null) {
         constructor.compile(factory, type, scope);
      }
      return null;
   }

   protected TypeFactory assemble(TypeFactory factory, Type type, Scope scope, boolean compile) throws Exception {
      Statement statement = new NoStatement();
      ClassConstructor constructor = new ClassConstructor(annotations, modifiers, parameters, statement);
      
      reference.set(constructor);
      
      return constructor.assemble(factory, type, scope, compile);
   }
}