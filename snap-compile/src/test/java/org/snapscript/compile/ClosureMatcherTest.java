package org.snapscript.compile;

import java.util.Arrays;

import junit.framework.TestCase;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.core.Context;
import org.snapscript.core.ContextModule;
import org.snapscript.core.Module;
import org.snapscript.core.Path;
import org.snapscript.core.Type;
import org.snapscript.core.TypeLoader;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.EmptyFunction;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionFinder;
import org.snapscript.core.function.FunctionSignature;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;

public class ClosureMatcherTest extends TestCase {

   public void testClosureMatcher() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      Path path = new Path("/");
      Module module = new ContextModule(context, null, path, "yy", 1);
      ConstraintMatcher matcher = context.getMatcher();
      TypeLoader loader = context.getLoader();
      FunctionFinder finder = new FunctionFinder(loader);
      Parameter parameter = new Parameter("n", loader.loadType(String.class), false);
      Signature signature = new FunctionSignature(Arrays.asList(parameter), module, null, false);
      Type type = new EmptyFunction(signature).getHandle();
      ConstraintConverter converter = matcher.match(type);
      Function function = new InvocationFunction(signature, null, type, null, "xx");
      Score score = converter.score(function);
      
      System.err.println(score);
   }
}
