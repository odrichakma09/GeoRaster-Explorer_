/* 
import java.awt.image.BufferedImage;
import java.io.File;

import org.geotools.api.geometry.Bounds;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;

public class RasterIO {

    // ==================================================
    // 4.3 + 4.4
    // Read a GeoTIFF and convert it to our Raster object
    // ==================================================
    public Raster readGeoTiff(String filePath) throws Exception {

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException(
                    "GeoTIFF file not found: " + filePath
            );
        }

        // Create GeoTIFF reader
        GeoTiffReader reader = new GeoTiffReader(file);

        // Read GeoTIFF
        GridCoverage2D coverage = reader.read(null);

        // Get image data
        BufferedImage image =
                (BufferedImage) coverage.getRenderedImage();

        java.awt.image.Raster imageRaster =
                image.getRaster();

        // Get raster dimensions
        int columns = imageRaster.getWidth();
        int rows = imageRaster.getHeight();

        // Create our own data array
        double[][] data =
                new double[rows][columns];

        // Read every pixel
        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++) {

                double value =
                        imageRaster.getSampleDouble(
                                column,
                                row,
                                0
                        );

                data[row][column] = value;
            }
        }

        // ==================================================
        // Spatial metadata
        // ==================================================

        Bounds bounds = coverage.getEnvelope();

        double originX =
                bounds.getMinimum(0);

        double originY =
                bounds.getMaximum(1);

        double cellSizeX =
                (bounds.getMaximum(0)
                - bounds.getMinimum(0))
                / columns;

        double cellSizeY =
                (bounds.getMaximum(1)
                - bounds.getMinimum(1))
                / rows;

        // Our current Raster class has one cellSize.
        // We use the X resolution for now.
        double cellSize = cellSizeX;

        String crs =
                coverage
                .getCoordinateReferenceSystem2D()
                .toString();

        // Create our custom Raster object
        return new Raster(
                data,
                cellSize,
                -9999.0,
                originX,
                originY,
                crs
        );
    }


    // ==================================================
    // 4.5
    // Print information about the raster
    // ==================================================
    public void printRasterInfo(Raster raster) {

        System.out.println();
        System.out.println("Real GeoTIFF Information");
        System.out.println("-------------------------");

        System.out.println(
                "Rows: " + raster.getRows()
        );

        System.out.println(
                "Columns: " + raster.getColumns()
        );

        System.out.println(
                "Cell Size: " + raster.getCellSize()
        );

        System.out.println(
                "NoData Value: " + raster.getNoDataValue()
        );

        System.out.println(
                "Origin X: " + raster.getOriginX()
        );

        System.out.println(
                "Origin Y: " + raster.getOriginY()
        );

        System.out.println(
                "CRS: "
                + raster.getCoordinateReferenceSystem()
        );

        System.out.println();
    }
}*/


import java.awt.image.RenderedImage;
import java.io.File;

import org.geotools.api.geometry.Bounds;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.gce.geotiff.GeoTiffReader;

public class RasterIO {

    // =========================================================
    // Read a GeoTIFF and convert it into our custom Raster object
    // =========================================================

    public Raster readGeoTiff(String filePath) throws Exception {

        // -----------------------------------------------------
        // 1. Check whether the file exists
        // -----------------------------------------------------

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IllegalArgumentException(
                    "GeoTIFF file not found: " + filePath
            );
        }

        // -----------------------------------------------------
        // 2. Create GeoTIFF reader
        // -----------------------------------------------------

        GeoTiffReader reader = new GeoTiffReader(file);

        // -----------------------------------------------------
        // 3. Read the GeoTIFF
        // -----------------------------------------------------

        GridCoverage2D coverage = reader.read(null);

        if (coverage == null) {
            throw new IllegalStateException(
                    "Could not read GeoTIFF: " + filePath
            );
        }

        // -----------------------------------------------------
        // 4. Get the image
        //
        // IMPORTANT:
        // getRenderedImage() returns a RenderedImage.
        // It is NOT necessarily a BufferedImage.
        // -----------------------------------------------------

        RenderedImage image = coverage.getRenderedImage();

        // -----------------------------------------------------
        // 5. Get raster data
        // -----------------------------------------------------

        java.awt.image.Raster imageRaster = image.getData();

        // -----------------------------------------------------
        // 6. Get raster dimensions
        // -----------------------------------------------------

        int columns = imageRaster.getWidth();
        int rows = imageRaster.getHeight();

        // -----------------------------------------------------
        // 7. Create our own data array
        // -----------------------------------------------------

        double[][] data = new double[rows][columns];

        // -----------------------------------------------------
        // 8. Read every pixel
        //
        // Band 0 is used for now.
        // -----------------------------------------------------

        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++) {

                double value =
                        imageRaster.getSampleDouble(
                                column,
                                row,
                                0
                        );

                data[row][column] = value;
            }
        }

        // =====================================================
        // Spatial Metadata
        // =====================================================

        Bounds bounds = coverage.getEnvelope();

        // -----------------------------------------------------
        // 9. Get spatial bounds
        // -----------------------------------------------------

        double minimumX = bounds.getMinimum(0);
        double maximumX = bounds.getMaximum(0);

        double minimumY = bounds.getMinimum(1);
        double maximumY = bounds.getMaximum(1);

        // -----------------------------------------------------
        // 10. Calculate cell size
        // -----------------------------------------------------

        double cellSizeX =
                (maximumX - minimumX) / columns;

        double cellSizeY =
                (maximumY - minimumY) / rows;

        // -----------------------------------------------------
        // Our current Raster class stores only one cell size.
        // Use X resolution for now.
        // -----------------------------------------------------

        double cellSize = cellSizeX;

        // -----------------------------------------------------
        // 11. Define raster origin
        //
        // We currently use the upper-left corner.
        // -----------------------------------------------------

        double originX = minimumX;
        double originY = maximumY;

        // -----------------------------------------------------
        // 12. Get CRS
        // -----------------------------------------------------

        String crs = "Unknown";

        if (coverage.getCoordinateReferenceSystem2D() != null) {

            crs = coverage
                    .getCoordinateReferenceSystem2D()
                    .toString();
        }

        // =====================================================
        // 13. Create our custom Raster object
        // =====================================================

        Raster raster = new Raster(
                data,
                cellSize,
                -9999.0,
                originX,
                originY,
                crs
        );

        // -----------------------------------------------------
        // 14. Return the raster
        // -----------------------------------------------------

        return raster;
    }


    // =========================================================
    // Print raster information
    // =========================================================

    public void printRasterInfo(Raster raster) {

        System.out.println();
        System.out.println("====================================");
        System.out.println("       GeoRaster Explorer");
        System.out.println("       GeoTIFF Information");
        System.out.println("====================================");

        System.out.println(
                "Rows: " + raster.getRows()
        );

        System.out.println(
                "Columns: " + raster.getColumns()
        );

        System.out.println(
                "Cell Size: " + raster.getCellSize()
        );

        System.out.println(
                "NoData Value: " + raster.getNoDataValue()
        );

        System.out.println(
                "Origin X: " + raster.getOriginX()
        );

        System.out.println(
                "Origin Y: " + raster.getOriginY()
        );

        System.out.println(
                "CRS: "
                + raster.getCoordinateReferenceSystem()
        );

        System.out.println("====================================");
        System.out.println();
    }
}