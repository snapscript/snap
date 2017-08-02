package org.snapscript.core.bridge;

import org.snapscript.core.Type;

public interface BridgeProvider {
   BridgeBuilder create(Type type);
}