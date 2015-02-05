ARX_CLASSPATH=lib/colt/colt-1.2.0.jar:lib/commons/commons-io-2.4.jar:lib/commons/commons-lang-2.6.jar:lib/commons/commons-math3-3.1.1.jar:lib/junit/hamcrest-core-1.3.jar:lib/hppc/hppc-0.6.0.jar:lib/jama/Jama-1.0.2.jar:lib/junit/junit-4.11.jar:lib/jhc/libjhc-1.0.0-swt.jar:lib/log4j/log4j-1.2.17.jar:lib/mysql/mysql-connector-java-5.1.27-bin.jar:lib/objectselector/objectselector-0.1-lib.jar:lib/jface/org.eclipse.core.commands_3.6.2.v20130123-162658.jar:lib/jface/org.eclipse.core.runtime_3.8.0.v20120912-155025.jar:lib/draw2d/org.eclipse.draw2d_3.8.1.201301141834.jar:lib/jface/org.eclipse.equinox.common_3.6.100.v20120522-1841.jar:lib/jface/org.eclipse.jface_3.8.102.v20130123-162658.jar:lib/nattable/org.eclipse.nebula.widgets.nattable.core_1.1.0.201405012245.jar:lib/swtchart/org.swtchart.ext_0.8.0.v20120301.jar:lib/swtchart/org.swtchart_0.8.0.v20120301.jar:lib/poi/poi-3.10-FINAL-20140208.jar:lib/poi/poi-ooxml-3.10-FINAL-20140208.jar:lib/poi/poi-ooxml-schemas-3.10-FINAL-20140208.jar:lib/postgresql/postgresql-9.3-1101.jdbc41.jar:lib/sqlite/sqlite-jdbc-3.7.2.jar:lib/swt/swt-4.2.1-cocoa-macosx-x86_64.jar:lib/swt/swt-4.2.1-cocoa-macosx.jar:lib/swt/swt-4.2.1-gtk-linux-x86.jar:lib/swt/swt-4.2.1-gtk-linux-x86_64.jar:lib/swt/swt-4.2.1-win32-win32-x86.jar:lib/swt/swt-4.2.1-win32-win32-x86_64.jar:lib/swtknob/swtknob-1.0.0.jar

javac -encoding UTF-8 -cp $ARX_CLASSPATH:build/classes -s src -d build/classes $(find src/teanon -name "*.java" -type f)
echo "==============================================="
for ((i=2;i<=100;i++));
do
  java -cp $ARX_CLASSPATH:build/classes syntheticmedical.L3Diversity $i
done
echo "==============================================="
for ((i=2;i<=100;i++));
do
  java -cp $ARX_CLASSPATH:build/classes syntheticmedical.L3DiversityWithSuppression $i
done
