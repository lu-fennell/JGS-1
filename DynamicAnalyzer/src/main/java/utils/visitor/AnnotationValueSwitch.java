package utils.visitor;

import analyzer.level1.JimpleInjector;
import soot.Local;
import soot.SootMethod;
import soot.jimple.*;
import utils.exceptions.InternalAnalyzerException;
import utils.exceptions.NotSupportedStmtException;
import utils.logging.L1Logger;

import java.util.logging.Logger;

public class AnnotationValueSwitch implements JimpleValueSwitch {
	
	private Logger logger = L1Logger.getLogger();
	private VisitorHelper vh = new VisitorHelper();

	/*
	 * ???
	 * UNDEF 
	 * INVOKE 
	 * ASSIGNRIGHT 
	 * ASSIGNLEFT 
	 * IDENTITY 
	 * RETURN 
	 * GOTO 
	 * IF 
	 * SWITCH 
	 * THROW
	 */
	protected enum StmtContext { 
		UNDEF, INVOKE, ASSIGNRIGHT, ASSIGNLEFT, IDENTITY, 
		RETURN, GOTO, IF, SWITCH, THROW
	}
	
	protected StmtContext actualContext = StmtContext.UNDEF;
	
	/*
	 * Type of the right expression in an assign statement. Certain types need to
	 * be handled in a special way. Explanation:
	 * IGNORE This is used for arithmetic expressions. The locals in the expressions
	 * 		are already handled, so its not neccessary to treat the expression
	 *      in StmtSwitch
	 * NEW_ARRAY 
	 * NEW_UNDEF_OBJECT
	 * INVOKE_INTERAL_METHOD 
	 * INVOKE_EXTERNAL_METHOD 
	 * MAKE_HIGH ?????
	 * MAKE_LOW ?????
	 */
	protected enum RequiredActionForRHS {
		IGNORE, NEW_ARRAY, NEW_UNDEF_OBJECT,
		INVOKE_INTERAL_METHOD, INVOKE_EXTERNAL_METHOD, 
		SET_RETURN_LEVEL,
		MAKE_HIGH, MAKE_MEDIUM, MAKE_LOW,
		CAST
	} 
	
	private RequiredActionForRHS requiredActionForRHS = RequiredActionForRHS.IGNORE;

	public RequiredActionForRHS getRequiredActionForRHS() {
		return requiredActionForRHS;
	}


	/*
	 * The statement which called the AnnotationValueSwitch. This variable is set
	 * by AnnotationStmtSwitch.
	 */
	protected Stmt callingStmt;

