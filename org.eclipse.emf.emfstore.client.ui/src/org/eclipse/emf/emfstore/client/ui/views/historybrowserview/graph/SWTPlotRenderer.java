/*******************************************************************************
 * Copyright 2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
/*******************************************************************************
 * Copyright 2008, Robin Rosenberg <robin.rosenberg@dewire.com>
 * Copyright 2008, Shawn O. Pearce <spearce@spearce.org>
 * Copyright 2011, Mathias Kinzler <mathias.kinzler@sap.com>
 * Copyright 2011, Matthias Sohn <matthias.sohn@sap.com>*
 * Copyright 2012, Alexander Aumann <alexander.f.aumann@gmail.com>
 * All rights reserved. This program and the accompanying materials
 * are made available under the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.eclipse.emf.emfstore.client.ui.views.historybrowserview.graph;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

/**
 * A renderer for IPlotCommits able to draw these commits into a table or tree cell.
 * Originally taken from org.eclipse.egit.ui.internal.history.
 */
public class SWTPlotRenderer extends AbstractPlotRenderer {

	// The colors following are used for labels in the EGit renderer and might prove useful in the future.

	// private static final RGB OUTER_HEAD = new RGB(0, 128, 0);
	//
	// private static final RGB INNER_HEAD = new RGB(188, 220, 188);
	//
	// private static final RGB OUTER_TAG = new RGB(121, 120, 13);
	//
	// private static final RGB INNER_TAG = new RGB(249, 255, 199);
	//
	// private static final RGB OUTER_ANNOTATED = new RGB(104, 78, 0);
	//
	// private static final RGB INNER_ANNOTATED = new RGB(255, 239, 192);
	//
	// private static final RGB OUTER_REMOTE = new RGB(80, 80, 80);
	//
	// private static final RGB INNER_REMOTE = new RGB(225, 225, 225);
	//
	// private static final RGB OUTER_OTHER = new RGB(30, 30, 30);
	//
	// private static final RGB INNER_OTHER = new RGB(250, 250, 250);

	private static final int MAX_LABEL_LENGTH = 15;

	private final Color sysColorBlack;

	private final Color sysColorGray;

	private final Color sysColorWhite;

	private final Color commitDotFill;

	private final Color commitDotOutline;

	private final Map<String, Point> labelCoordinates = new HashMap<String, Point>();

	private int textHeight;

	private boolean enableAntialias = true;

	private GC g;

	private int cellX;

	private int cellY;

	private Color cellFG;

	private Color cellBG;

	/**
	 * Creates a new SWTPlotRenderer that can draw IPlotCommits for table/tree events.
	 * 
	 * @param d The display for the renderer.
	 */
	public SWTPlotRenderer(final Display d) {
		sysColorBlack = d.getSystemColor(SWT.COLOR_BLACK);
		sysColorGray = d.getSystemColor(SWT.COLOR_GRAY);
		sysColorWhite = d.getSystemColor(SWT.COLOR_WHITE);
		commitDotFill = new Color(d, new RGB(220, 220, 220));
		commitDotOutline = new Color(d, new RGB(110, 110, 110));
	}

	/**
	 * Frees all resources reserved by the renderer.
	 */
	void dispose() {
		commitDotFill.dispose();
		commitDotOutline.dispose();
	}

	/**
	 * Paints a IPlotCommit into the cell determined by the given event.
	 * 
	 * @param event The event triggering the painting.
	 * @param representer The commit to paint.
	 */
	public void paint(final Event event, IPlotCommit representer) {
		g = event.gc;

		if (this.enableAntialias) {
			try {
				g.setAntialias(SWT.ON);
			} catch (SWTException e) {
				this.enableAntialias = false;
			}
		}

		// this.headRef = actHeadRef;
		cellX = event.x;
		cellY = event.y;
		cellFG = g.getForeground();
		cellBG = g.getBackground();
		if (textHeight == 0) {
			textHeight = g.stringExtent("/").y; //$NON-NLS-1$
		}

		// final TableItem ti = (TableItem) event.item;
		// IMockCommit commit = (IMockCommit) ti.getData();
		// try {
		// commit.parseBody();
		// } catch (IOException e) {
		//			Activator.error("Error parsing body", e); //$NON-NLS-1$
		// return;
		// }
		paintCommit(representer, event.height);
	}

