package org.snapscript.instruction.template;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import org.snapscript.tree.template.Segment;
import org.snapscript.tree.template.SegmentIterator;

public class SegmentIteratorTest extends TestCase {

   public void testTemplate() throws Exception {
      List<String> list = Arrays.asList(
            "a=${$aa",
            "arr=${arr}",
            "arr1=$${arr1}",
            "arr[1]=${arr[1]}",
            "arr[1]=$$${arr[1]}",
            "array[1]=$$$${array[1]}");
      
      for(String template: list){
         SegmentIterator iterator = new SegmentIterator(template);
         System.err.println("["+template+"]");
         int index=0;
         
         while(iterator.hasNext()) {
            Segment segment = iterator.next();
            System.err.println(index+"=["+segment+"]-->"+segment.getClass().getSimpleName());
            index++;
         }
         System.err.println();
      }
   }
}
