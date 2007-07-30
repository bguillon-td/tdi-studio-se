// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2007 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.repository.ui.swt.preview;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.talend.core.CorePlugin;
import org.talend.core.model.metadata.builder.connection.SchemaTarget;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.core.utils.CsvArray;
import org.talend.core.utils.XmlArray;
import org.talend.core.utils.XmlField;
import org.talend.core.utils.XmlRow;
import org.talend.repository.i18n.Messages;
import org.talend.repository.preview.ProcessDescription;

/**
 * Create SWT Table to show the content of a file. <br/>
 * 
 * $Id:ShadowProcessPreview.java 1185 2006-12-22 10:43:29 +0000 (星期五, 22 十二月 2006) amaumont $
 * 
 */
public class ShadowProcessPreview {

    protected int maximumRowsToPreview = CorePlugin.getDefault().getPreferenceStore().getInt(
            ITalendCorePrefConstants.PREVIEW_LIMIT);

    /**
     * Constante and main var.
     */
    private int filePreviewWidth = 350;

    private int filePreviewHeight = 180;

    private String filePath;

    private String[] header;

    private Table table;

    private Composite composite;

    /**
     * Create Object to manage Preview and MetaData.
     * 
     * @param compositeFileViewer
     * @param filepath (null or path)
     */
    public ShadowProcessPreview(Composite composite, String filePath, int width, int height) {
        this.filePath = filePath;
        this.composite = composite;
        this.filePreviewWidth = width;
        this.filePreviewHeight = height;
    }

    /**
     * Create Table to show the content of a file.
     * 
     * @return
     * 
     */
    public void newTablePreview() {
        table = new Table(composite, SWT.BORDER);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);
        // table.setSize(filePreviewWidth, filePreviewHeight);