	@Override
	protected void drawLine(final Color color, final int x1, final int y1, final int x2, final int y2, final int width) {
		g.setForeground(color);
		g.setLineWidth(width);
		g.drawLine(cellX + x1, cellY + y1, cellX + x2, cellY + y2);
	}

	/**
	 * Draws a dot with the given parameters.
	 * 
	 * @param outline The color for the outline of the dot.
	 * @param fill The color used to fill the dot.
	 * @param x The left most coordinate of the dot.
	 * @param y The top most coordinate of the dot.
	 * @param w The width of the dot.
	 * @param h The height of the dot.
	 */
	protected void drawDot(final Color outline, final Color fill, final int x, final int y, final int w, final int h) {
		int dotX = cellX + x + 2;
		int dotY = cellY + y + 1;
		int dotW = w - 2;
		int dotH = h - 2;
		g.setBackground(fill);
		g.fillOval(dotX, dotY, dotW, dotH);
		g.setForeground(outline);
		g.setLineWidth(2);
		g.drawOval(dotX, dotY, dotW, dotH);
	}

	@Override
	protected void drawCommitDot(final int x, final int y, final int w, final int h) {
		drawDot(commitDotOutline, commitDotFill, x, y, w, h);
	}

	@Override
	protected void drawBoundaryDot(final int x, final int y, final int w, final int h) {
		drawDot(sysColorGray, sysColorWhite, x, y, w, h);
	}

	@Override
	protected void drawText(final String msg, final int x, final int y) {
		final Point textsz = g.textExtent(msg);
		final int texty = (y * 2 - textsz.y) / 2;
		g.setForeground(cellFG);
		g.setBackground(cellBG);
		g.drawString(msg, cellX + x, cellY + texty, true);
	}

	@Override
	protected int drawLabel(int x, int y, IPlotCommit commit) {
		String txt = commit.getBranch();
		// String name = commit.getName();
		// boolean tag = false;
		// boolean branch = false;
		// RGB labelOuter;
		// RGB labelInner;
		// if (name.startsWith(Constants.R_HEADS)) {
		// branch = true;
		// labelOuter = OUTER_HEAD;
		// labelInner = INNER_HEAD;
		// txt = name.substring(Constants.R_HEADS.length());
		// } else if (name.startsWith(Constants.R_REMOTES)) {
		// branch = true;
		// labelOuter = OUTER_REMOTE;
		// labelInner = INNER_REMOTE;
		// txt = name.substring(Constants.R_REMOTES.length());
		// } else if (name.startsWith(Constants.R_TAGS)) {
		// tag = true;
		// if (commit.getPeeledObjectId() != null) {
		// labelOuter = OUTER_ANNOTATED;
		// labelInner = INNER_ANNOTATED;
		// } else {
		// labelOuter = OUTER_TAG;
		// labelInner = INNER_TAG;
		// }
		//
		// txt = name.substring(Constants.R_TAGS.length());
		// } else {
		// labelOuter = OUTER_OTHER;
		// labelInner = INNER_OTHER;
		//
		// if (name.startsWith(Constants.R_REFS))
		// txt = name.substring(Constants.R_REFS.length());
		// else
		// txt = name; // HEAD and such
		// }

		int maxLength;
		// if (tag)
		// maxLength = Activator.getDefault().getPreferenceStore().getInt(UIPreferences.HISTORY_MAX_TAG_LENGTH);
		// else if (branch)
		// maxLength = Activator.getDefault().getPreferenceStore().getInt(UIPreferences.HISTORY_MAX_BRANCH_LENGTH);
		// else
		maxLength = MAX_LABEL_LENGTH;
		if (txt.length() > maxLength) {
			txt = txt.substring(0, maxLength) + "\u2026"; // ellipsis "..." (in UTF-8) //$NON-NLS-1$
		}
		// highlight checked out branch
		// Font oldFont = g.getFont();
		// boolean isHead = isHead(name);
		// if (isHead)
		// g.setFont(CommitGraphTable.highlightFont());

		Point textsz = g.stringExtent(txt);
		int arc = textsz.y / 2;
		final int texty = (y * 2 - textsz.y) / 2;

		// Draw backgrounds
		g.setLineWidth(1);

		g.setBackground(sysColorWhite);
		// g.fillRoundRectangle(cellX + x + 1, cellY + texty, textsz.x + 6, textsz.y + 1, arc, arc);

		g.setForeground(getLabelBorderColor(commit));
		// g.fillRoundRectangle(cellX + x, cellY + texty - 1, textsz.x + 8, textsz.y, arc - 1, arc - 1);

		g.setBackground(getLabelColor(commit));
		g.fillRoundRectangle(cellX + x + 1, cellY + texty, textsz.x + 6, textsz.y - 1, arc, arc);
		// g.fillRoundRectangle(cellX + x + 2, cellY + texty + 1, textsz.x + 4, textsz.y - 4, arc - 1, arc - 1);

		g.drawRoundRectangle(cellX + x, cellY + texty, textsz.x + 7, textsz.y - 1, arc, arc);
		g.setForeground(sysColorBlack);

		// Draw text
		g.drawString(txt, cellX + x + 4, cellY + texty, true);

		// if (isHead)
		// g.setFont(oldFont);

		labelCoordinates.put(String.valueOf(commit.getId()), new Point(x, x + textsz.x));
		return 10 + textsz.x;
	}

