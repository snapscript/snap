package org.snapscript.tree.construct;

import static java.util.Collections.EMPTY_LIST;
import static org.snapscript.core.constraint.Constraint.NONE;
import static org.snapscript.core.variable.Value.NULL;

import java.util.Arrays;
import java.util.List;

import org.snapscript.core.scope.Scope;
import org.snapscript.core.variable.Value;
import org.snapscript.tree.ArgumentList;
import org.snapscript.tree.collection.Range;

public class ElementData {

   private final ArgumentList list;
   private final Range range;

   public ElementData(Range range) {
      this(null, range);
   }

   public ElementData(ArgumentList list) {
      this(list, null);
   }

   public ElementData(ArgumentList list, Range range) {
      this.range = range;
      this.list = list;
   }

   public void define(Scope scope) throws Exception{
      if(list != null) {
         list.define(scope);
      }
      if(range != null) {
         range.define(scope);
      }
   }

   public void compile(Scope scope) throws Exception{
      if(list != null) {
         list.compile(scope, NONE);
      }
      if(range != null) {
         range.compile(scope, NONE);
      }
   }

   public Value evaluate(Scope scope) throws Exception {
      if(list != null) {
         Object[] values = list.create(scope);
         List<Object> iterable = Arrays.asList(values);

         return Value.getTransient(iterable);
      }
      if(range != null) {
         return range.evaluate(scope, NULL);
      }
      return Value.getTransient(EMPTY_LIST);
   }
}
