package soot.jimple.paddle;

import soot.jimple.paddle.queue.*;
import soot.jimple.paddle.bdddomains.*;
import soot.*;

public class BDDReachableMethods extends AbsReachableMethods {
    private final jedd.internal.RelationContainer reachables =
      new jedd.internal.RelationContainer(new jedd.Attribute[] { ctxt.v(), method.v() },
                                          new jedd.PhysicalDomain[] { C2.v(), MS.v() },
                                          ("private <soot.jimple.paddle.bdddomains.ctxt, soot.jimple.pad" +
                                           "dle.bdddomains.method> reachables at /tmp/fixing-paddle/src/" +
                                           "soot/jimple/paddle/BDDReachableMethods.jedd:30,12-26"));
    
    private AbsCallGraph cg;
    
    private Rctxt_method newMethods;
    
    BDDReachableMethods(Rsrcc_srcm_stmt_kind_tgtc_tgtm edgesIn,
                        Rctxt_method methodsIn,
                        Qctxt_method out,
                        AbsCallGraph cg) {
        super(edgesIn, methodsIn, out);
        this.cg = cg;
        newMethods = out.reader("bddrm");
    }
    
    public boolean update() {
        boolean change = false;
        if (methodsIn != null) {
            final jedd.internal.RelationContainer newMethodsIn =
              new jedd.internal.RelationContainer(new jedd.Attribute[] { ctxt.v(), method.v() },
                                                  new jedd.PhysicalDomain[] { C1.v(), MS.v() },
                                                  ("<soot.jimple.paddle.bdddomains.ctxt:soot.jimple.paddle.bdddo" +
                                                   "mains.C1, soot.jimple.paddle.bdddomains.method:soot.jimple.p" +
                                                   "addle.bdddomains.MS> newMethodsIn = methodsIn.get(); at /tmp" +
                                                   "/fixing-paddle/src/soot/jimple/paddle/BDDReachableMethods.je" +
                                                   "dd:42,27-39"),
                                                  methodsIn.get());
            newMethodsIn.eqMinus(jedd.internal.Jedd.v().replace(reachables,
                                                                new jedd.PhysicalDomain[] { C2.v() },
                                                                new jedd.PhysicalDomain[] { C1.v() }));
            if (!jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(newMethodsIn),
                                               jedd.internal.Jedd.v().falseBDD()))
                change = true;
            reachables.eqUnion(jedd.internal.Jedd.v().replace(newMethodsIn,
                                                              new jedd.PhysicalDomain[] { C1.v() },
                                                              new jedd.PhysicalDomain[] { C2.v() }));
            out.add(new jedd.internal.RelationContainer(new jedd.Attribute[] { ctxt.v(), method.v() },
                                                        new jedd.PhysicalDomain[] { C1.v(), MS.v() },
                                                        ("out.add(newMethodsIn) at /tmp/fixing-paddle/src/soot/jimple/" +
                                                         "paddle/BDDReachableMethods.jedd:46,12-15"),
                                                        newMethodsIn));
        }
        final jedd.internal.RelationContainer newEdges =
          new jedd.internal.RelationContainer(new jedd.Attribute[] { srcc.v(), srcm.v(), stmt.v(), kind.v(), tgtc.v(), tgtm.v() },
                                              new jedd.PhysicalDomain[] { C1.v(), MS.v(), ST.v(), KD.v(), C2.v(), MT.v() },
                                              ("<soot.jimple.paddle.bdddomains.srcc:soot.jimple.paddle.bdddo" +
                                               "mains.C1, soot.jimple.paddle.bdddomains.srcm:soot.jimple.pad" +
                                               "dle.bdddomains.MS, soot.jimple.paddle.bdddomains.stmt:soot.j" +
                                               "imple.paddle.bdddomains.ST, soot.jimple.paddle.bdddomains.ki" +
                                               "nd:soot.jimple.paddle.bdddomains.KD, soot.jimple.paddle.bddd" +
                                               "omains.tgtc:soot.jimple.paddle.bdddomains.C2, soot.jimple.pa" +
                                               "ddle.bdddomains.tgtm:soot.jimple.paddle.bdddomains.MT> newEd" +
                                               "ges = jedd.internal.Jedd.v().join(jedd.internal.Jedd.v().rea" +
                                               "d(edgesIn.get()), jedd.internal.Jedd.v().replace(reachables," +
                                               " new jedd.PhysicalDomain[...], new jedd.PhysicalDomain[...])" +
                                               ", new jedd.PhysicalDomain[...]); at /tmp/fixing-paddle/src/s" +
                                               "oot/jimple/paddle/BDDReachableMethods.jedd:49,45-53"),
                                              jedd.internal.Jedd.v().join(jedd.internal.Jedd.v().read(edgesIn.get()),
                                                                          jedd.internal.Jedd.v().replace(reachables,
                                                                                                         new jedd.PhysicalDomain[] { C2.v() },
                                                                                                         new jedd.PhysicalDomain[] { C1.v() }),
                                                                          new jedd.PhysicalDomain[] { C1.v(), MS.v() }));
        newEdges.eqUnion(cg.edgesOutOf(newMethods).get());
        while (!jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(newEdges),
                                              jedd.internal.Jedd.v().falseBDD())) {
            final jedd.internal.RelationContainer newTargets =
              new jedd.internal.RelationContainer(new jedd.Attribute[] { ctxt.v(), method.v() },
                                                  new jedd.PhysicalDomain[] { C2.v(), MS.v() },
                                                  ("<soot.jimple.paddle.bdddomains.ctxt:soot.jimple.paddle.bdddo" +
                                                   "mains.C2, soot.jimple.paddle.bdddomains.method:soot.jimple.p" +
                                                   "addle.bdddomains.MS> newTargets = jedd.internal.Jedd.v().rep" +
                                                   "lace(jedd.internal.Jedd.v().project(newEdges, new jedd.Physi" +
                                                   "calDomain[...]), new jedd.PhysicalDomain[...], new jedd.Phys" +
                                                   "icalDomain[...]); at /tmp/fixing-paddle/src/soot/jimple/padd" +
                                                   "le/BDDReachableMethods.jedd:55,27-37"),
                                                  jedd.internal.Jedd.v().replace(jedd.internal.Jedd.v().project(newEdges,
                                                                                                                new jedd.PhysicalDomain[] { MS.v(), C1.v(), ST.v(), KD.v() }),
                                                                                 new jedd.PhysicalDomain[] { MT.v() },
                                                                                 new jedd.PhysicalDomain[] { MS.v() }));
            newTargets.eqMinus(reachables);
            out.add(new jedd.internal.RelationContainer(new jedd.Attribute[] { ctxt.v(), method.v() },
                                                        new jedd.PhysicalDomain[] { C1.v(), MS.v() },
                                                        ("out.add(jedd.internal.Jedd.v().replace(newTargets, new jedd." +
                                                         "PhysicalDomain[...], new jedd.PhysicalDomain[...])) at /tmp/" +
                                                         "fixing-paddle/src/soot/jimple/paddle/BDDReachableMethods.jed" +
                                                         "d:58,12-15"),
                                                        jedd.internal.Jedd.v().replace(newTargets,
                                                                                       new jedd.PhysicalDomain[] { C2.v() },
                                                                                       new jedd.PhysicalDomain[] { C1.v() })));
            if (!jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(newTargets),
                                               jedd.internal.Jedd.v().falseBDD()))
                change = true;
            reachables.eqUnion(newTargets);
            newEdges.eq(cg.edgesOutOf(newMethods).get());
        }
        return change;
    }
    
    boolean add(MethodOrMethodContext m) {
        final jedd.internal.RelationContainer newM =
          new jedd.internal.RelationContainer(new jedd.Attribute[] { ctxt.v(), method.v() },
                                              new jedd.PhysicalDomain[] { C1.v(), MS.v() },
                                              ("<soot.jimple.paddle.bdddomains.ctxt:soot.jimple.paddle.bdddo" +
                                               "mains.C1, soot.jimple.paddle.bdddomains.method:soot.jimple.p" +
                                               "addle.bdddomains.MS> newM = jedd.internal.Jedd.v().literal(n" +
                                               "ew java.lang.Object[...], new jedd.Attribute[...], new jedd." +
                                               "PhysicalDomain[...]); at /tmp/fixing-paddle/src/soot/jimple/" +
                                               "paddle/BDDReachableMethods.jedd:66,23-27"),
                                              jedd.internal.Jedd.v().literal(new Object[] { m.context(), m.method() },
                                                                             new jedd.Attribute[] { ctxt.v(), method.v() },
                                                                             new jedd.PhysicalDomain[] { C1.v(), MS.v() }));
        final jedd.internal.RelationContainer newReachables =
          new jedd.internal.RelationContainer(new jedd.Attribute[] { ctxt.v(), method.v() },
                                              new jedd.PhysicalDomain[] { C1.v(), MS.v() },
                                              ("<soot.jimple.paddle.bdddomains.ctxt:soot.jimple.paddle.bdddo" +
                                               "mains.C1, soot.jimple.paddle.bdddomains.method:soot.jimple.p" +
                                               "addle.bdddomains.MS> newReachables = jedd.internal.Jedd.v()." +
                                               "union(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().rep" +
                                               "lace(reachables, new jedd.PhysicalDomain[...], new jedd.Phys" +
                                               "icalDomain[...])), newM); at /tmp/fixing-paddle/src/soot/jim" +
                                               "ple/paddle/BDDReachableMethods.jedd:67,23-36"),
                                              jedd.internal.Jedd.v().union(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().replace(reachables,
                                                                                                                                      new jedd.PhysicalDomain[] { C2.v() },
                                                                                                                                      new jedd.PhysicalDomain[] { C1.v() })),
                                                                           newM));
        if (!jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().replace(newReachables,
                                                                                                      new jedd.PhysicalDomain[] { C1.v() },
                                                                                                      new jedd.PhysicalDomain[] { C2.v() })),
                                           reachables)) {
            reachables.eq(jedd.internal.Jedd.v().replace(newReachables,
                                                         new jedd.PhysicalDomain[] { C1.v() },
                                                         new jedd.PhysicalDomain[] { C2.v() }));
            out.add(new jedd.internal.RelationContainer(new jedd.Attribute[] { ctxt.v(), method.v() },
                                                        new jedd.PhysicalDomain[] { C1.v(), MS.v() },
                                                        ("out.add(newM) at /tmp/fixing-paddle/src/soot/jimple/paddle/B" +
                                                         "DDReachableMethods.jedd:70,12-15"),
                                                        newM));
            return true;
        }
        return false;
    }
    
    int size() {
        return (int)
                 new jedd.internal.RelationContainer(new jedd.Attribute[] { ctxt.v(), method.v() },
                                                     new jedd.PhysicalDomain[] { C2.v(), MS.v() },
                                                     ("reachables.size() at /tmp/fixing-paddle/src/soot/jimple/padd" +
                                                      "le/BDDReachableMethods.jedd:76,21-31"),
                                                     reachables).size();
    }
    
    boolean contains(MethodOrMethodContext m) {
        final jedd.internal.RelationContainer newM =
          new jedd.internal.RelationContainer(new jedd.Attribute[] { ctxt.v(), method.v() },
                                              new jedd.PhysicalDomain[] { C2.v(), MS.v() },
                                              ("<soot.jimple.paddle.bdddomains.ctxt:soot.jimple.paddle.bdddo" +
                                               "mains.C2, soot.jimple.paddle.bdddomains.method:soot.jimple.p" +
                                               "addle.bdddomains.MS> newM = jedd.internal.Jedd.v().literal(n" +
                                               "ew java.lang.Object[...], new jedd.Attribute[...], new jedd." +
                                               "PhysicalDomain[...]); at /tmp/fixing-paddle/src/soot/jimple/" +
                                               "paddle/BDDReachableMethods.jedd:79,23-27"),
                                              jedd.internal.Jedd.v().literal(new Object[] { m.context(), m.method() },
                                                                             new jedd.Attribute[] { ctxt.v(), method.v() },
                                                                             new jedd.PhysicalDomain[] { C2.v(), MS.v() }));
        return !jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().intersect(jedd.internal.Jedd.v().read(reachables),
                                                                                                           newM)),
                                              jedd.internal.Jedd.v().falseBDD());
    }
}
