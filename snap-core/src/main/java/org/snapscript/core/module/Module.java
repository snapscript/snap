package org.snapscript.core.module;

import java.io.InputStream;
import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.Entity;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.link.ImportManager;
import org.snapscript.core.property.Property;
import org.snapscript.core.type.Type;

public interface Module extends Entity{
   Context getContext();
   ImportManager getManager();
   Type getType(Class type);   
   Type getType(String name);
   Type addType(String name, int modifiers);
   Module getModule(String module); 
   InputStream getResource(String path);
   List<Annotation> getAnnotations();
   List<Property> getProperties();
   List<Function> getFunctions();
   List<Type> getTypes();
   Type getType();
   Path getPath();
}