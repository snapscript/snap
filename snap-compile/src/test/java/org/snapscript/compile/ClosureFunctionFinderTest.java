package org.snapscript.compile;

import static org.snapscript.core.function.Origin.DEFAULT;

import java.util.Arrays;

import junit.framework.TestCase;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.FunctionComparator;
import org.snapscript.core.function.ClosureFunctionFinder;
import org.snapscript.core.function.Function;
import org.snapscript.core.function.FunctionSignature;
import org.snapscript.core.function.InvocationFunction;
import org.snapscript.core.function.Parameter;
import org.snapscript.core.function.Signature;
import org.snapscript.core.module.ContextModule;
import org.snapscript.core.module.Module;
import org.snapscript.core.module.Path;
import org.snapscript.core.type.Type;
import org.snapscript.core.type.TypeExtractor;
import org.snapscript.core.type.TypeLoader;

public class ClosureFunctionFinderTest extends TestCase {

   public void testFunctionFinder() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      TypeLoader loader = context.getLoader();
      TypeExtractor extractor = context.getExtractor();
      ConstraintMatcher matcher = context.getMatcher();
      Path path = new Path("/");
      FunctionComparator comparator = new FunctionComparator(matcher);
      Module module = new ContextModule(context, null, path, "yy", "", 1);
      ClosureFunctionFinder finder = new ClosureFunctionFinder(comparator, extractor, loader);
      Parameter parameter = new Parameter("n", Constraint.STRING, false);
      Signature signature = new FunctionSignature(Arrays.asList(parameter), module, null, DEFAULT, false);
      Type type = new InvocationFunction(signature, null, null, null, "xx").getHandle();
      Function function = finder.findFunctional(type);
      
      assertNotNull(function);
   }
}
