package org.eclipse.cdt.example.codeanalysis;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

public class ReturnTypeChecker extends AbstractIndexAstChecker {

	private final class MethodDeclaratorVisitor extends ASTVisitor {

		{
			shouldVisitDeclarators = true;
		}

		@Override
		public int visit(IASTDeclarator declarator) {
			return super.visit(declarator);
		}
		
	}

	public ReturnTypeChecker() {
	}

	@Override
	public void processAst(IASTTranslationUnit ast) {
		ast.accept(new MethodDeclaratorVisitor());
	}
}
