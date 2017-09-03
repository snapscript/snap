package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Bug;
import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.core.ValueType;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class Declaration {

   private final DeclarationAllocator allocator;
   private final NameReference reference;
   private final AtomicInteger index;
   private final Evaluation value;
   
   public Declaration(TextLiteral identifier) {
      this(identifier, null, null);
   }
   
   public Declaration(TextLiteral identifier, Constraint constraint) {      
      this(identifier, constraint, null);
   }
   
   public Declaration(TextLiteral identifier, Evaluation value) {
      this(identifier, null, value);
   }
   
   public Declaration(TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.allocator = new DeclarationAllocator(constraint, value);
      this.reference = new NameReference(identifier);
      this.index = new AtomicInteger(-1);
      this.value = value;
   }   

   @Bug("fix allocator")
   public Value compile(Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      
      if(value !=null){
         value.compile(scope, null);
      }
      int depth = scope.getState().addLocal(name);

      System.err.println("DECLARE: name="+name+" depth="+depth);
      index.set(depth);

      return ValueType.getTransient(null);
   }
   
   public Value create(Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Value value = allocator.allocate(scope, name, modifiers);
      State state = scope.getState();
      
      try { 
         int v = index.get();

         state.addLocal(v, value);
         //state.addScope(name, value);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }  
      return value;
   }
}