package org.snapscript.core.type;

import org.snapscript.core.link.Package;

public interface TypeLoader {
   Package importPackage(String module);
   Package importType(String type);
   Package importType(String module, String name);
   Type defineType(String module, String name, int modifiers);
   Type loadType(String module, String name);
   Type loadArrayType(String module, String name, int size);
   Type loadType(String type);
   Type loadType(Class type);
}