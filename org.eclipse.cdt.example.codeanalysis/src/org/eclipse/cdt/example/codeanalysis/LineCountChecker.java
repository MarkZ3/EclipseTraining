package org.eclipse.cdt.example.codeanalysis;

import org.eclipse.cdt.codan.core.model.AbstractCheckerWithProblemPreferences;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.OperationCanceledException;

public class LineCountChecker extends AbstractCheckerWithProblemPreferences {

	@Override
	public boolean processResource(IResource resource) throws OperationCanceledException {
		return false;
	}
	
	private void processFile(IFile file) {
		/*
		 * Reading from an IFile is not obvious, here's something to start
		 * 
		try (BufferedReader bis = new BufferedReader(new InputStreamReader(file.getContents()))) {
			// TODO: use bis.readLine to read lines one by one, count the number of lines
			
			// TODO: print number of lines to System.out.println 
		} catch (IOException e) {
			// ignore
		} catch (CoreException e) {
			// ignore
		}
		*/
	}
}