	private Color getLabelBorderColor(IPlotCommit commit) {
		// if (commit.isLocalHistoryOnly()) {
		// assert commit.getLane() == null : "Local history commits do not have a lane.";
		// return LOCAL_HISTORY_BORDER_COLOR;
		// }
		// assert commit.getLane() != null : "Only local history commits do not have a lane.";
		// return commit.getLane().getSaturatedColor();
		return commit.getColor();
	}

	private Color getLabelColor(IPlotCommit commit) {
		// if (commit.isLocalHistoryOnly()) {
		// assert commit.getLane() == null : "Local history commits do not have a lane.";
		// return LOCAL_HISTORY_INNER_COLOR;
		// }
		//
		// assert commit.getLane() != null : "Only local history commits do not have a lane.";
		// return commit.getLane().getLightColor();
		return commit.getLightColor();

	}

	// private boolean isHead(String name) {
	// boolean isHead = false;
	// if (headRef != null) {
	// String headRefName = headRef.getLeaf().getName();
	// if (name.equals(headRefName))
	// isHead = true;
	// }
	// return isHead;
	// }
	@Override
	protected Color laneColor(final PlotLane myLane, boolean fullSaturation) {
		Color color;
		if (myLane == null) {
			if (fullSaturation) {
				color = sysColorBlack;
			} else {
				color = sysColorGray;
			}
		} else {
			if (fullSaturation) {
				color = myLane.getSaturatedColor();
			} else {
				color = myLane.getLightColor();
			}
		}
		if (color == null) {
			assert false;
			color = sysColorGray;
		}
		return color;
	}

	// /**
	// * Obtain the horizontal span of {@link Ref} label in pixels
	// *
	// * For example, let's assume the SWTCommit has two {@link Ref}s (see {@link SWTCommit#getRef(int)}, which are
	// * rendered as two labels. The
	// * first label may span from 15 to 54 pixels in x direction, while the
	// * second one may span from 58 to 76 pixels.
	// *
	// * This can be used to determine if the mouse is positioned over a label.
	// *
	// * @param ref
	// * @return a Point where x and y designate the start and end x position of
	// * the label
	// */
	// public Point getRefHSpan(Ref ref) {
	// return labelCoordinates.get(ref.getName());
	// }

	/**
	 * @return text height in pixel
	 */
	public int getTextHeight() {
		return textHeight;
	}
}
