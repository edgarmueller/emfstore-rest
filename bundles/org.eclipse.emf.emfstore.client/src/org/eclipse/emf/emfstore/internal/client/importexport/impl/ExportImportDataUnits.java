/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.client.importexport.impl;

import org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit;
import org.eclipse.emf.emfstore.internal.client.model.ProjectSpace;
import org.eclipse.emf.emfstore.internal.common.model.Project;

/**
 * Represents all units that are considered valid to be exported/imported.
 */
public enum ExportImportDataUnits implements ExportImportDataUnit {
	/**
	 * A local change.
	 * 
	 * @see ProjectSpace#getLocalChangePackage()
	 */
	Change {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit#getExtension()
		 */
		public String getExtension() {
			return ".eoc";
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit#getName()
		 */
		public String getName() {
			return "operations";
		}
	},

	/**
	 * A {@link Project}.
	 */
	Project {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit#getExtension()
		 */
		public String getExtension() {
			return ".ecp";
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit#getName()
		 */
		public String getName() {
			return "project";
		}
	},

	/**
	 * A {@link ProjectSpace}.
	 */
	ProjectSpace {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit#getExtension()
		 */
		public String getExtension() {
			return ".esp";
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit#getName()
		 */
		public String getName() {
			return "projectspace";
		}
	},

	/**
	 * A {@link org.eclipse.emf.emfstore.internal.client.model.Workspace}.
	 */
	Workspace {

		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit#getExtension()
		 */
		public String getExtension() {
			return ".esw";
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit#getName()
		 */
		public String getName() {
			return "workspace";
		}
	},

	/**
	 * A project history.
	 */
	History {
		/**
		 * 
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit#getExtension()
		 */
		public String getExtension() {
			return ".esh";
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see org.eclipse.emf.emfstore.internal.client.importexport.ExportImportDataUnit#getName()
		 */
		public String getName() {
			return "history";
		}
	}
}