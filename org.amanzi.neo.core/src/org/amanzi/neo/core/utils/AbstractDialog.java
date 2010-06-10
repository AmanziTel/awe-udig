/* AWE - Amanzi Wireless Explorer
 * http://awe.amanzi.org
 * (C) 2008-2009, AmanziTel AB
 *
 * This library is provided under the terms of the Eclipse Public License
 * as described at http://www.eclipse.org/legal/epl-v10.html. Any use,
 * reproduction or distribution of the library constitutes recipient's
 * acceptance of this agreement.
 *
 * This library is distributed WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.amanzi.neo.core.utils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * TODO Purpose of
 * <p>
 * </p>
 * 
 * @author NiCK
 * @since 1.0.0
 */
public abstract class AbstractDialog<E> extends Dialog {

    private String title;
    protected E status;

    public AbstractDialog(Shell parent, String title) {
        this(parent, title, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.CENTER);
    }

    public AbstractDialog(Shell parent, String title, int style) {
        super(parent, style);
        this.title = title;
        status = null;
    }

    public E open() {
        Shell parentShell = getParent();
        Shell shell = new Shell(parentShell, getStyle());
        shell.setText(getTitle());

        createContents(shell);
        shell.pack();

        // calculate location
        Point size = parentShell.getSize();
        int dlgWidth = shell.getSize().x;
        int dlgHeight = shell.getSize().y;
        shell.setLocation((size.x - dlgWidth) / 2, (size.y - dlgHeight) / 2);
        beforeOpen();
        shell.open();
        // wait
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        dispose();
        return status;
    }

    /**
     *
     */
    protected void dispose() {
    }

    /**
     *
     */
    protected void beforeOpen() {
    }

    protected String getTitle() {
        return title;
    }

    protected abstract void createContents(final Shell shell);

}