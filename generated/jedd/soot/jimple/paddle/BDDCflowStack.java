package soot.jimple.paddle;

import soot.*;
import soot.jimple.*;
import soot.jimple.paddle.queue.*;
import soot.jimple.paddle.bdddomains.*;
import java.util.*;

public class BDDCflowStack {
    BDDCflow cflow;
    
    public BDDCflowStack(BDDCflow cflow, Collection shadows, Collection isValids) {
        super();
        this.cflow = cflow;
        for (Iterator sIt = isValids.iterator(); sIt.hasNext(); ) {
            final Stmt s = (Stmt) sIt.next();
            Scene.v().getUnitNumberer().add(s);
            this.isValids.eqUnion(jedd.internal.Jedd.v().literal(new Object[] { s },
                                                                 new jedd.Attribute[] { stmt.v() },
                                                                 new jedd.PhysicalDomain[] { ST.v() }));
        }
        for (Iterator sIt = shadows.iterator(); sIt.hasNext(); ) {
            final Shadow s = (Shadow) sIt.next();
            ShadowNumberer.v().add(s);
            Scene.v().getUnitNumberer().add(s.pushStmt());
            Scene.v().getUnitNumberer().add(s.popStmt());
            this.shadows.eqUnion(jedd.internal.Jedd.v().literal(new Object[] { s },
                                                                new jedd.Attribute[] { shadow.v() },
                                                                new jedd.PhysicalDomain[] { V1.v() }));
        }
    }
    
    private static final boolean DEBUG = false;
    
    private void debug(String s) { if (DEBUG) System.err.println(s); }
    
