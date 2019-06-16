package springframework.doc.mutiscope;

import java.util.Random;

public class MyPrototypeBean {
     public int getRandom() {
    	return new Random().nextInt(10);
     }
}
