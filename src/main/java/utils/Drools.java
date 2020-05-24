package utils;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;

import java.util.concurrent.ThreadLocalRandom;

public class Drools {

    private static KieSession workingMemory;

    public static KieSession getWorkingMemory()
    {
        if( workingMemory == null )
        {
            KieServices ks = KieServices.Factory.get();
            KieContainer kc = ks.getKieClasspathContainer();
            workingMemory = kc.newKieSession();
        }

        return workingMemory;
    }

    public static void updateObject( Object object )
    {
        FactHandle factHandle = getWorkingMemory().getFactHandle( object );
        getWorkingMemory().update( factHandle, object );
    }
}
