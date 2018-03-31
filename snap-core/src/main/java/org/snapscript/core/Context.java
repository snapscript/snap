package org.snapscript.core;

import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.function.dispatch.FunctionBinder;
import org.snapscript.core.function.search.FunctionSearcher;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.platform.PlatformProvider;
import org.snapscript.core.stack.ThreadStack;
import org.snapscript.core.trace.TraceInterceptor;

public interface Context extends Any {
   ThreadStack getStack();
   ErrorHandler getHandler();
   TypeExtractor getExtractor();
   ResourceManager getManager();
   ModuleRegistry getRegistry();
   ConstraintMatcher getMatcher();
   ProgramValidator getValidator();
   TraceInterceptor getInterceptor();
   ExpressionEvaluator getEvaluator();
   FunctionSearcher getSearcher();
   PlatformProvider getProvider();
   PackageLinker getLinker();
   ProxyWrapper getWrapper();
   FunctionBinder getBinder();
   TypeLoader getLoader();

   
}