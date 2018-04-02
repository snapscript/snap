package org.snapscript.core;

import org.snapscript.core.module.Module;
import org.snapscript.core.type.Type;

public interface ApplicationValidator {
   void validate(Context context) throws Exception;
   void validate(Type type) throws Exception;
   void validate(Module module) throws Exception;
}