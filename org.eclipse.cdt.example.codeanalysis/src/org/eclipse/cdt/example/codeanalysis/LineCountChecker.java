package org.eclipse.cdt.example.codeanalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.cdt.codan.core.model.AbstractCheckerWithProblemPreferences;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;

public class LineCountChecker extends AbstractCheckerWithProblemPreferences {

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
			System.out.println(file.getName() + " " + numLines);
		} catch (IOException e) {
			// ignore
		} catch (CoreException e) {
			// ignore
		}
	}
}