    private jedd.internal.RelationContainer within(Shadow sh) {
        debug("Doing within " + sh);
        final jedd.internal.RelationContainer ret =
          new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                              new jedd.PhysicalDomain[] { ST.v() },
                                              ("<soot.jimple.paddle.bdddomains.stmt:soot.jimple.paddle.bdddo" +
                                               "mains.ST> ret = jedd.internal.Jedd.v().falseBDD(); at /tmp/f" +
                                               "ixing-paddle/src/soot/jimple/paddle/BDDCflowStack.jedd:57,15" +
                                               "-18"),
                                              jedd.internal.Jedd.v().falseBDD());
        boolean inShadow = false;
        for (Iterator sIt = sh.method().getActiveBody().getUnits().iterator(); sIt.hasNext(); ) {
            final Stmt s = (Stmt) sIt.next();
            if (s == sh.popStmt()) inShadow = false;
            if (inShadow) {
                ret.eqUnion(jedd.internal.Jedd.v().literal(new Object[] { s },
                                                           new jedd.Attribute[] { stmt.v() },
                                                           new jedd.PhysicalDomain[] { ST.v() }));
                debug("within: " + s);
            }
            if (s == sh.pushStmt()) inShadow = true;
        }
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                                   new jedd.PhysicalDomain[] { ST.v() },
                                                   ("return ret; at /tmp/fixing-paddle/src/soot/jimple/paddle/BDD" +
                                                    "CflowStack.jedd:68,8-14"),
                                                   ret);
    }
    
    private jedd.internal.RelationContainer targetsOf(final jedd.internal.RelationContainer calls) {
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { method.v() },
                                                   new jedd.PhysicalDomain[] { MT.v() },
                                                   ("return jedd.internal.Jedd.v().compose(jedd.internal.Jedd.v()" +
                                                    ".read(jedd.internal.Jedd.v().project(cflow.callGraph(), new " +
                                                    "jedd.PhysicalDomain[...])), calls, new jedd.PhysicalDomain[." +
                                                    "..]); at /tmp/fixing-paddle/src/soot/jimple/paddle/BDDCflowS" +
                                                    "tack.jedd:72,8-14"),
                                                   jedd.internal.Jedd.v().compose(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().project(cflow.callGraph(),
                                                                                                                                             new jedd.PhysicalDomain[] { MS.v() })),
                                                                                  calls,
                                                                                  new jedd.PhysicalDomain[] { ST.v() }));
    }
    
    private jedd.internal.RelationContainer targetsOfShadow(final jedd.internal.RelationContainer calls) {
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { method.v(), shadow.v() },
                                                   new jedd.PhysicalDomain[] { MT.v(), V1.v() },
                                                   ("return jedd.internal.Jedd.v().compose(jedd.internal.Jedd.v()" +
                                                    ".read(jedd.internal.Jedd.v().project(cflow.callGraph(), new " +
                                                    "jedd.PhysicalDomain[...])), calls, new jedd.PhysicalDomain[." +
                                                    "..]); at /tmp/fixing-paddle/src/soot/jimple/paddle/BDDCflowS" +
                                                    "tack.jedd:76,8-14"),
                                                   jedd.internal.Jedd.v().compose(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().project(cflow.callGraph(),
                                                                                                                                             new jedd.PhysicalDomain[] { MS.v() })),
                                                                                  calls,
                                                                                  new jedd.PhysicalDomain[] { ST.v() }));
    }
    
    private jedd.internal.RelationContainer stmtsIn(final jedd.internal.RelationContainer methods) {
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                                   new jedd.PhysicalDomain[] { ST.v() },
                                                   ("return jedd.internal.Jedd.v().compose(jedd.internal.Jedd.v()" +
                                                    ".read(cflow.stmtMethod()), methods, new jedd.PhysicalDomain[" +
                                                    "...]); at /tmp/fixing-paddle/src/soot/jimple/paddle/BDDCflow" +
                                                    "Stack.jedd:80,8-14"),
                                                   jedd.internal.Jedd.v().compose(jedd.internal.Jedd.v().read(cflow.stmtMethod()),
                                                                                  methods,
                                                                                  new jedd.PhysicalDomain[] { MT.v() }));
    }
    
    private jedd.internal.RelationContainer stmtsInShadow(final jedd.internal.RelationContainer methods) {
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v(), shadow.v() },
                                                   new jedd.PhysicalDomain[] { ST.v(), V1.v() },
                                                   ("return jedd.internal.Jedd.v().compose(jedd.internal.Jedd.v()" +
                                                    ".read(cflow.stmtMethod()), methods, new jedd.PhysicalDomain[" +
                                                    "...]); at /tmp/fixing-paddle/src/soot/jimple/paddle/BDDCflow" +
                                                    "Stack.jedd:84,8-14"),
                                                   jedd.internal.Jedd.v().compose(jedd.internal.Jedd.v().read(cflow.stmtMethod()),
                                                                                  methods,
                                                                                  new jedd.PhysicalDomain[] { MT.v() }));
    }
    
    private jedd.internal.RelationContainer mayCflow() {
        final jedd.internal.RelationContainer ret =
          new jedd.internal.RelationContainer(new jedd.Attribute[] { shadow.v(), stmt.v() },
                                              new jedd.PhysicalDomain[] { V1.v(), ST.v() },
                                              ("<soot.jimple.paddle.bdddomains.shadow:soot.jimple.paddle.bdd" +
                                               "domains.V1, soot.jimple.paddle.bdddomains.stmt:soot.jimple.p" +
                                               "addle.bdddomains.ST> ret = jedd.internal.Jedd.v().falseBDD()" +
                                               "; at /tmp/fixing-paddle/src/soot/jimple/paddle/BDDCflowStack" +
                                               ".jedd:88,23-26"),
                                              jedd.internal.Jedd.v().falseBDD());
        for (Iterator shIt =
               new jedd.internal.RelationContainer(new jedd.Attribute[] { shadow.v() },
                                                   new jedd.PhysicalDomain[] { V1.v() },
                                                   ("shadows.iterator() at /tmp/fixing-paddle/src/soot/jimple/pad" +
                                                    "dle/BDDCflowStack.jedd:89,29-36"),
                                                   shadows).iterator();
             shIt.hasNext();
             ) {
            final Shadow sh = (Shadow) shIt.next();
            ret.eqUnion(jedd.internal.Jedd.v().join(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().literal(new Object[] { sh },
                                                                                                               new jedd.Attribute[] { shadow.v() },
                                                                                                               new jedd.PhysicalDomain[] { V1.v() })),
                                                    within(sh),
                                                    new jedd.PhysicalDomain[] {  }));
        }
        while (true) {
            final jedd.internal.RelationContainer targets =
              new jedd.internal.RelationContainer(new jedd.Attribute[] { shadow.v(), method.v() },
                                                  new jedd.PhysicalDomain[] { V1.v(), MT.v() },
                                                  ("<soot.jimple.paddle.bdddomains.shadow:soot.jimple.paddle.bdd" +
                                                   "domains.V1, soot.jimple.paddle.bdddomains.method:soot.jimple" +
                                                   ".paddle.bdddomains.MT> targets = targetsOfShadow(new jedd.in" +
                                                   "ternal.RelationContainer(...)); at /tmp/fixing-paddle/src/so" +
                                                   "ot/jimple/paddle/BDDCflowStack.jedd:94,29-36"),
                                                  targetsOfShadow(new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v(), shadow.v() },
                                                                                                      new jedd.PhysicalDomain[] { ST.v(), V1.v() },
                                                                                                      ("targetsOfShadow(ret) at /tmp/fixing-paddle/src/soot/jimple/p" +
                                                                                                       "addle/BDDCflowStack.jedd:94,39-54"),
                                                                                                      ret)));
            if (jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(ret),
                                              ret.eqUnion(stmtsInShadow(new jedd.internal.RelationContainer(new jedd.Attribute[] { shadow.v(), method.v() },
                                                                                                            new jedd.PhysicalDomain[] { V1.v(), MT.v() },
                                                                                                            ("stmtsInShadow(targets) at /tmp/fixing-paddle/src/soot/jimple" +
                                                                                                             "/paddle/BDDCflowStack.jedd:95,31-44"),
                                                                                                            targets)))))
                break;
        }
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v(), shadow.v() },
                                                   new jedd.PhysicalDomain[] { ST.v(), V1.v() },
                                                   ("return ret; at /tmp/fixing-paddle/src/soot/jimple/paddle/BDD" +
                                                    "CflowStack.jedd:97,8-14"),
                                                   ret);
    }
    
    private jedd.internal.RelationContainer mustCflow() {
        final jedd.internal.RelationContainer ret =
          new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                              new jedd.PhysicalDomain[] { ST.v() },
                                              ("<soot.jimple.paddle.bdddomains.stmt:soot.jimple.paddle.bdddo" +
                                               "mains.ST> ret = jedd.internal.Jedd.v().falseBDD(); at /tmp/f" +
                                               "ixing-paddle/src/soot/jimple/paddle/BDDCflowStack.jedd:101,1" +
                                               "5-18"),
                                              jedd.internal.Jedd.v().falseBDD());
        for (Iterator shIt =
               new jedd.internal.RelationContainer(new jedd.Attribute[] { shadow.v() },
                                                   new jedd.PhysicalDomain[] { V1.v() },
                                                   ("shadows.iterator() at /tmp/fixing-paddle/src/soot/jimple/pad" +
                                                    "dle/BDDCflowStack.jedd:102,29-36"),
                                                   shadows).iterator();
             shIt.hasNext();
             ) {
            final Shadow sh = (Shadow) shIt.next();
            if (sh.unconditional()) { ret.eqUnion(within(sh)); }
        }
        while (true) {
            final jedd.internal.RelationContainer methods =
              new jedd.internal.RelationContainer(new jedd.Attribute[] { method.v() },
                                                  new jedd.PhysicalDomain[] { MT.v() },
                                                  ("<soot.jimple.paddle.bdddomains.method:soot.jimple.paddle.bdd" +
                                                   "domains.MT> methods = jedd.internal.Jedd.v().minus(jedd.inte" +
                                                   "rnal.Jedd.v().read(targetsOf(new jedd.internal.RelationConta" +
                                                   "iner(...))), targetsOf(new jedd.internal.RelationContainer(." +
                                                   "..))); at /tmp/fixing-paddle/src/soot/jimple/paddle/BDDCflow" +
                                                   "Stack.jedd:109,21-28"),
                                                  jedd.internal.Jedd.v().minus(jedd.internal.Jedd.v().read(targetsOf(new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                                                                                                                                         new jedd.PhysicalDomain[] { ST.v() },
                                                                                                                                                         ("targetsOf(ret) at /tmp/fixing-paddle/src/soot/jimple/paddle/" +
                                                                                                                                                          "BDDCflowStack.jedd:109,31-40"),
                                                                                                                                                         ret))),
                                                                               targetsOf(new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                                                                                                             new jedd.PhysicalDomain[] { ST.v() },
                                                                                                                             ("targetsOf(jedd.internal.Jedd.v().minus(jedd.internal.Jedd.v(" +
                                                                                                                              ").read(jedd.internal.Jedd.v().trueBDD()), ret)) at /tmp/fixi" +
                                                                                                                              "ng-paddle/src/soot/jimple/paddle/BDDCflowStack.jedd:109,48-5" +
                                                                                                                              "7"),
                                                                                                                             jedd.internal.Jedd.v().minus(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().trueBDD()),
                                                                                                                                                          ret)))));
            if (jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(ret),
                                              ret.eqUnion(stmtsIn(new jedd.internal.RelationContainer(new jedd.Attribute[] { method.v() },
                                                                                                      new jedd.PhysicalDomain[] { MT.v() },
                                                                                                      ("stmtsIn(methods) at /tmp/fixing-paddle/src/soot/jimple/paddl" +
                                                                                                       "e/BDDCflowStack.jedd:110,31-38"),
                                                                                                      methods)))))
                break;
        }
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                                   new jedd.PhysicalDomain[] { ST.v() },
                                                   ("return ret; at /tmp/fixing-paddle/src/soot/jimple/paddle/BDD" +
                                                    "CflowStack.jedd:112,8-14"),
                                                   ret);
    }
    
    private final jedd.internal.RelationContainer shadows =
      new jedd.internal.RelationContainer(new jedd.Attribute[] { shadow.v() },
                                          new jedd.PhysicalDomain[] { V1.v() },
                                          ("private <soot.jimple.paddle.bdddomains.shadow:soot.jimple.pa" +
                                           "ddle.bdddomains.V1> shadows = jedd.internal.Jedd.v().falseBD" +
                                           "D() at /tmp/fixing-paddle/src/soot/jimple/paddle/BDDCflowSta" +
                                           "ck.jedd:115,12-23"),
                                          jedd.internal.Jedd.v().falseBDD());
    
    private final jedd.internal.RelationContainer mustCflow =
      new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                          new jedd.PhysicalDomain[] { ST.v() },
                                          ("private <soot.jimple.paddle.bdddomains.stmt> mustCflow = jed" +
                                           "d.internal.Jedd.v().trueBDD() at /tmp/fixing-paddle/src/soot" +
                                           "/jimple/paddle/BDDCflowStack.jedd:116,12-18"),
                                          jedd.internal.Jedd.v().trueBDD());
    
    private final jedd.internal.RelationContainer mayCflow =
      new jedd.internal.RelationContainer(new jedd.Attribute[] { shadow.v(), stmt.v() },
                                          new jedd.PhysicalDomain[] { V1.v(), ST.v() },
                                          ("private <soot.jimple.paddle.bdddomains.shadow, soot.jimple.p" +
                                           "addle.bdddomains.stmt> mayCflow = jedd.internal.Jedd.v().tru" +
                                           "eBDD() at /tmp/fixing-paddle/src/soot/jimple/paddle/BDDCflow" +
                                           "Stack.jedd:117,12-26"),
                                          jedd.internal.Jedd.v().trueBDD());
    
    private final jedd.internal.RelationContainer isValids =
      new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                          new jedd.PhysicalDomain[] { ST.v() },
                                          ("private <soot.jimple.paddle.bdddomains.stmt> isValids = jedd" +
                                           ".internal.Jedd.v().falseBDD() at /tmp/fixing-paddle/src/soot" +
                                           "/jimple/paddle/BDDCflowStack.jedd:118,12-18"),
                                          jedd.internal.Jedd.v().falseBDD());
    
    private final jedd.internal.RelationContainer neverValid =
      new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                          new jedd.PhysicalDomain[] { ST.v() },
                                          ("private <soot.jimple.paddle.bdddomains.stmt> neverValid = je" +
                                           "dd.internal.Jedd.v().falseBDD() at /tmp/fixing-paddle/src/so" +
                                           "ot/jimple/paddle/BDDCflowStack.jedd:119,12-18"),
                                          jedd.internal.Jedd.v().falseBDD());
    
    private final jedd.internal.RelationContainer alwaysValid =
      new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                          new jedd.PhysicalDomain[] { ST.v() },
                                          ("private <soot.jimple.paddle.bdddomains.stmt> alwaysValid = j" +
                                           "edd.internal.Jedd.v().falseBDD() at /tmp/fixing-paddle/src/s" +
                                           "oot/jimple/paddle/BDDCflowStack.jedd:120,12-18"),
                                          jedd.internal.Jedd.v().falseBDD());
    
    public boolean neverValid(Stmt s) {
        if (jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(mayCflow), jedd.internal.Jedd.v().trueBDD()))
            mayCflow.eq(mayCflow());
        return jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().join(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().literal(new Object[] { s },
                                                                                                                                                                new jedd.Attribute[] { stmt.v() },
                                                                                                                                                                new jedd.PhysicalDomain[] { ST.v() })),
                                                                                                     jedd.internal.Jedd.v().project(mayCflow,
                                                                                                                                    new jedd.PhysicalDomain[] { V1.v() }),
                                                                                                     new jedd.PhysicalDomain[] { ST.v() })),
                                             jedd.internal.Jedd.v().falseBDD());
    }
    
    public boolean alwaysValid(Stmt s) {
        if (jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(mustCflow), jedd.internal.Jedd.v().trueBDD()))
            mustCflow.eq(mustCflow());
        return !jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().join(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().literal(new Object[] { s },
                                                                                                                                                                 new jedd.Attribute[] { stmt.v() },
                                                                                                                                                                 new jedd.PhysicalDomain[] { ST.v() })),
                                                                                                      mustCflow,
                                                                                                      new jedd.PhysicalDomain[] { ST.v() })),
                                              jedd.internal.Jedd.v().falseBDD());
    }
    
    private jedd.internal.RelationContainer computeNeverValid() {
        if (!jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(neverValid), jedd.internal.Jedd.v().falseBDD()))
            return new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                                       new jedd.PhysicalDomain[] { ST.v() },
                                                       ("return neverValid; at /tmp/fixing-paddle/src/soot/jimple/pad" +
                                                        "dle/BDDCflowStack.jedd:139,29-35"),
                                                       neverValid);
        if (jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(mayCflow), jedd.internal.Jedd.v().trueBDD()))
            mayCflow.eq(mayCflow());
        final jedd.internal.RelationContainer mayBeValid =
          new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                              new jedd.PhysicalDomain[] { ST.v() },
                                              ("<soot.jimple.paddle.bdddomains.stmt:soot.jimple.paddle.bdddo" +
                                               "mains.ST> mayBeValid = jedd.internal.Jedd.v().join(jedd.inte" +
                                               "rnal.Jedd.v().read(jedd.internal.Jedd.v().project(mayCflow, " +
                                               "new jedd.PhysicalDomain[...])), isValids, new jedd.PhysicalD" +
                                               "omain[...]); at /tmp/fixing-paddle/src/soot/jimple/paddle/BD" +
                                               "DCflowStack.jedd:141,15-25"),
                                              jedd.internal.Jedd.v().join(jedd.internal.Jedd.v().read(jedd.internal.Jedd.v().project(mayCflow,
                                                                                                                                     new jedd.PhysicalDomain[] { V1.v() })),
                                                                          isValids,
                                                                          new jedd.PhysicalDomain[] { ST.v() }));
        neverValid.eq(jedd.internal.Jedd.v().minus(jedd.internal.Jedd.v().read(isValids), mayBeValid));
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                                   new jedd.PhysicalDomain[] { ST.v() },
                                                   ("return neverValid; at /tmp/fixing-paddle/src/soot/jimple/pad" +
                                                    "dle/BDDCflowStack.jedd:143,8-14"),
                                                   neverValid);
    }
    
    public Iterator neverValid() {
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                                   new jedd.PhysicalDomain[] { ST.v() },
                                                   ("computeNeverValid().iterator() at /tmp/fixing-paddle/src/soo" +
                                                    "t/jimple/paddle/BDDCflowStack.jedd:149,35-43"),
                                                   computeNeverValid()).iterator();
    }
    
    private jedd.internal.RelationContainer computeAlwaysValid() {
        if (jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(mustCflow), jedd.internal.Jedd.v().trueBDD()))
            mustCflow.eq(mustCflow());
        alwaysValid.eq(jedd.internal.Jedd.v().join(jedd.internal.Jedd.v().read(mustCflow),
                                                   isValids,
                                                   new jedd.PhysicalDomain[] { ST.v() }));
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                                   new jedd.PhysicalDomain[] { ST.v() },
                                                   ("return alwaysValid; at /tmp/fixing-paddle/src/soot/jimple/pa" +
                                                    "ddle/BDDCflowStack.jedd:155,8-14"),
                                                   alwaysValid);
    }
    
    public Iterator alwaysValid() {
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                                   new jedd.PhysicalDomain[] { ST.v() },
                                                   ("computeAlwaysValid().iterator() at /tmp/fixing-paddle/src/so" +
                                                    "ot/jimple/paddle/BDDCflowStack.jedd:162,36-44"),
                                                   computeAlwaysValid()).iterator();
    }
    
    public Iterator unnecessaryShadows() {
        final jedd.internal.RelationContainer interestingIsValids =
          new jedd.internal.RelationContainer(new jedd.Attribute[] { stmt.v() },
                                              new jedd.PhysicalDomain[] { ST.v() },
                                              ("<soot.jimple.paddle.bdddomains.stmt:soot.jimple.paddle.bdddo" +
                                               "mains.ST> interestingIsValids = isValids; at /tmp/fixing-pad" +
                                               "dle/src/soot/jimple/paddle/BDDCflowStack.jedd:171,15-34"),
                                              isValids);
        interestingIsValids.eqMinus(computeAlwaysValid());
        interestingIsValids.eqMinus(computeNeverValid());
        if (jedd.internal.Jedd.v().equals(jedd.internal.Jedd.v().read(mayCflow), jedd.internal.Jedd.v().trueBDD()))
            mayCflow.eq(mayCflow());
        final jedd.internal.RelationContainer necessaryShadows =
          new jedd.internal.RelationContainer(new jedd.Attribute[] { shadow.v() },
                                              new jedd.PhysicalDomain[] { V1.v() },
                                              ("<soot.jimple.paddle.bdddomains.shadow:soot.jimple.paddle.bdd" +
                                               "domains.V1> necessaryShadows = jedd.internal.Jedd.v().compos" +
                                               "e(jedd.internal.Jedd.v().read(mayCflow), interestingIsValids" +
                                               ", new jedd.PhysicalDomain[...]); at /tmp/fixing-paddle/src/s" +
                                               "oot/jimple/paddle/BDDCflowStack.jedd:175,17-33"),
                                              jedd.internal.Jedd.v().compose(jedd.internal.Jedd.v().read(mayCflow),
                                                                             interestingIsValids,
                                                                             new jedd.PhysicalDomain[] { ST.v() }));
        return new jedd.internal.RelationContainer(new jedd.Attribute[] { shadow.v() },
                                                   new jedd.PhysicalDomain[] { V1.v() },
                                                   ("jedd.internal.Jedd.v().minus(jedd.internal.Jedd.v().read(sha" +
                                                    "dows), necessaryShadows).iterator() at /tmp/fixing-paddle/sr" +
                                                    "c/soot/jimple/paddle/BDDCflowStack.jedd:176,44-52"),
                                                   jedd.internal.Jedd.v().minus(jedd.internal.Jedd.v().read(shadows),
                                                                                necessaryShadows)).iterator();
    }
}
