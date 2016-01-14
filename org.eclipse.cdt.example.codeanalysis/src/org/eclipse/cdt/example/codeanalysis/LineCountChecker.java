package org.eclipse.cdt.example.codeanalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.cdt.codan.core.model.AbstractCheckerWithProblemPreferences;
import org.eclipse.cdt.codan.core.model.IProblemWorkingCopy;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;

public class LineCountChecker extends AbstractCheckerWithProblemPreferences {

	final static String FILE_TOO_LONG_PROBLEM_ID = "org.eclipse.cdt.example.codeanalysis.toolong";
	
	@Override
	public boolean processResource(IResource resource) throws OperationCanceledException {
		if (!shouldProduceProblems(resource)) {
			return false;
		}
		
		if (resource instanceof IFile) {
			IFile file = (IFile) resource;
			processFile(file);
			return false;
		}
		return false;
	}
	
	private void processFile(IFile file) {
		try (BufferedReader bis = new BufferedReader(new InputStreamReader(file.getContents()))) {
			
			String line;
			int numLines = 0;
			while ((line = bis.readLine()) != null) {
				numLines++;
			}
			
			if (numLines > 100) {
				reportProblem(FILE_TOO_LONG_PROBLEM_ID, file, 1);
			}
		} catch (IOException e) {
			// ignore
		} catch (CoreException e) {
			// ignore
		}
	}
	
	private int getMaxLineCount(IFile file) {
		//TODO: Call getProblemById to get the problem
		//TODO: Call getPreference to get a given preference for that problem
		int maxLineCount = 0;
		return 0;
	}

	@Override
	public void initPreferences(IProblemWorkingCopy problem) {
		super.initPreferences(problem);
		//TODO: Call addPreference(IProblemWorkingCopy, String, ...) to let the framework and UI be aware of the preference
	}
}
