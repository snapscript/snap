package org.snapscript.compile;

import static java.util.Collections.EMPTY_LIST;
import static org.snapscript.core.function.Origin.DEFAULT;

import java.util.Arrays;

import junit.framework.TestCase;

import org.snapscript.common.store.ClassPathStore;
import org.snapscript.common.store.Store;
import org.snapscript.core.Context;
import org.snapscript.core.constraint.Constraint;
import org.snapscript.core.convert.ConstraintConverter;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.FunctionComparator;
import org.snapscript.core.convert.Score;
import org.snapscript.core.function.ClosureFunctionFinder;
import org.snapscript.core.function.EmptyFunction;
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

public class ClosureMatcherTest extends TestCase {

   public void testClosureMatcher() throws Exception {
      Store store = new ClassPathStore();
      Context context = new StoreContext(store);
      Path path = new Path("/");
      Module module = new ContextModule(context, null, path, "yy", "", 1);
      ConstraintMatcher matcher = context.getMatcher();
      TypeLoader loader = context.getLoader();
      TypeExtractor extractor = context.getExtractor();
      FunctionComparator comparator = new FunctionComparator(matcher);
      ClosureFunctionFinder finder = new ClosureFunctionFinder(comparator, extractor, loader);
      Parameter parameter = new Parameter("n", Constraint.STRING, false);
      Signature signature = new FunctionSignature(Arrays.asList(parameter), EMPTY_LIST, module, null, DEFAULT, false);
      Type type = new EmptyFunction(signature).getHandle();
      ConstraintConverter converter = matcher.match(type);
      Function function = new InvocationFunction(signature, null, type, null, "xx");
      Score score = converter.score(function);
      
      System.err.println(score);
   }
}
