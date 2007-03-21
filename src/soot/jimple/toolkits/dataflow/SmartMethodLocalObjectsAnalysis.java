package soot.jimple.toolkits.dataflow;

import soot.*;
import java.util.*;
import soot.toolkits.graph.*;
import soot.jimple.*;

// SmartMethodLocalObjectsAnalysis written by Richard L. Halpert, 2007-02-23
// Uses a SmartMethodDataFlowAnalysis to determine if a Local or FieldRef is
// LOCAL or SHARED in the given method.

public class SmartMethodLocalObjectsAnalysis
{
	public static int counter = 0;
	
	SootMethod method;
	SmartMethodDataFlowAnalysis smdfa;
	boolean printMessages;

	public SmartMethodLocalObjectsAnalysis(UnitGraph g, DataFlowAnalysis dfa)
	{
		this.method = g.getBody().getMethod();
		this.smdfa = dfa.getMethodDataFlowAnalysis(method);
		this.printMessages = false;
		
		counter++;
	}
	
	public Value getThisLocal()
	{
		return smdfa.getThisLocal();
	}
	
	// 
	public boolean isObjectLocal(Value local, CallLocalityContext context) // to this analysis of this method (which depends on context)
	{
		List sources = smdfa.sourcesOf(new EquivalentValue(local));
		Iterator sourcesIt = sources.iterator();
		while(sourcesIt.hasNext())
		{
			EquivalentValue source = (EquivalentValue) sourcesIt.next();
			if(source.getValue() instanceof Ref)
			{
				if(!context.isFieldLocal(source))
				{
					if(printMessages)
						G.v().out.println("      Requested value " + local + " is LOCAL in " + method + " ");
					return false;
				}
			}
		}
		if(printMessages)
			G.v().out.println("      Requested value " + local + " is SHARED in " + method + " ");
		return true;
	}
}
