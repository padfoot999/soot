package soot.jimple.paddle.queue;

import soot.util.*;
import soot.jimple.paddle.bdddomains.*;
import soot.jimple.paddle.*;
import soot.jimple.toolkits.callgraph.*;
import soot.*;
import soot.util.queue.*;
import jedd.*;
import java.util.*;

public final class Qvarc_var_objc_objBDD extends Qvarc_var_objc_obj {
    public Qvarc_var_objc_objBDD(String name) { super(name); }
    
    private LinkedList readers = new LinkedList();
    
    public void add(Context _varc, VarNode _var, Context _objc, AllocNode _obj) {
        add(new jedd.internal.RelationContainer(new Attribute[] { varc.v(), var.v(), objc.v(), obj.v() },
                                                new PhysicalDomain[] { C1.v(), V1.v(), C2.v(), H1.v() },
                                                ("add(jedd.internal.Jedd.v().literal(new java.lang.Object[...]" +
                                                 ", new jedd.Attribute[...], new jedd.PhysicalDomain[...])) at" +
                                                 " /tmp/fixing-paddle/src/soot/jimple/paddle/queue/Qvarc_var_o" +
                                                 "bjc_objBDD.jedd:34,8-11"),
                                                jedd.internal.Jedd.v().literal(new Object[] { _varc, _var, _objc, _obj },
                                                                               new Attribute[] { varc.v(), var.v(), objc.v(), obj.v() },
                                                                               new PhysicalDomain[] { C1.v(), V1.v(), C2.v(), H1.v() })));
    }
    
    public void add(final jedd.internal.RelationContainer in) {
        if (!jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(in), jedd.internal.Jedd.v().falseBDD()))
            invalidate();
        for (Iterator it = readers.iterator(); it.hasNext(); ) {
            Rvarc_var_objc_objBDD reader = (Rvarc_var_objc_objBDD) it.next();
            reader.add(new jedd.internal.RelationContainer(new Attribute[] { obj.v(), var.v(), varc.v(), objc.v() },
                                                           new PhysicalDomain[] { H1.v(), V1.v(), C1.v(), C2.v() },
                                                           ("reader.add(in) at /tmp/fixing-paddle/src/soot/jimple/paddle/" +
                                                            "queue/Qvarc_var_objc_objBDD.jedd:40,12-18"),
                                                           in));
        }
    }
    
    public Rvarc_var_objc_obj reader(String rname) {
        Rvarc_var_objc_obj ret = new Rvarc_var_objc_objBDD(name + ":" + rname);
        readers.add(ret);
        return ret;
    }
}
