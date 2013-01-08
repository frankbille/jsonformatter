/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dk.frankbille.jsonformatter;

import java.io.File;
import java.io.IOException;

import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class JsonFormatterWorker extends SwingWorker<Object, Object> {

	private final File sourceFile;
	private final File destinationFile;
	private final JProgressBar progressBar;

	private IOException occurredException;

	public JsonFormatterWorker(final File sourceFile, final File destinationFile, final JProgressBar progressBar) {
		this.sourceFile = sourceFile;
		this.destinationFile = destinationFile;
		this.progressBar = progressBar;
	}

	@Override
	protected Object doInBackground() throws Exception {
		progressBar.setMaximum(100);
		progressBar.setValue(0);
		progressBar.setString(null);

		try {
			JsonFormatter.format(sourceFile, destinationFile, new FormatProgressListener() {
				@Override
				public void progress(long step, long total) {
					progressBar.setValue((int) (100 * step / total));
				}
			});
		} catch (IOException e) {
			occurredException = e;
			throw e;
		}

		return null;
	}

	public IOException getOccurredException() {
		return occurredException;
	}

	public boolean hasErrors() {
		return getOccurredException() != null;
	}

	@Override
	protected void done() {
		if (hasErrors()) {
			progressBar.setValue(0);
			progressBar.setString(getOccurredException().getMessage());
		} else {
			progressBar.setValue(100);
		}
	}

}
