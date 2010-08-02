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
package org.amanzi.awe.neostyle;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import net.refractions.udig.catalog.IGeoResource;
import net.refractions.udig.project.StyleContent;

import org.amanzi.awe.catalog.neo.NeoGeoResource;
import org.amanzi.neo.core.enums.GisTypes;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IMemento;

/**
 * <p>
 * Style content
 * </p>
 * 
 * @author Cinkel_A
 * @since 1.0.0
 */
public class NeoStyleContent extends StyleContent {
    public static final String ID = "org.amanzi.awe.neostyle.style";

    /*
     * Default values for most fields
     */
    public static final int DEF_TRANSPARENCY = 40;
    public static final int DEF_SYMB_SIZE = 15;
    public static final int DEF_SYMB_SIZE_DRIVE = 7;
    public static final boolean DEF_FIX_SYMB_SIZE = false;
    public static final boolean CHANGE_TRANSPARENCY = true;
    public static final int DEF_LABELING = 50;
    public static final int DEF_SMALL_SYMB = 100;
    public static final int DEF_SMALLEST_SYMB = 1000;
    public static final Color DEF_COLOR_LINE = Color.DARK_GRAY;
    public static final Color DEF_COLOR_LABEL = Color.DARK_GRAY;
    public static final Color DEF_COLOR_FILL = new Color(255, 255, 128);
    public static final String DEF_NONE = "";
    public static final String DEF_SECONDARY_PROPERTY = DEF_NONE;
    public static final String DEF_MAIN_PROPERTY = "name";
    /** Network site font size */
    public static final Integer DEF_FONT_SIZE = 10;
    /** Network sector font size */
    public static final Integer DEF_FONT_SIZE_SECTOR = 8;
    public static final Integer DEF_MAXIMUM_SYMBOL_SIZE = 40;
    public static final Integer DEF_DEF_BEAMWIDTH = 10;
    public static final Integer DEF_ICON_OFFSET = 0;
    public static final Color DEF_COLOR_SITE = Color.DARK_GRAY;
    private static final String LINE_PRFX = "LINE_";
    private static final String FILL_PRFX = "FILL_";
    private static final String LABEL_PRFX = "LABEL_";
    private static final String SITE_PRFX = "SITE_";
    private static final String COLOR_RGB = "RGB";
    private static final String SMALLEST_SYMB = "SECTOR_SMALLEST_SYMB";
    private static final String SMALL_SYMB = "SECTOR_SMALL_SYMB";
    private static final String LABELING = "SECTOR_LABELING";
    private static final String FIX_SYMBOL = "FIX_SYMBOL";
    private static final String SYMBOL_SIZE = "SYMBOL_SIZE";
    private static final String SECTOR_TRANSPARENCY = "SECTOR_TRANSPARENCY";
    private static final String MAX_SYMB_SIZE = "MAXIMUM_SYMBOL_SIZE";
    private static final String DEF_BEAMWIDTH = "DEF_BEAMWIDTH";
    private static final String ICON_OFFSET = "ICON_OFFSET";
    private static final String FONT_SIZE = "FONT_SIZE";
    private static final String FONT_SIZE_SECTOR = "FONT_SIZE_SECTOR";
    private static final String SITE_NAME = "SITE_NAME";
    private static final String SECTOR_NAME = "SECTOR_NAME";

    // private static final String IS_NETWORK_STYLE = "IS_NETWORK";


    public NeoStyleContent() {
        super(ID);
    }

    @Override
    public Object createDefaultStyle(IGeoResource resource, Color colour, IProgressMonitor monitor) throws IOException {
        if (resource.canResolve(NeoGeoResource.class)) {
            NeoGeoResource res = resource.resolve(NeoGeoResource.class, monitor);
            if (res.getGeoNeo(monitor).getGisType() == GisTypes.NETWORK) {
                NeoStyle result = new NeoStyle(DEF_COLOR_LINE, DEF_COLOR_FILL, DEF_COLOR_LABEL);
                result.setSmallestSymb(DEF_SMALLEST_SYMB);
                result.setSmallSymb(DEF_SMALL_SYMB);
                result.setLabeling(DEF_LABELING);
                result.setFixSymbolSize(DEF_FIX_SYMB_SIZE);
                result.setSymbolSize(DEF_SYMB_SIZE);
                result.setSymbolTransparency(DEF_TRANSPARENCY);
                result.setSiteFill(DEF_COLOR_SITE);
                result.setMaximumSymbolSize(DEF_MAXIMUM_SYMBOL_SIZE);
                result.setDefaultBeamwidth(DEF_DEF_BEAMWIDTH);
                result.setIconOffset(DEF_ICON_OFFSET);
                result.setFontSize(DEF_FONT_SIZE);
                result.setSectorFontSize(DEF_FONT_SIZE_SECTOR);
                result.setMainProperty(DEF_MAIN_PROPERTY);
                result.setSecondaryProperty(DEF_SECONDARY_PROPERTY);
                result.setChangeTransparency(CHANGE_TRANSPARENCY);
                // result.setNetwork(true);
                return result;
            } else {
                NeoStyle result = new NeoStyle(Color.BLACK, new Color(200, 128, 255, (int)(0.6 * 255.0)), Color.BLACK);
                result.setSmallestSymb(DEF_SMALLEST_SYMB);
                result.setSmallSymb(DEF_SMALL_SYMB);
                result.setLabeling(DEF_LABELING);
                result.setFixSymbolSize(DEF_FIX_SYMB_SIZE);
                result.setSymbolSize(DEF_SYMB_SIZE_DRIVE);
                result.setSymbolTransparency(DEF_TRANSPARENCY);
                result.setSiteFill(DEF_COLOR_SITE);
                result.setMaximumSymbolSize(DEF_MAXIMUM_SYMBOL_SIZE);
                result.setDefaultBeamwidth(DEF_DEF_BEAMWIDTH);
                result.setIconOffset(DEF_ICON_OFFSET);
                result.setFontSize(DEF_FONT_SIZE);
                result.setSectorFontSize(DEF_FONT_SIZE_SECTOR);
                result.setMainProperty(DEF_MAIN_PROPERTY);
                result.setSecondaryProperty(DEF_SECONDARY_PROPERTY);
                result.setChangeTransparency(CHANGE_TRANSPARENCY);
                return result;
            }
        }
        return null;
    }

