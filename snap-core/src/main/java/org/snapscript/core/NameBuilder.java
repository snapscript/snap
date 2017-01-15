package org.snapscript.core;

public interface NameBuilder {
   String createFullName(Class type);
   String createShortName(Class type);
   String createFullName(String module, String name);
   String createArrayName(String module, String name, int size);
   String createOuterName(String module, String name);
   String createTopName(String type);
   String createTopName(String module, String name);
}
