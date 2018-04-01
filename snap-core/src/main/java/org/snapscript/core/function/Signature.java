package org.snapscript.core.function;

import java.lang.reflect.Member;
import java.util.List;

import org.snapscript.core.type.Type;

public interface Signature {
   ArgumentConverter getConverter();
   List<Parameter> getParameters();
   Type getDefinition();
   Member getSource();
   boolean isVariable();
   boolean isAbsolute(); // does it have absolute parameters
   boolean isInvalid();
}