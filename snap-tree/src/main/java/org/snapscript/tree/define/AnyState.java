package org.snapscript.tree.define;

import static org.snapscript.core.type.Category.OTHER;

import org.snapscript.core.result.Result;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeState;
import org.snapscript.core.type.index.PrimitiveInstanceBuilder;

public class AnyState extends TypeState {
   
   private final PrimitiveInstanceBuilder builder;
   
   public AnyState() {
      this.builder = new PrimitiveInstanceBuilder();
   }
   
   @Override
   public Category define(Scope instance, Type real) throws Exception {
      return OTHER;
   }

   @Override
   public Result execute(Scope instance, Type real) throws Exception {
      Scope base = builder.create(instance, real);      
      return Result.getNormal(base);
   }
}