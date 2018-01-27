package org.snapscript.core;

import java.io.InputStream;
import java.util.List;

import org.snapscript.core.link.ImportManager;

public interface Module extends Entity{
   Scope getScope();
   Context getContext();
   ImportManager getManager();
   Type getType(Class type);   
   Type getType(String name);
   Type addType(String name, Category category);
   Module getModule(String module); 
   InputStream getResource(String path);
   List<Type> getTypes();
   Path getPath();
   int getOrder();
}