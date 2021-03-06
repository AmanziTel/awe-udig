/**
 * 
 */
package net.refractions.udig.project.internal.render.impl;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import javax.units.Converter;
import javax.units.SI;
import javax.units.Unit;

import net.refractions.udig.project.ILayer;
import net.refractions.udig.project.IMap;
import net.refractions.udig.project.ProjectBlackboardConstants;
import net.refractions.udig.project.internal.Layer;
import net.refractions.udig.project.internal.ProjectPlugin;
import net.refractions.udig.project.internal.render.ViewportModel;
import net.refractions.udig.project.preferences.PreferenceConstants;
import net.refractions.udig.project.render.IRenderManager;
import net.refractions.udig.project.render.displayAdapter.IMapDisplay;

import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.referencing.GeodeticCalculator;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.TransformException;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

/**
 * Methods for calculating the ScaleDenominator
 * 
 * @author jesse
 */
public final class ScaleUtils {

	static final double ACCURACY = 0.00000001;
	private static final int MAX_ITERATIONS = 10;
	private static Envelope WORLD = new Envelope(-180, 180, -90, 90);

	private ScaleUtils() {
	}

	public static Unit getUnit(CoordinateReferenceSystem crs) {
		return crs.getCoordinateSystem().getAxis(0).getUnit();
	}

	public static double fromMeterToCrs(double value,
			CoordinateReferenceSystem crs) {
		Unit unit = getUnit(crs);
		Converter converter = SI.METER.getConverterTo(unit);
		return converter.convert(value);
	}

	public static double fromCrsToMeter(double value,
			CoordinateReferenceSystem crs) {
		Unit unit = getUnit(crs);
		Converter converter = unit.getConverterTo(SI.METER);
		return converter.convert(value);
	}

	/**
	 * Determines if the crs is a lat/long crs (has angular units)
	 * 
	 * @return true if the crs is a latlong crs (has angular units)
	 */
	public static boolean isLatLong(CoordinateReferenceSystem crs) {
		Unit unit = getUnit(crs);
		Unit degrees = getUnit(DefaultGeographicCRS.WGS84);
		boolean isLatLong = CRS.equalsIgnoreMetadata(unit, degrees);
		return isLatLong;
	}

	/**
	 * Calculates the width of the bounding box that will fit in the display
	 * size at the specified scale.
	 * 
	 * @param newScaleDenominator
	 *            The scale denominator to use to calculate the bounds
	 * @param displaySize
	 *            the size of the display
	 * @param dpi
	 *            the dots per inch of the display
	 * @param currentBounds
	 *            the current bounds, this is required if the current CRS is
	 *            latlong
	 * @return the width of the extent that is at the specified scale
	 */
	public static ReferencedEnvelope calculateBoundsFromScale(
			double newScaleDenominator, Dimension displaySize, int dpi,
			ReferencedEnvelope currentBounds) {
		double MIN_SCALE = 1.0E-100;
		if (newScaleDenominator <= MIN_SCALE || Double.isInfinite(newScaleDenominator) || Double.isNaN(newScaleDenominator)) {
			return currentBounds;
		}
		return calculateBoundsFromScaleInternal(newScaleDenominator,
				displaySize, dpi, currentBounds, 0);
	}

	private static ReferencedEnvelope calculateBoundsFromScaleInternal(
			double newScaleDenominator, Dimension displaySize, int dpi,
			ReferencedEnvelope currentBounds, int iterations) {
		double min = Double.MIN_VALUE;
		double oldScaleDenom = calculateScaleDenominator(currentBounds,
				displaySize, dpi);
		if(oldScaleDenom<=0 || Double.isInfinite(oldScaleDenom) || Double.isNaN(oldScaleDenom)){
			return currentBounds;
		}
		double ratio = newScaleDenominator / oldScaleDenom;
		double newWidth = currentBounds.getWidth() * ratio;
		ReferencedEnvelope newExtent = calculateBounds(newWidth, displaySize,
				currentBounds);
		double calculatedScale = calculateScaleDenominator(newExtent,
				displaySize, dpi);
		if (Math.abs(calculatedScale - newScaleDenominator) > ACCURACY
				&& iterations < MAX_ITERATIONS && calculatedScale>0) {
			return calculateBoundsFromScaleInternal(newScaleDenominator,
					displaySize, dpi, newExtent, iterations + 1);
		} else {
			return newExtent;
		}
	}

