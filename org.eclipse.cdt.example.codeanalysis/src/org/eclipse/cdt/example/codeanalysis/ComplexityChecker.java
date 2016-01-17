package org.eclipse.cdt.example.codeanalysis;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

public class ComplexityChecker extends AbstractIndexAstChecker {
	
	private static final String HIGH_COMPLEXITY_PROBLEM_ID = "org.eclipse.cdt.example.codeanalysis.highcomplexity";

	private final class ComplexityVisitor extends ASTVisitor {
		private static final int MAX_COMPLEXITY = 3;
		private int level = 0;
		
		{
			shouldVisitStatements = true;
		}
		
		@Override
		public int visit(IASTStatement statement) {
			if (isStatementIncreasingComplexity(statement)) {
				if (level == MAX_COMPLEXITY) {
					reportProblem(HIGH_COMPLEXITY_PROBLEM_ID, getFile(), statement.getFileLocation().getStartingLineNumber(), MAX_COMPLEXITY);
					return ASTVisitor.PROCESS_SKIP;
				}
				level++;
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
			}
			return super.leave(statement);
		}
	}

	@Override
	public void processAst(IASTTranslationUnit ast) {
		ast.accept(new ComplexityVisitor());
	}

}
