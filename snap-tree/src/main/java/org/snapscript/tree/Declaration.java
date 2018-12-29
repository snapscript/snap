package org.snapscript.tree;

import java.util.concurrent.atomic.AtomicReference;

import org.snapscript.core.Evaluation;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.error.InternalStateException;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.scope.index.Address;
import org.snapscript.core.scope.index.Index;
import org.snapscript.core.scope.index.Local;
import org.snapscript.core.scope.index.Table;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.literal.TextLiteral;

public class Declaration {

   private final AtomicReference<Address> location;
   private final DeclarationAllocator allocator;
   private final NameReference reference;
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
      this.location = new AtomicReference<Address>();
      this.value = value;
   }   

   public Address define(Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      
      if(value != null){
         value.define(scope); // must compile value first
      }
      Index index = scope.getIndex();
      Address address = index.index(name);
      
      location.set(address);
      return address;
   }
   
   public Value compile(Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Local local = allocator.compile(scope, name, modifiers);
      Table table = scope.getTable();
      Address address = location.get();
      
      try {
         table.addValue(address, local);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }  
      return local;
   }
   
   public Value declare(Scope scope, int modifiers) throws Exception {
      String name = reference.getName(scope);
      Local local = allocator.allocate(scope, name, modifiers);
      Table table = scope.getTable();
      Address address = location.get();
      
      try {
         table.addValue(address, local);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }  
      return local;
   }
}