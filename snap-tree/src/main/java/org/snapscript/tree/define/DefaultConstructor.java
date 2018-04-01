package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.NoStatement;
import org.snapscript.core.Scope;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.TypeBody;
import org.snapscript.core.Allocation;
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
   public Allocation define(TypeBody body, Type type, Scope scope) throws Exception {
      List<Function> functions = type.getFunctions();
      
      for(Function function : functions) {
         String name = function.getName();
         
         if(name.equals(TYPE_CONSTRUCTOR)) {
            return null;
         }
      }
      return assemble(body, type, scope, compile);
   }
   
   @Override
   public void compile(TypeBody body, Type type, Scope scope) throws Exception {
      ClassConstructor constructor = reference.get();
      
      if(constructor != null) {
         constructor.compile(body, type, scope);
      }
   }

   protected Allocation assemble(TypeBody body, Type type, Scope scope, boolean compile) throws Exception {
      Statement statement = new NoStatement();
      ClassConstructor constructor = new ClassConstructor(annotations, modifiers, parameters, statement);
      
      reference.set(constructor);
      
      return constructor.assemble(body, type, scope, compile);
   }
}