        // force the dimension width a gridData
        GridData gridData = new GridData(GridData.FILL_BOTH);
        // gridData.minimumWidth = filePreviewWidth;
        // gridData.minimumHeight = filePreviewHeight;
        // gridData.heightHint = filePreviewHeight;
        // gridData.widthHint = filePreviewWidth;
        table.setLayoutData(gridData);
    }

    /**
     * refresh Header of table Preview.
     * 
     * @param columns
     */
    public void refreshPreviewHeader(final String[] columns) {
        int existingColumnsLength = table.getColumnCount();

        // update the existing columns
        for (int i = 0; i < existingColumnsLength; i++) {
            if (i < columns.length) {
                // the column exist and must be updated
                table.getColumn(i).setText(columns[i]);
            } else {
                // the following column exist and must be dispose
                i = existingColumnsLength;
            }
        }

        // if necessary, add the another columns must be created
        for (int i = existingColumnsLength; i < columns.length; i++) {
            TableColumn column = new TableColumn(table, SWT.LEFT);
            column.setText(columns[i]);
        }

        // if necessary, dispose the unusable columns
        while (table.getColumnCount() > columns.length) {
            table.getColumn(table.getColumnCount() - 1).dispose();
        }
    }

    /**
     * refresh TablePreview width the first rows of the file.
     * 
     * @param csvArray
     * @param firstRowIsLabel
     */
    public void refreshTablePreview(final CsvArray csvArray, final boolean firstRowIsLabel) {
        List<String[]> csvRows = csvArray.getRows();

        // init the title columns

        // XmlRow firstRow = xmlRows.get(0);

        String[] firstRowFields = csvRows.get(0);

        // List<XmlField> firstRowFields = firstRow.getFields();

        Integer numbersOfColumns = getRightFirstRow(csvRows);

        String[] title = new String[numbersOfColumns];
        for (int i = 0; i < numbersOfColumns; i++) {
            if (firstRowIsLabel) {
                if (numbersOfColumns <= firstRowFields.length) {
                    if (firstRowFields[i] != null && !("").equals(firstRowFields[i])) { //$NON-NLS-1$[
                        title[i] = firstRowFields[i];
                    } else {
                        title[i] = Messages.getString("DelimitedFilePreview.column") + " " + i; //$NON-NLS-1$ //$NON-NLS-2$
                    }
                } else {
                    if (i < firstRowFields.length) {
                        title[i] = firstRowFields[i];
                    } else {
                        title[i] = Messages.getString("DelimitedFilePreview.column") + " " + i; //$NON-NLS-1$ //$NON-NLS-2$
                    }
                }
            } else {
                title[i] = Messages.getString("DelimitedFilePreview.column") + " " + i; //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        this.header = title;

        // clear all the item
        table.clearAll();

        // refresh the Header and the Item of the table
        refreshPreviewHeader(title);
        refreshPreviewItem(csvRows, firstRowIsLabel);

        refreshPreviewHeader(title);

        // resize all the columns but not the table
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumn(i).pack();
        }

        // scroll to show the first col and first row
        table.showItem(table.getItem(0));
        if (table.getColumns() != null && table.getColumns().length > 0) {
            table.showColumn(table.getColumn(0));
        }
    }

    /**
     * refresh TablePreview width the first rows of the file.
     * 
     * @param csvArray
     * @param firstRowIsLabel
     */
    public void refreshTablePreview(final CsvArray csvArray, final boolean firstRowIsLabel,
            ProcessDescription processDescription) {
        List<String[]> xmlRows = csvArray.getRows();

        // init the title columns

        String[] firstRowFields = xmlRows.get(0);

        // List<XmlField> firstRowFields = firstRow.getFields();

        Integer numbersOfColumns = getRightFirstRow(xmlRows);

        String[] title = new String[numbersOfColumns];
        for (int i = 0; i < numbersOfColumns; i++) {
            if (firstRowIsLabel) {
                if (numbersOfColumns <= firstRowFields.length) {
                    if (firstRowFields[i] != null && !("").equals(firstRowFields[i])) { //$NON-NLS-1$
                        title[i] = firstRowFields[i];
                    } else {
                        title[i] = Messages.getString("DelimitedFilePreview.column") + " " + i; //$NON-NLS-1$ //$NON-NLS-2$
                    }
                } else {
                    if (i < firstRowFields.length) {
                        title[i] = firstRowFields[i];
                    } else {
                        title[i] = Messages.getString("DelimitedFilePreview.column") + " " + i; //$NON-NLS-1$ //$NON-NLS-2$
                    }
                }
            } else {
                title[i] = "" + processDescription.getSchema().get(0).getListColumns().get(i); //$NON-NLS-1$
            }
        }
        this.header = title;

        // clear all the item
        table.clearAll();

        // refresh the Header and the Item of the table
        refreshPreviewHeader(title);
        refreshPreviewItem(xmlRows, firstRowIsLabel);

        refreshPreviewHeader(title);

        // resize all the columns but not the table
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumn(i).pack();
        }

        // scroll to show the first col and first row
        table.showItem(table.getItem(0));
        if (table.getColumns() != null && table.getColumns().length > 0) {
            table.showColumn(table.getColumn(0));
        }
    }

    /**
     * refresh TablePreview width the first rows of the file.
     * 
     * @param csvArray
     * @param firstRowIsLabel
     */
    public void refreshTablePreview(final CsvArray csvArray, final boolean firstRowIsLabel,
            List<SchemaTarget> schemaTarget) {
        List<String[]> csvRows = csvArray.getRows();

        // init the title columns

        String[] firstRowFields = csvRows.get(0);

        // List<XmlField> firstRowFields = firstRow.getFields();

        Integer numbersOfColumns = getRightFirstRow(csvRows);

        String[] title = new String[numbersOfColumns];
        for (int i = 0; i < numbersOfColumns; i++) {
            if (firstRowIsLabel) {
                if (numbersOfColumns <= firstRowFields.length) {
                    if (firstRowFields[i] != null && !("").equals(firstRowFields[i])) { //$NON-NLS-1$
                        title[i] = firstRowFields[i];
                    } else {
                        title[i] = Messages.getString("DelimitedFilePreview.column") + " " + i; //$NON-NLS-1$ //$NON-NLS-2$
                    }
                } else {
                    if (i < firstRowFields.length) {
                        title[i] = firstRowFields[i];
                    } else {
                        title[i] = Messages.getString("DelimitedFilePreview.column") + " " + i; //$NON-NLS-1$ //$NON-NLS-2$
                    }
                }
            } else {
                if (schemaTarget.get(i).getTagName() != null && !schemaTarget.get(i).getTagName().equals("")) { //$NON-NLS-1$
                    title[i] = "" + schemaTarget.get(i).getTagName(); //$NON-NLS-1$
                } else {
                    title[i] = Messages.getString("DelimitedFilePreview.column") + " " + i; //$NON-NLS-1$ //$NON-NLS-2$
                }
            }
        }
        this.header = title;

        // clear all the item
        table.removeAll();

        // refresh the Header and the Item of the table
        refreshPreviewHeader(title);
        refreshPreviewItem(csvRows, firstRowIsLabel);

        refreshPreviewHeader(title);

        // resize all the columns but not the table
        for (int i = table.getColumnCount() - 1; i >= 0; i--) {
            table.getColumn(i).pack();
        }

        // scroll to show the first col and first row
        table.showItem(table.getItem(0));
        if (table.getColumns() != null && table.getColumns().length > 0) {
            table.showColumn(table.getColumn(0));
        }
    }

    // CALCULATE THE NULBER OF COLUMNS IN THE PREVIEW
    public Integer getRightFirstRow(List<String[]> csvRows) {

        Integer numbersOfColumns = null;
        int parserLine = csvRows.size();

        if (parserLine > maximumRowsToPreview) {
            parserLine = maximumRowsToPreview;
        }
        for (int i = 0; i < parserLine; i++) {
            if (csvRows.get(i) != null) {
                String[] nbRow = csvRows.get(i);
                // List<XmlField> nbRowFields = nbRow.getFields();
                if (numbersOfColumns == null || nbRow.length >= numbersOfColumns) {
                    numbersOfColumns = nbRow.length;
                }
            }
        }
        return numbersOfColumns;
    }

    /**
     * refresh the Item of Preview.
     * 
     * @param csvRows
     * @param firstRowIsLabel
     */
    private void refreshPreviewItem(final List<String[]> csvRows, final boolean firstRowIsLabel) {
        int existingItemCount = table.getItemCount();

        int end = csvRows.size();
        if (firstRowIsLabel) {
            end--;
        }
        for (int f = 0; f < end; f++) {
            String[] csvFields;
            if (firstRowIsLabel) {
                csvFields = csvRows.get(f + 1);
            } else {
                csvFields = csvRows.get(f);
            }
            String[] values = new String[csvFields.length];
            for (int i = 0; i < csvFields.length; i++) {
                values[i] = csvFields[i];
            }

            if (f >= existingItemCount) {
                // create a new Item
                TableItem row = new TableItem(table, SWT.NONE);
                row.setText(values);
            } else {
                // update an existing Item
                table.getItem(f).setText(values);
            }
        }
    }

    /**
     * Getter for filePath.
     * 
     * @return the filePath
     */
    public String getFilePath() {
        return this.filePath;
    }

    /**
     * Sets the filePath.
     * 
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Getter for header.
     * 
     * @return the header
     */
    public String[] getHeader() {
        return this.header;
    }

    /**
     * DOC ocarbone Comment method "clearTablePreview".
     */
    public void clearTablePreview() {
        table.clearAll();
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumn(i).setText(""); //$NON-NLS-1$
            table.getColumn(i).setWidth(0);
        }
    }

}