	/**
	 * It is not neccessary to treat a constant.
	 * @param v a constant
	 */
	@Override
	public void caseDoubleConstant(DoubleConstant v) {
		logger.finest("DoubleConstant identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}
	
	/**
	 * It is not neccessary to treat a constant.
	 * @param v a constant
	 */
	@Override
	public void caseFloatConstant(FloatConstant v) {
		logger.finest("FloatConstant identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	/**
	 * It is not neccessary to treat a constant.
	 * @param v a constant
	 */
	@Override
	public void caseIntConstant(IntConstant v) {
		logger.finest("IntConstant identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	/**
	 * It is not neccessary to treat a constant.
	 * @param v a constant
	 */
	@Override
	public void caseLongConstant(LongConstant v) {
		logger.finest("LongConstant identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	/**
	 * It is not neccessary to treat a constant.
	 * @param v a constant
	 */
	@Override
	public void caseNullConstant(NullConstant v) {
		logger.finest("NullConstant identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	/**
	 * It is not neccessary to treat a constant.
	 * @param v a constant
	 */
	@Override
	public void caseStringConstant(StringConstant v) {
		logger.finest("StringConstant identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	/**
	 * It is not neccessary to treat a constant.
	 * @param v a constant
	 */
	@Override
	public void caseClassConstant(ClassConstant v) {
		logger.finest("ClassConstant identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new InternalAnalyzerException();
		}
	}

	@Override
	public void caseMethodHandle(MethodHandle methodHandle) {
		throw new NotSupportedStmtException("method handle");
	}

	@Override
	public void defaultCase(Object object) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("defaultCase");
		}
	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseAddExpr(AddExpr v) {
		logger.finest("Add Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseAndExpr(AndExpr v) {
		logger.finest("And Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	@Override
	public void caseCmpExpr(CmpExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("CmpExpr");
		}
	}

	@Override
	public void caseCmpgExpr(CmpgExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("CmpgExpr");
		}
	}

	@Override
	public void caseCmplExpr(CmplExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("CmplExpr");
		}
	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseDivExpr(DivExpr v) {
		logger.finest("Div Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseEqExpr(EqExpr v) {
		logger.finest("Eq Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	@Override
	public void caseNeExpr(NeExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("NeExpr");
		}
	}

	@Override
	public void caseGeExpr(GeExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("GeExpr");
		}
	}

	@Override
	public void caseGtExpr(GtExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("GtExpr");
		}
	}

	@Override
	public void caseLeExpr(LeExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("LeExpr");
		}
	}

	@Override
	public void caseLtExpr(LtExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("LtExpr");
		}
	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseMulExpr(MulExpr v) {
		logger.finest("Mul Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;

	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseOrExpr(OrExpr v) {
		logger.finest("Or Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;

	}

	@Override
	public void caseRemExpr(RemExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("RemExpr");
		}
	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseShlExpr(ShlExpr v) {
		logger.finest("Shl Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;

	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseShrExpr(ShrExpr v) {
		logger.finest("Shr Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;

	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseUshrExpr(UshrExpr v) {
		logger.finest("Ushr Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseSubExpr(SubExpr v) {
		logger.finest("Sub Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	/**
	 * It is not neccessary to treat arithmetic expressions. The SecurityLevels
	 * of this expressions are treated at an other place.
	 * @param v an arithmetic expression
	 */
	@Override
	public void caseXorExpr(XorExpr v) {
		logger.finest("Xor Expr identified " + callingStmt.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	}

	@Override
	public void caseInterfaceInvokeExpr(InterfaceInvokeExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		logger.fine("Invoke expression is of type InterfaceInvoke");
		/*
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("InterfaceInvokeExpression");
		}
		*/
		caseMethodInvokation(v);
	}

	@Override
	public void caseSpecialInvokeExpr(SpecialInvokeExpr v) {
		logger.fine("Invoke expression is of type SpecialInvoke");
		caseMethodInvokation(v);
	}

	@Override
	public void caseStaticInvokeExpr(StaticInvokeExpr v) {
		logger.fine("Invoke expression is of type StaticInvoke");
		caseMethodInvokation(v);
	}

	@Override
	public void caseVirtualInvokeExpr(VirtualInvokeExpr v) {
		logger.fine("Invoke expression is of type VirtualInvoke");
		caseMethodInvokation(v);
	}

	@Override
	public void caseDynamicInvokeExpr(DynamicInvokeExpr v) {
		logger.fine("Invoke expression is of type DynamicInvoke");
		caseMethodInvokation(v);
	}

	@Override
	public void caseCastExpr(CastExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
			
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			// new InternalAnalyzerException();
		}
	}

	@Override
	public void caseInstanceOfExpr(InstanceOfExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("InstanceOf");
		}
	}

	@Override
	public void caseNewArrayExpr(NewArrayExpr v) {
		requiredActionForRHS = RequiredActionForRHS.NEW_ARRAY;
		logger.finest("New Array expression identified: " + callingStmt.toString());
		if (actualContext == StmtContext.ASSIGNRIGHT) {
		// TODO
		}
	}

	@Override
	public void caseNewMultiArrayExpr(NewMultiArrayExpr v) {
		requiredActionForRHS = RequiredActionForRHS.NEW_ARRAY;
		logger.finest("New Multiarray expression identified: " + callingStmt.toString());
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			new InternalAnalyzerException();
		}
	}

	@Override
	public void caseNewExpr(NewExpr v) {
		requiredActionForRHS = RequiredActionForRHS.NEW_UNDEF_OBJECT;
		logger.finest("NewExpr identified " + v.getBaseType());
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			// new InternalAnalyzerException("new Expr");
			if (!ExternalClasses.classMap.contains(v.toString())) {
				// TODO Standardverfahren
			} else {
				// TODO special methods
			}
		}
	}

	
	/**
	 * It is supposed that it is not neccessay to treat the length expression since
	 * it always comes with the corresponding array and the level of the array (which is
	 * the same as its length level) will always be already treated.
	 * @param v length expression
	 */
	@Override
	public void caseLengthExpr(LengthExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		logger.finest("LengthExpr identified: " + v.toString());
//		if (actualContext == StmtContext.ASSIGNRIGHT) {
//			// JimpleInjector.addLevelInAssignStmt(v, callingStmt);
//			System.out.println(v.getType());
//			System.out.println(v.getUseBoxes().get(0).toString());
//
//			new InternalAnalyzerException();
//		} else {
//			new InternalAnalyzerException();			
//		}
	}

	@Override
	public void caseNegExpr(NegExpr v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("NegExpr");
		}
	}

	@Override
	public void caseArrayRef(ArrayRef v) {
		logger.finest("Array reference identified " + v.toString());
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			JimpleInjector.addLevelInAssignStmt(v, callingStmt);
		} else if (actualContext == StmtContext.ASSIGNLEFT) {
			JimpleInjector.setLevelOfAssignStmt(v, callingStmt);
		} else {
			throw new InternalAnalyzerException();			
		}
	}

	@Override
	public void caseStaticFieldRef(StaticFieldRef v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		logger.finest("Static field reference identified " + v.toString());
		logger.finest(		v.getField().getDeclaringClass().toString());
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			JimpleInjector.addLevelInAssignStmt(v, callingStmt);
		} else if (actualContext == StmtContext.ASSIGNLEFT) {
			JimpleInjector.setLevelOfAssignStmt(v, callingStmt);
		} else {
			new InternalAnalyzerException();			
		}
	}

	@Override
	public void caseInstanceFieldRef(InstanceFieldRef v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		logger.finest("Instance field reference identified " + v.toString());	
		logger.finest("kh" + v.getBase().toString());
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			JimpleInjector.addLevelInAssignStmt(v, callingStmt);
		} else if (actualContext == StmtContext.ASSIGNLEFT) {
			JimpleInjector.setLevelOfAssignStmt(v, callingStmt);
		} else {
			new InternalAnalyzerException();			
		}
	}

	@Override
	public void caseParameterRef(ParameterRef v) {
		logger.finest("Parameter reference identified " + v.toString());
		throw new NotSupportedStmtException("ParameterRef");
	}

	@Override
	public void caseCaughtExceptionRef(CaughtExceptionRef v) {
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			throw new NotSupportedStmtException("CaughtExceptionRef");
		}
	}

	@Override
	public void caseThisRef(ThisRef v) {
		logger.finer("@This reference identified " + v.toString());
		throw new NotSupportedStmtException("ThisRef");
	}

	@Override
	public void caseLocal(Local l) {	
		logger.finest("Local identified " + l.toString());
		if (actualContext == StmtContext.ASSIGNRIGHT) {
			JimpleInjector.addLevelInAssignStmt(l, callingStmt);
		} else if (actualContext == StmtContext.ASSIGNLEFT) {
			JimpleInjector.setLevelOfAssignStmt(l, callingStmt);
		} else {
			new InternalAnalyzerException();			
		}
	}
	
	/**
	 * Method to handly any invoke statement
	 * @param v
	 */
	private void caseMethodInvokation(InvokeExpr v) {

	    // TODO: this log message surely is confusing
		logger.finest(v.toString());
		requiredActionForRHS = RequiredActionForRHS.IGNORE;
	
		if (actualContext == StmtContext.INVOKE
				|| actualContext == StmtContext.ASSIGNRIGHT ) {
			Local[] args = vh.getArgumentsForInvokedMethod(v);
			SootMethod method = v.getMethod();

			if (ExternalClasses.isSpecialMethod(method)) {
				logger.fine("Found special method: " + method);
				requiredActionForRHS = ExternalClasses.instrumentSpecialMethod(method, callingStmt, args);
			} else {
				if (v.getMethod().getDeclaringClass().isLibraryClass()) {
					// method is not instrumented and we have no special treatment for it... 
					logger.severe("====== IGNORING UNKNOWN LIBRARY METHOD " + method + " =======");
                    // TODO: why don't we exit here? (otherwise we are not ignoring the library method)
				}
				logger.fine("Found an external class " + method);
				logger.fine("This class is treated as an internal class");
				// JimpleInjector.pushToGlobalPC(LocalPC JOIN GlobalPC)
				requiredActionForRHS = RequiredActionForRHS.SET_RETURN_LEVEL;
				// aber überall noch mal checken ob nirgendwo das right element
				// überschrieben wird, d.h. ob das hier eine eindeutige 
				// positioin ist
				JimpleInjector.storeArgumentLevels(callingStmt, args);	// this is where we could push a global pc
			}
		} else {
			throw new InternalAnalyzerException(
					"Unexpected Context for Invoke Expression");
		}		
	}
}


