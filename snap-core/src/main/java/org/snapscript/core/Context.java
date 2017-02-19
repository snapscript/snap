/*
 * Context.java December 2016
 *
 * Copyright (C) 2016, Niall Gallagher <niallg@users.sf.net>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.snapscript.core;

import org.snapscript.core.bind.FunctionBinder;
import org.snapscript.core.convert.ConstraintMatcher;
import org.snapscript.core.convert.ProxyWrapper;
import org.snapscript.core.error.ErrorHandler;
import org.snapscript.core.link.PackageLinker;
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
   FunctionBinder getBinder();
   PackageLinker getLinker();
   ProxyWrapper getWrapper();
   TypeLoader getLoader();  
   
}