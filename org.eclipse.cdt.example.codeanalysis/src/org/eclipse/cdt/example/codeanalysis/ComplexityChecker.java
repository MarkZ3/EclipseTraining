package org.eclipse.cdt.example.codeanalysis;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

public class ComplexityChecker extends AbstractIndexAstChecker {

	private final class ComplexityVisitor extends ASTVisitor {
		private int level = 0;
		
		{
			shouldVisitStatements = true;
		}
		
		@Override
		public int visit(IASTStatement statement) {
			if (isStatementIncreasingComplexity(statement)) {
				level++;
				System.out.println(level);
			}
			return super.visit(statement);
		}

		private boolean isStatementIncreasingComplexity(IASTStatement statement) {
			return statement instanceof IASTIfStatement || statement instanceof IASTForStatement || statement instanceof IASTWhileStatement;
		}
		
		@Override
		public int leave(IASTStatement statement) {
			if (isStatementIncreasingComplexity(statement)) {
				level--;
				System.out.println(level);
			}
			return super.leave(statement);
		}
	}

	@Override
	public void processAst(IASTTranslationUnit ast) {
		ast.accept(new ComplexityVisitor());
	}

}
