package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicInteger;

import org.snapscript.core.Constraint;
import org.snapscript.core.Evaluation;
import org.snapscript.core.Index;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Local;
import org.snapscript.core.Scope;
import org.snapscript.core.Table;
import org.snapscript.core.Value;
import org.snapscript.tree.literal.TextLiteral;

public class Declaration {

   private final DeclarationAllocator allocator;
   private final NameReference reference;
   private final AtomicInteger offset;
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
      this.offset = new AtomicInteger(-1);
      this.value = value;
   }   

   public void compile(Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      
      if(value != null){
         value.define(scope); // must compile value first
      }
      Index index = scope.getIndex();
      int depth = index.index(name);
      
      offset.set(depth);
   }
   
   public Value validate(Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Local local = allocator.validate(scope, name, modifiers);
      Table table = scope.getTable();
      int depth = offset.get();
      
      try { 
         table.add(depth, local);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }  
      return local;
   }
   
   public Value create(Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Local local = allocator.allocate(scope, name, modifiers);
      Table table = scope.getTable();
      int depth = offset.get();
      
      try { 
         table.add(depth, local);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }  
      return local;
   }
}