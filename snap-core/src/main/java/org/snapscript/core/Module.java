/*
 * Module.java December 2016
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

import java.io.InputStream;
import java.util.List;

import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.link.ImportManager;

public interface Module extends Any{
   Scope getScope();
   Context getContext();
   ImportManager getManager();
   Type getType(Class type);   
   Type getType(String name);
   Type addType(String name);
   Module getModule(String module); 
   InputStream getResource(String path);
   List<Annotation> getAnnotations();
   List<Function> getFunctions();
   List<Type> getTypes();
   String getName();
   Path getPath();
   int getOrder();
}