	private static ReferencedEnvelope calculateBounds(double width,
			Dimension displaySize, ReferencedEnvelope originalExtent) {
		Coordinate center = originalExtent.centre();
		double height = width * displaySize.height / displaySize.width;
		CoordinateReferenceSystem crs = originalExtent
				.getCoordinateReferenceSystem();
		double minx = center.x - width / 2;
		double maxx = center.x + width / 2;
		double miny = center.y - height / 2;
		double maxy = center.y + height / 2;
		return new ReferencedEnvelope(minx, maxx, miny, maxy, crs);
	}

	private static double distancePerPixel(Dimension displaySize,
			ReferencedEnvelope currentBounds) {
		ReferencedEnvelope referencePixel = toValidPixelBoundsClosestToCenter(
				displaySize, currentBounds);
		try {
			ReferencedEnvelope referencePixelLatLong = referencePixel
					.transform(DefaultGeographicCRS.WGS84, true);
			double minX = referencePixelLatLong.getMinX();
			double maxX = referencePixelLatLong.getMaxX();
			double scale = 1;

			if (referencePixelLatLong.getWidth() > 360) {
				scale = referencePixelLatLong.getWidth() / 18;
				minX = 0;
				maxX = 180;
			}
			GeodeticCalculator calc = new GeodeticCalculator();
			double centerY = centeredYWithinWorld(referencePixelLatLong);
			calc.setAnchorPoint(minX, centerY);
			calc.setDestinationPoint(maxX, centerY);
			return calc.getOrthodromicDistance() * scale;
		} catch (FactoryException e) {
			ProjectPlugin.log("error transforming: " + referencePixel
					+ " to latlong", e);
			return -1;
		} catch (TransformException e) {
			ProjectPlugin.log("error transforming: " + referencePixel
					+ " to latlong", e);
			return -1;
		}
	}

	/**
	 * Finds the y coord that is centered extent and or the center of the world
	 */
	private static double centeredYWithinWorld(ReferencedEnvelope extent) {
		Coordinate centre = extent.centre();
		if (WORLD.contains(centre)) {
			return centre.y;
		}

		return 0;
	}

	/**
	 * Calculates the world bounds of the center pixel of the screen. If the
	 * pixel is not within the world lat/long bounds it finds the closest pixel
	 * that is within the world and calculates the bounds for that.
	 */
	static ReferencedEnvelope toValidPixelBoundsClosestToCenter(
			Dimension displaySize, ReferencedEnvelope currentBounds) {

		Coordinate centre = currentBounds.centre();
		Point referencePixel = nearestPixel(centre.x, centre.y, currentBounds,
				displaySize);

		ReferencedEnvelope pixelBounds = pixelBounds(referencePixel.x,
				referencePixel.y, currentBounds, displaySize);

		return shiftToWorld(pixelBounds);
	}

	static ReferencedEnvelope shiftToWorld(ReferencedEnvelope pixelBounds) {
		DefaultGeographicCRS wgs84 = DefaultGeographicCRS.WGS84;
		ReferencedEnvelope latLong;
		try {
			latLong = pixelBounds.transform(wgs84, true);

			if (WORLD.contains(latLong)) {
				return pixelBounds;
			}

			double deltax = 0, deltay = 0;
			if (latLong.getWidth() < WORLD.getWidth()) {
				if (latLong.getMinX() < WORLD.getMinX()) {
					deltax = WORLD.getMinX() - latLong.getMinX();
				}
				if (latLong.getMaxX() > WORLD.getMaxX()) {
					deltax = WORLD.getMaxX() - latLong.getMaxX();
				}
			}

			if (latLong.getHeight() < WORLD.getHeight()) {
				if (latLong.getMinY() < WORLD.getMinY()) {
					deltay = WORLD.getMinY() - latLong.getMinY();
				}
				if (latLong.getMaxY() > WORLD.getMaxY()) {
					deltay = WORLD.getMaxX() - latLong.getMaxY();
				}
			}

			latLong.translate(deltax, deltay);

			return latLong.transform(
					pixelBounds.getCoordinateReferenceSystem(), true);
		} catch (TransformException e) {
			ProjectPlugin.log("", e);
		} catch (FactoryException e) {
			ProjectPlugin.log("", e);
		}
		return pixelBounds;
	}

