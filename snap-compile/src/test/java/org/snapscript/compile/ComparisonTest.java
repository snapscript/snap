package org.snapscript.compile;

import junit.framework.TestCase;

import org.snapscript.compile.StoreContext;
import org.snapscript.core.Context;
import org.snapscript.core.EmptyModel;
import org.snapscript.core.Model;
import org.snapscript.core.store.ClassPathStore;
import org.snapscript.core.store.Store;

public class ComparisonTest extends TestCase {

   public void testComparisons() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store, null);
      Model model = new EmptyModel();
      
      assertEquals(Boolean.FALSE, context.getEvaluator().evaluate(model, "'a'>'b'"));
      assertEquals(Boolean.TRUE, context.getEvaluator().evaluate(model, "'a'<'b'"));
      assertEquals(Boolean.FALSE, context.getEvaluator().evaluate(model, "\"a\">\"b\""));
      assertEquals(Boolean.TRUE, context.getEvaluator().evaluate(model, "\"a\"<\"b\""));
   }
}
