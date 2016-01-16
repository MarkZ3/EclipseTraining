package org.eclipse.cdt.example.codeanalysis;

import org.eclipse.cdt.codan.core.cxx.model.AbstractIndexAstChecker;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

public class ComplexityChecker extends AbstractIndexAstChecker {

	private final class ComplexityVisitor extends ASTVisitor {
		
		{
			shouldVisitStatements = true;
		}
		
		@Override
		public int visit(IASTStatement statement) {
			// TODO: increment complexity level
			return super.visit(statement);
		}
		
		@Override
		public int leave(IASTStatement statement) {
			// TODO: decrement complexity level
			return super.leave(statement);
		}
	}

	@Override
	public void processAst(IASTTranslationUnit ast) {
		ast.accept(new ComplexityVisitor());
	}

}
