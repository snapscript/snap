package org.snapscript.tree.define;

import org.snapscript.core.Evaluation;
import org.snapscript.core.InternalStateException;
import org.snapscript.core.Scope;
import org.snapscript.core.State;
import org.snapscript.core.Value;
import org.snapscript.tree.DeclarationAllocator;
import org.snapscript.tree.ModifierData;
import org.snapscript.tree.NameExtractor;
import org.snapscript.tree.constraint.Constraint;
import org.snapscript.tree.literal.TextLiteral;

public class MemberFieldDeclaration implements Evaluation {

   private final DeclarationAllocator allocator;
   private final NameExtractor extractor;
   private final ModifierData modifiers;
   
   public MemberFieldDeclaration(ModifierData modifiers, TextLiteral identifier) {
      this(modifiers, identifier, null, null);
   }
   
   public MemberFieldDeclaration(ModifierData modifiers, TextLiteral identifier, Constraint constraint) {      
      this(modifiers, identifier, constraint, null);
   }
   
   public MemberFieldDeclaration(ModifierData modifiers, TextLiteral identifier, Evaluation value) {
      this(modifiers, identifier, null, value);
   }
   
   public MemberFieldDeclaration(ModifierData modifiers, TextLiteral identifier, Constraint constraint, Evaluation value) {
      this.allocator = new MemberFieldAllocator(constraint, value);
      this.extractor = new NameExtractor(identifier);
      this.modifiers = modifiers;
   }   

   @Override
   public Value evaluate(Scope scope, Object left) throws Exception {
      int mask = modifiers.getModifiers();
      String name = extractor.extract(scope);
      Value value = allocator.convert(scope, name, mask);
      State state = scope.getState();
      
      try { 
         state.add(name, value);
      }catch(Exception e) {
         throw new InternalStateException("Declaration of variable '" + name +"' failed", e);
      }  
      return value;
   }
}
