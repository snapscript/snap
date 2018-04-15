package org.snapscript.tree.define;

import static org.snapscript.core.Reserved.TYPE_CONSTRUCTOR;
import static org.snapscript.core.type.Order.OTHER;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.NoStatement;
import org.snapscript.core.Statement;
import org.snapscript.core.function.Function;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Order;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeBody;
import org.snapscript.core.type.TypeState;
import org.snapscript.tree.ModifierList;
import org.snapscript.tree.annotation.AnnotationList;
import org.snapscript.tree.function.ParameterList;

public class DefaultConstructor extends TypeState {
   
   private final AtomicReference<TypeState> reference;
   private final MemberConstructor constructor;
   private final AnnotationList annotations;
   private final ParameterList parameters;
   private final ModifierList modifiers;
   private final Statement statement;
   private final TypeBody body;
   private final boolean compile;

   public DefaultConstructor(TypeBody body){
      this(body, true);
   }
   
   public DefaultConstructor(TypeBody body, boolean compile) {
      this.reference = new AtomicReference<TypeState>();
      this.annotations = new AnnotationList();
      this.parameters = new ParameterList();
      this.modifiers = new ModifierList();
      this.statement = new NoStatement();
      this.constructor = new ClassConstructor(annotations, modifiers, parameters, statement);
      this.compile = compile;
      this.body = body;
   } 

   @Override
   public Order define(Scope scope, Type type) throws Exception {
      List<Function> functions = type.getFunctions();
      
      for(Function function : functions) {
         String name = function.getName();
         
         if(name.equals(TYPE_CONSTRUCTOR)) {
            return OTHER;
         }
      }
      TypeState allocation = constructor.assemble(body, type, scope, compile);
      
      if(allocation != null) {
         reference.set(allocation);
      }
      return OTHER;
   }
   
   @Override
   public void compile(Scope scope, Type type) throws Exception {
      TypeState state = reference.get();
      
      if(state != null) {
         state.compile(scope, type);
      }
   }
}