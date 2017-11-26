package org.snapscript.core;

public interface Reserved {
   String ANY_TYPE = "Any";
   String TYPE_CLASS = "class";
   String TYPE_THIS = "this";
   String TYPE_SUPER = "super";
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
   String IMPORT_SNAPSCRIPT = "org.snapscript.";
   String SCRIPT_EXTENSION = ".snap";
   String DEFAULT_PACKAGE = "default";
   String DEFAULT_RESOURCE = "Unknown Source";
   String DEFAULT_PARAMETER = "a";
   String GRAMMAR_FILE = "grammar.txt";
   String IMPORT_FILE = "import.txt";
}