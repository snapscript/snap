/*
 * Reserved.java December 2016
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

public interface Reserved {
   String ANY_TYPE = "Any";
   String TYPE_CLASS = "class";
   String TYPE_THIS = "this";
   String TYPE_CONSTRUCTOR = "new";
   String ENUM_VALUES = "values";
   String ENUM_ORDINAL = "ordinal";
   String ENUM_NAME = "name";
   String PROPERTY_LENGTH = "length";
   String PROPERTY_GET = "get";
   String PROPERTY_SET = "set";
   String PROPERTY_IS = "is";
   String METHOD_CLOSURE = "anonymous";
   String METHOD_HASH_CODE = "hashCode";
   String METHOD_EQUALS = "equals";
   String METHOD_TO_STRING = "toString";
   String METHOD_ARGUMENT = "object";
   String METHOD_WAIT = "wait";
   String METHOD_NOTIFY = "notify";
   String METHOD_NOTIFY_ALL = "notifyAll";
   String IMPORT_JAVA = "java.";
   String IMPORT_JAVAX = "javax.";
   String IMPORT_JAVA_UTIL = "java.util.";
   String IMPORT_JAVA_MATH = "java.math.";
   String IMPORT_JAVA_IO = "java.io.";
   String IMPORT_JAVA_LANG = "java.lang.";
   String IMPORT_JAVA_NET = "java.net.";
   String IMPORT_SNAPSCRIPT = "org.snapscript.";
   String SCRIPT_EXTENSION = ".snap";
   String DEFAULT_PACKAGE = "default";
   String DEFAULT_RESOURCE = "Unknown Source";
   String DEFAULT_PARAMETER = "a";
}

