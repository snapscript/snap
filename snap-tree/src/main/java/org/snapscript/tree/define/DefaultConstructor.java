package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;

import java.util.List;

import org.snapscript.core.NoStatement;
import org.snapscript.core.Statement;
import org.snapscript.core.Type;
import org.snapscript.core.define.Initializer;
import org.snapscript.core.function.Function;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class DefaultConstructor implements TypePart {
   
   private final AnnotationList annotations;
   private final ParameterList parameters;
   private final ModifierList modifiers;
   private final boolean compile;

   public DefaultConstructor(){
      this(true);
   }
   
   public DefaultConstructor(boolean compile) {
      this.annotations = new AnnotationList();
      this.parameters = new ParameterList();
      this.modifiers = new ModifierList();
      this.compile = compile;
   } 
   
   @Override
   public Initializer compile(Initializer statements, Type type) throws Exception {
      List<Function> functions = type.getFunctions();
      
      for(Function function : functions) {
         String name = function.getName();
         
         if(name.equals(TYPE_CONSTRUCTOR)) {
            return null;
         }
      }
      return define(statements, type, compile);
   }
   
   protected Initializer define(Initializer statements, Type type, boolean compile) throws Exception {
      Statement statement = new NoStatement();
      ClassConstructor constructor = new ClassConstructor(annotations, modifiers, parameters, statement);
      
      return constructor.compile(statements, type, compile);
   }
}