	/**
	 * calculates the pixel closest to x and y that is contained within the
	 * world
	 * 
	 * @param displaySize
	 */
	static Point nearestPixel(double x, double y, ReferencedEnvelope extent,
			Dimension displaySize) {
		if (WORLD.contains(x, y)) {
			return worldToPixel(new Coordinate(x, y), extent, displaySize);
		}

		double newX, newY;

		if (x < WORLD.getMinX()) {
			newX = WORLD.getMinX();
		} else if (x > WORLD.getMaxX()) {
			newX = WORLD.getMaxX();
		} else {
			newX = x;
		}

		if (y < WORLD.getMinY()) {
			newY = WORLD.getMinY();
		} else if (y > WORLD.getMaxY()) {
			newY = WORLD.getMaxY();
		} else {
			newY = y;
		}

		return worldToPixel(new Coordinate(newX, newY), extent, displaySize);
	}

	public static ReferencedEnvelope pixelBounds(int x, int y,
			ReferencedEnvelope currentBounds, Dimension displaySize) {
		double minX = ((double) x);
		double maxX = ((double) x) + 1;
		double minY = ((double) y);
		double maxY = ((double) y) + 1;

		Coordinate ul = pixelToWorld(minX, minY, currentBounds, displaySize);
		Coordinate lr = pixelToWorld(maxX, maxY, currentBounds, displaySize);

		if( ul==null || lr==null ){
			return new ReferencedEnvelope(new Envelope(), currentBounds.getCoordinateReferenceSystem());
		}
		
		return new ReferencedEnvelope(ul.x, lr.x, ul.y, lr.y, currentBounds
				.getCoordinateReferenceSystem());

	}

	public static double calculateScaleDenominator(ReferencedEnvelope bounds,
			Dimension displaySize, int dpi) {
		
		if( bounds.getWidth()==0 || bounds.getHeight()==0){
			return -1;
		}

		CoordinateReferenceSystem crs = bounds.getCoordinateReferenceSystem();

		int width = displaySize.width;
		boolean isLatLong = isLatLong(crs);

		if (isLatLong) {
			double distancePerPixel = distancePerPixel(displaySize, bounds);
			if (distancePerPixel < 0) {
				return -1;
			}
			double pixelSize = 1.0 / dpi * 25.4 / 1000;
			double scaleDenominator = distancePerPixel / pixelSize;
			return scaleDenominator;

		} else {
			double refWidthMeters = fromCrsToMeter(bounds.getWidth(), crs);
			double displayMeterDistancePixels = refWidthMeters * dpi / 2.54
					* 100.0;
			double scaleDenominator = displayMeterDistancePixels / width;
			return scaleDenominator;
		}
	}

	public static Envelope centerPixelBounds(IMapDisplay display,
			ReferencedEnvelope bounds) {
		Coordinate ul = pixelToWorld((int) (display.getWidth() / 2 - 0.5),
				(int) (display.getHeight() / 2 - 0.5), bounds, display
						.getDisplaySize());
		Coordinate lr = pixelToWorld((int) (display.getWidth() / 2 + 0.5),
				(int) (display.getHeight() / 2 + 0.5), bounds, display
						.getDisplaySize());
		return new Envelope(ul.x, lr.x, ul.y, lr.y);
	}

	public static boolean withinValidWorld(ReferencedEnvelope bounds) {
		Envelope world = new Envelope(-181, 181, -91, 91);
		return world.contains(bounds.centre());
	}

	public static Coordinate pixelToWorld(double x, double y,
			ReferencedEnvelope extent, Dimension displaySize) {
		// set up the affine transform and calculate scale values
		AffineTransform at = worldToScreenTransform(extent, displaySize);

		try {
			Point2D result = at.inverseTransform(
					new java.awt.geom.Point2D.Double(x, y),
					new java.awt.geom.Point2D.Double());
			Coordinate c = new Coordinate(result.getX(), result.getY());

			return c;
		} catch (Exception e) {
			ProjectPlugin.log("Error transforming point:" + x + "," + y
					+ " to a coordinate", e);
		}

		return null;
	}

