/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The contents of this file are subject to the terms of either the Universal Permissive License
 * v 1.0 as shown at http://oss.oracle.com/licenses/upl
 *
 * or the following license:
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or other materials provided with
 * the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.openjdk.jmc.javafx.osgi;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.resource.ImageDescriptor;

import javafx.application.Platform;
import javafx.embed.swt.SWTFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;

/**
 * Toolkit for common JavaFX operations.
 */
public class FXToolkit {
	static {
		// Funnily enough, Java FX has a new feature that implicitly
		// exits when the last panel is closed. Not so good in the typical eclipse case...
		Platform.setImplicitExit(false);
	}

	public static void loadFromFxml(URL fxmlUrl, Object controller, Object root) throws IOException {
		FXMLLoader loader = new FXMLLoader(fxmlUrl);
		loader.setRoot(root);
		loader.setController(controller);
		loader.load();
	}

	public static Object loadFromFxml(URL fxmlUrl, Object controller) throws IOException {
		FXMLLoader loader = new FXMLLoader(fxmlUrl);
		loader.setController(controller);
		return loader.load();
	}

	public static Image createImage(ImageDescriptor imageDescriptor) {
		return SWTFXUtils.toFXImage(imageDescriptor.getImageData(), null);
	}

	public static Scene createErrorScene(Throwable e) {
		// FIXME: Create error scene, with the appropriate bells and whistles.
		// FIXME: Plug-in is currently in IGNORE_NLS_PROBLEM mode - fix?
		Scene scene = new Scene(new Group());
		TextArea textArea = new TextArea();
		textArea.setText(
				String.format("Could not create Java FX UI. Message was %s. Check your log for info.", e.getMessage()));
		scene.setRoot(textArea);
		Logger.getLogger("org.openjdk.jmc.javafx.osgi").log(Level.SEVERE, "Could not create Java FX UI.", e);
		return scene;
	}
}
