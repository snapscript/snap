package org.snapscript.compile;

import org.snapscript.core.scope.Model;
import org.snapscript.core.type.Any;

public interface Executable extends Any {   
   void execute() throws Exception;
   void execute(Model model) throws Exception;
   void execute(Model model, boolean test) throws Exception;
}