	/**
	 * @see ViewportModel#worldToScreenTransform(Envelope, Dimension)
	 */
	public static AffineTransform worldToScreenTransform(Envelope mapExtent,
			Dimension screenSize) {
		double scaleX = screenSize.getWidth() / mapExtent.getWidth();
		double scaleY = screenSize.getHeight() / mapExtent.getHeight();

		double tx = -mapExtent.getMinX() * scaleX;
		double ty = (mapExtent.getMinY() * scaleY) + screenSize.getHeight();

		AffineTransform at = new AffineTransform(scaleX, 0.0d, 0.0d, -scaleY,
				tx, ty);

		return at;
	}

	public static Point worldToPixel(Coordinate coord,
			ReferencedEnvelope bounds, Dimension displaySize) {

		Point2D w = new Point2D.Double(coord.x, coord.y);
		AffineTransform at = worldToScreenTransform(bounds, displaySize);
		Point2D p = at.transform(w, new Point2D.Double());
		return new Point((int) p.getX(), (int) p.getY());
	}


    /**
     * This method restricts the bounds so that the resulting bounding box is at a legal scale within the bounds.  The centers of
     * the new and old BBox should be the same.
     *
     * @param bbox the bbox to restrict to a value between the min and max scales.
     * 
     * @return a new bounds that is withing the min and max bounds of the layer
     */
    public static ReferencedEnvelope fitToMinAndMax(ReferencedEnvelope bbox, ILayer layer) {
        ReferencedEnvelope bounds = restrictMinimum(bbox, layer);
        bounds = restrictMaximum(bounds, layer.getMap(), layer);

        return bounds;
    }

    /**
     * Checks the max scale denominator on the layer
     */
    private static ReferencedEnvelope restrictMaximum(ReferencedEnvelope bounds,
            IMap map, ILayer layer) {
        double maxFromLayer = ((Layer) layer).getMaxScaleDenominator();

        ReferencedEnvelope result = bounds;
        ReferencedEnvelope maxBounds = calculateBoundsFromScale(bounds, maxFromLayer, layer);
        if (bounds.contains((Envelope)maxBounds))
            result = maxBounds;
        return result;
    }

    /**
     * Checks the minScale denominator on the layer and the preference that is
     * set globally.
     */
    private static ReferencedEnvelope restrictMinimum(ReferencedEnvelope bounds, ILayer layer) {
        double minFromLayer = ((Layer) layer).getMinScaleDenominator();

        /*
         * Additional analysis of bounds. What if the only one point exists in
         * the layer? The application should not zoom to the scale 1:1
         */
        Integer minimumScale = layer.getMap().getBlackboard().getInteger(
                ProjectBlackboardConstants.LAYER__MINIMUM_ZOOM_SCALE);
        if (minimumScale == null) {
            minimumScale = ProjectPlugin.getPlugin().getPluginPreferences()
                    .getInt(PreferenceConstants.P_MINIMUM_ZOOM_SCALE);
        }

        ReferencedEnvelope result = bounds;
        if (minimumScale != null && minFromLayer < minimumScale) {
            ReferencedEnvelope minimumBounds = calculateBoundsFromScale(bounds,
                    minimumScale, layer);
            if (minimumBounds.contains((Envelope)bounds))
                result = minimumBounds;
        } else {
            ReferencedEnvelope minimumBounds = calculateBoundsFromScale(bounds,
                    minFromLayer, layer);
            if (minimumBounds.contains((Envelope)bounds))
                result = minimumBounds;
        }
        return result;
    }

    private static ReferencedEnvelope calculateBoundsFromScale(
            ReferencedEnvelope requestedBounds, double scaleDenominator, ILayer layer) {

        IRenderManager renderManager = layer.getMap().getRenderManager();
        if( renderManager==null ){
            // no render manager and therefore no scale to calculate.
            return requestedBounds;
        }
        IMapDisplay mapDisplay = renderManager.getMapDisplay();

        return ScaleUtils.calculateBoundsFromScale(scaleDenominator, mapDisplay
                .getDisplaySize(), mapDisplay.getDPI(), requestedBounds);
    }

}
