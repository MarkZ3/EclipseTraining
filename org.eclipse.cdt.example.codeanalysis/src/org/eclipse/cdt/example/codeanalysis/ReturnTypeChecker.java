package org.eclipse.cdt.example.codeanalysis;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IBinding;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPClassType;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPMethod;
import org.eclipse.cdt.core.parser.util.ArrayUtil;
import org.eclipse.cdt.internal.core.dom.parser.cpp.ClassTypeHelper;

public class ReturnTypeChecker extends AbstractIndexAstChecker {

	private final class MethodDeclaratorVisitor extends ASTVisitor {

		{
			shouldVisitDeclarators = true;
		}

		@Override
		public int visit(IASTDeclarator declarator) {
			IBinding binding = declarator.getName().resolveBinding();
			if (binding instanceof ICPPMethod) {
				checkConflictingReturn((ICPPMethod) binding, declarator);
			}
			return super.visit(declarator);
		}

		private void checkConflictingReturn(ICPPMethod methodChecked, IASTDeclarator declarator) {
			// TODO: Get the class type of this method. i.e. the 'owner'
			// TODO: Get all the base methods of this class using getAllBaseMethods
			
			// TODO: for each method
			//    - if the method is virtual
			//    - check if the name (getName) is equal to the methodChecked name
			//    - check if the return type is not the same (so that it can be reported
 
		}

		/**
		 * Return all base methods of a given class type
		 * 
		 * @param classType the class type
		 * @return the base methods
		 */
		private ICPPMethod[] getAllBaseMethods(ICPPClassType classType) {
			ICPPClassType[] bases = ClassTypeHelper.getAllBases(classType, null);
			ICPPMethod[] methods = null;
			for (ICPPClassType base : bases) {
				methods = ArrayUtil.addAll(ICPPMethod.class, methods, ClassTypeHelper.getDeclaredMethods(base, null));
			}
			return methods;
		}
	}

	public ReturnTypeChecker() {
	}

	@Override
	public void processAst(IASTTranslationUnit ast) {
		ast.accept(new MethodDeclaratorVisitor());
	}
}
