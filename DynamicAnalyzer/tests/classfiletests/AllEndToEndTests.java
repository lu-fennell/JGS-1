package classfiletests;

import classfiletests.utils.ClassCompiler;
import classfiletests.utils.ClassRunner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import utils.logging.L1Logger;

import java.util.Arrays;
import java.util.logging.Logger;

@RunWith(Parameterized.class)
public class AllEndToEndTests {

	private final String name;
	private final boolean hasIllegalFlow;
	private final String[] involvedVars;

	/**
	 * Constructor.
	 * 
	 * @param name
	 *            Name of the class
	 * @param hasIllegalFlow
	 *            true if an exception is expected
	 * @param involvedVars
	 *            variables which are expected to be involved in the exception
	 */
	public AllEndToEndTests(String name, boolean hasIllegalFlow,
			String... involvedVars) {

		this.name = name;
		this.hasIllegalFlow = hasIllegalFlow;
		this.involvedVars = involvedVars;
	}

	Logger logger = L1Logger.getLogger();

	/**
	 * Create an Iterable for all testclasses. Arguments: String name, boolean
	 * hasIllegalFlow, String... involvedVars
	 * 
	 * @return Iterable
	 */
	@Parameters(name = "Name: {0}")
	public static Iterable<Object[]> generateParameters() {
		return Arrays.asList(
				new Object[] { "AccessFieldsOfObjectsFail", true, new String[] { "java.lang.String_$r6" } }, // set involved variable
				new Object[] { "AccessFieldsOfObjectsSuccess", false, new String[] {} },
				
				new Object[] { "ArithmeticExpressionsFail", true, new String[] { "java.lang.Integer_$r4" } }, 	// set involved variable
				new Object[] { "ArithmeticExpressionsSuccess", false, new String[] {} },
				
				new Object[] { "ArrayRefFail", true, new String[] {"java.lang.String_$r7"} }, 					// fails, rewrote non-expressive (?!) test
				new Object[] { "ArrayRefSuccess", false, new String[] {} }, 									// rewritten (was one testfile ExtClasses)
				
				new Object[] { "ExtClassesFail", true, new String[] {} }, 										// Todo: write external, manually instrumented class for this test
				new Object[] { "ExtClassesSuccess", false, new String[] {} }, 									// same
				
				new Object[] { "FieldsSuccess", false, new String[] {} },
				new Object[] { "FieldWriteFail", true, new String[] {} },
				
				new Object[] { "ForLoopSuccess", false, new String[] {} }, 										// fails with java.lang.VerifyError:
				new Object[] { "ForLoopFail", true, new String[] {} }, 											// fails
				
				new Object[] { "IfStmtSuccess", false, new String[] {} }, 										// split up from one test IfStmt 
				new Object[] { "IfStmtFail", true, new String[] { "byte_b1" } }, 
				
				new Object[] { "InvokeInternalMethod", true, new String[] {} }, // fails
				new Object[] { "InvokeLibMethod", true, new String[] { "int_i0" } },
				new Object[] { "MakeHigh", false, new String[] {} },
				new Object[] { "MulArray", false, new String[] {} },
				new Object[] { "MulArrayFail", true, new String[] {} }, // fails
				
				new Object[] { "NonStaticMethods", false, new String[] {} },
				new Object[] { "NonStaticMethodsFail", true, new String[] {} }, //
				
				new Object[] { "Simple", true, new String[] { "java.lang.String_r3" } },
				
				new Object[] { "StaticMethods", false, new String[] {} }, // fail
				new Object[] { "StaticMethodsFail", true, new String[] {} },
				
				new Object[] { "SwitchStmt", false, new String[] {} },
				new Object[] { "SwitchStmtFail", true,new String[] { "int_i4" } }, 
				
				new Object[] { "WhileLoop", false, new String[] {} }, 
				new Object[] { "WhileLoopFail", true, new String[] { "int_$i3" } });
	}

	@Test
	/**
	 * Runs each testfile specified above. note that the outputDir can be set to ones liking.
	 */
	public void test() {
		System.out.println("\n\n\n");
		logger.info("Start of executing main.testclasses." + name + "");

		String outputDir = "junit";
		ClassCompiler.compile(name, outputDir);
		ClassRunner.testClass(name, outputDir, hasIllegalFlow, involvedVars);

		logger.info("Finished executing main.testclasses." + name + "");
	}
}
