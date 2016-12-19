package org.snapscript.compile;

import java.util.Arrays;

import junit.framework.TestCase;

import org.snapscript.core.AddressState;
import org.snapscript.core.Context;
import org.snapscript.core.ContextModule;
import org.snapscript.core.Module;
import org.snapscript.core.Stack;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.closure.ClosureFunctionFinder;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.store.ClassPathStore;
import org.snapscript.core.store.Store;

public class ClosureMatcherTest extends TestCase {

   public void testClosureMatcher() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      Stack state = new AddressState(1);
      Module module = new ContextModule(context, state, "/", "yy", 1);
      ConstraintMatcher matcher = context.getMatcher();
      TypeLoader loader = context.getLoader();
      ClosureFunctionFinder finder = new ClosureFunctionFinder(loader);
      Signature signature = new Signature(Arrays.asList(new Parameter("n", loader.loadType(String.class))), module, context.getStack().state());
      Type type = new EmptyFunction(signature).getHandle();
      ConstraintConverter converter = matcher.match(type);
      Function function = new InvocationFunction(signature, null, type, null, "xx");
      Score score = converter.score(function);
      
      System.err.println(score);
   }
}
