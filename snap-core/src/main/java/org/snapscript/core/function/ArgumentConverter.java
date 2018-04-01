package org.snapscript.core.function;

import org.snapscript.core.type.Type;
import org.snapscript.core.convert.Score;

public interface ArgumentConverter { 
   Score score(Type... list) throws Exception;
   Score score(Object... list) throws Exception;
   Object[] assign(Object... list) throws Exception;
   Object[] convert(Object... list) throws Exception;   
}