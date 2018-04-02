package org.snapscript.core.module;

import java.io.InputStream;
import java.util.List;

import org.snapscript.core.Context;
import org.snapscript.core.annotation.Annotation;
import org.snapscript.core.function.Function;
import org.snapscript.core.link.ImportManager;
import org.snapscript.core.property.Property;
import org.snapscript.core.scope.Scope;
import org.snapscript.core.type.Any;
import org.snapscript.core.type.Category;
import org.snapscript.core.type.Type;

public interface Module extends Any{
   Scope getScope();
   Context getContext();
   ImportManager getManager();
   Type getType(Class type);   
   Type getType(String name);
   Type addType(String name, Category category);
   Module getModule(String module); 
   InputStream getResource(String path);
   List<Annotation> getAnnotations();
   List<Property> getProperties();
   List<Function> getFunctions();
   List<Type> getTypes();
   Type getType();
   String getName();
   Path getPath();
   int getOrder();
}