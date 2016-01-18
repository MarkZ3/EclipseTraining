/*******************************************************************************
 * Copyright (c) 2016 Ericsson
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.cdt.example.codeanalysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.cdt.codan.core.model.AbstractCheckerWithProblemPreferences;
import org.eclipse.cdt.codan.core.model.IProblem;
import org.eclipse.cdt.codan.core.model.IProblemWorkingCopy;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;

public class LineCountChecker extends AbstractCheckerWithProblemPreferences {

	private static final String FILE_TOO_LONG_PROBLEM_ID = "org.eclipse.cdt.example.codeanalysis.toolong";
	private static final int MAX_LINE_COUNT_DEFAULT = 500;
	private static final String PARAM_MAX_LINE_COUNT = "maxlinecount"; //$NON-NLS-1$
	
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

			if (numLines > getMaxLineCount(file)) {
				reportProblem(FILE_TOO_LONG_PROBLEM_ID, file, 1, getMaxLineCount(file));
			}
		} catch (IOException e) {
			// ignore
		} catch (CoreException e) {
			// ignore
		}
	}

	private int getMaxLineCount(IFile file) {
		final IProblem problem = getProblemById(FILE_TOO_LONG_PROBLEM_ID, file);
		String parameter = (String) getPreference(problem, PARAM_MAX_LINE_COUNT);
		int maxLineCount = MAX_LINE_COUNT_DEFAULT;
		try {
				maxLineCount = Integer.parseInt(parameter);
		} catch (NumberFormatException e) {
			// User input something invalid, continue with default
		}
		return maxLineCount;
	}

	@Override
	public void initPreferences(IProblemWorkingCopy problem) {
		super.initPreferences(problem);
		addPreference(problem, PARAM_MAX_LINE_COUNT, "Max line count", Integer.toString(MAX_LINE_COUNT_DEFAULT)); //$NON-NLS-1$
	}
}
