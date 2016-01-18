package org.eclipse.cdt.example.codeanalysis;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTNode;
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

		private void checkConflictingReturn(ICPPMethod methodChecked, IASTNode node) {
			ICPPMethod[] baseMethods = getAllBaseMethods(methodChecked.getClassOwner());
			for (ICPPMethod baseMethod : baseMethods) {
				if (baseMethod.isVirtual() && methodChecked.getName().equals(baseMethod.getName())) {
					// Check that the return type is the same
				}
			}
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
