package org.snapscript.core.type.index;

import java.lang.reflect.Type;

import org.snapscript.core.constraint.Constraint;

public interface GenericConverter<T extends Type> {
   Constraint convert(T type, String name, int modifiers);
}
