package org.snapscript.core;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class ProgramScopeTest extends TestCase {

   public void testState() throws Exception {
      Map<String, Object> map = new HashMap<String, Object>();
      Model model = new MapModel(map);
      State stack = new AddressState(null);
      ProgramScope scope = new ProgramScope(null, null, stack, model);
      
      State state = scope.getState();
      
      map.put("x", "X");
      map.put("y", "Y");
      
      state.add("a", ValueType.getReference("A"));
      state.add("b", ValueType.getReference("B"));
      
      Address address1 = state.address("a");
      Address address2 = state.address("b");
      Address address3 = state.address("x");
      Address address4 = state.address("y");
      
      assertEquals(state.get(address1).getValue(), "A");
      assertEquals(state.get(address2).getValue(), "B");
      assertEquals(state.get(address3).getValue(), "X");
      assertEquals(state.get(address4).getValue(), "Y");
   }
}
