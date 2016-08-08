package org.snapscript.core;

import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.error.ThreadStack;
import org.snapscript.core.link.PackageLinker;
import org.snapscript.core.trace.TraceInterceptor;

public interface Context extends Any {
   ThreadStack getStack();
   ErrorHandler getHandler();
   ResourceManager getManager();
   ModuleRegistry getRegistry();
   ConstraintMatcher getMatcher();
   ProgramValidator getValidator();
   TraceInterceptor getInterceptor();
   ExpressionEvaluator getEvaluator();
   FunctionBinder getBinder();
   PackageLinker getLinker();
   ProxyWrapper getWrapper();
   TypeLoader getLoader();  
   
}