    @Override
    public Class<NeoStyle> getStyleClass() {
        return NeoStyle.class;
    }

    @Override
    public Object load(IMemento memento) {
        Color line = loadColor(memento, LINE_PRFX, Color.BLACK);
        Color fill = loadColor(memento, FILL_PRFX, Color.BLACK);
        Color label = loadColor(memento, LABEL_PRFX, Color.BLACK);
        Color site = loadColor(memento, SITE_PRFX, Color.BLACK);
        NeoStyle result = new NeoStyle(line, fill, label);
        result.setSiteFill(site);
        result.setSmallestSymb(memento.getInteger(SMALLEST_SYMB));
        result.setSmallSymb(memento.getInteger(SMALL_SYMB));
        result.setLabeling(memento.getInteger(LABELING));
        result.setFixSymbolSize(Boolean.parseBoolean(memento.getString(FIX_SYMBOL)));
        result.setSymbolSize(memento.getInteger(SYMBOL_SIZE));
        result.setSymbolTransparency(memento.getInteger(SECTOR_TRANSPARENCY));
        result.setMaximumSymbolSize(memento.getInteger(MAX_SYMB_SIZE));
        result.setDefaultBeamwidth(memento.getInteger(DEF_BEAMWIDTH));
        result.setIconOffset(memento.getInteger(ICON_OFFSET));
        result.setFontSize(memento.getInteger(FONT_SIZE));
        result.setSectorFontSize(memento.getInteger(FONT_SIZE_SECTOR));
        result.setMainProperty((memento.getString(SITE_NAME)));
        result.setSecondaryProperty((memento.getString(SECTOR_NAME)));
        // result.setNetwork(getBoolean(memento, IS_NETWORK_STYLE, true));
        return result;
    }

    /**
     * @param memento
     * @param isNetworkStyle
     * @param b
     * @return
     */
    private boolean getBoolean(IMemento memento, String prefix, boolean defvalue) {
        try {
            return Boolean.parseBoolean(memento.getString(prefix));
        } catch (Exception e) {
            return defvalue;
        }
    }

    private Color loadColor(IMemento memento, String prfx, Color defColor) {
        Integer rgb = memento.getInteger(prfx + COLOR_RGB);
        return rgb == null ? defColor : new Color(rgb);
    }

    @Override
    public Object load(URL url, IProgressMonitor monitor) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(IMemento memento, Object value) {
        NeoStyle style = (NeoStyle)value;
        saveColor(memento, LINE_PRFX, style.getLine());
        saveColor(memento, FILL_PRFX, style.getFill());
        saveColor(memento, LABEL_PRFX, style.getLabel());
        saveColor(memento, SITE_PRFX, style.getSiteFill());
        memento.putInteger(SMALLEST_SYMB, style.getSmallestSymb());
        memento.putInteger(SMALL_SYMB, style.getSmallSymb());
        memento.putInteger(LABELING, style.getLabeling());
        memento.putString(FIX_SYMBOL, String.valueOf(style.isFixSymbolSize()));
        memento.putInteger(SYMBOL_SIZE, style.getSymbolSize());
        memento.putInteger(SECTOR_TRANSPARENCY, style.getSymbolTransparency());
        memento.putInteger(MAX_SYMB_SIZE, style.getMaximumSymbolSize());
        memento.putInteger(DEF_BEAMWIDTH, style.getDefaultBeamwidth());
        memento.putInteger(ICON_OFFSET, style.getIconOffset());
        memento.putInteger(FONT_SIZE, style.getFontSize());
        memento.putInteger(FONT_SIZE_SECTOR, style.getSecondaryFontSize());
        memento.putString(SITE_NAME, style.getMainProperty());
        memento.putString(SECTOR_NAME, style.getSecondaryProperty());
    }

    /**
     * save color in IMemento
     * 
     * @param memento IMemento
     * @param prfx value prefix
     * @param colorTosave color to save
     */
    private void saveColor(IMemento memento, String prfx, Color colorTosave) {
        memento.putInteger(prfx + COLOR_RGB, colorTosave.getRGB());
    }

}
