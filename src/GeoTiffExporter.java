import java.awt.Point;
import java.awt.image.BandedSampleModel;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.File;

import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.gce.geotiff.GeoTiffWriter;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;

public class GeoTiffExporter {

    public void writeGeoTiff(
            Raster raster,
            String outputPath) throws Exception {

        int columns = raster.getColumns();
        int rows = raster.getRows();

        // ------------------------------------------------
        // 1. Create floating-point raster
        // ------------------------------------------------

        BandedSampleModel sampleModel =
                new BandedSampleModel(
                        DataBuffer.TYPE_FLOAT,
                        columns,
                        rows,
                        1
                );

        WritableRaster writableRaster =
                WritableRaster.createWritableRaster(
                        sampleModel,
                        new Point(0, 0)
                );

        // ------------------------------------------------
        // 2. Copy GeoRaster data
        // ------------------------------------------------

        for (int row = 0; row < rows; row++) {

            for (int column = 0; column < columns; column++) {

                double value =
                        raster.getValue(row, column);

                writableRaster.setSample(
                        column,
                        row,
                        0,
                        value
                );
            }
        }

        // ------------------------------------------------
        // 3. Calculate geographic extent
        // ------------------------------------------------

        double minX =
                raster.getOriginX();

        double maxX =
                minX
                + columns * raster.getCellSize();

        double maxY =
                raster.getOriginY();

        double minY =
                maxY
                - rows * raster.getCellSize();

        // ------------------------------------------------
        // 4. Define coordinate reference system
        // ------------------------------------------------

        CoordinateReferenceSystem crs =
                CRS.decode("EPSG:4326", true);

        // ------------------------------------------------
        // 5. Create spatial envelope
        // ------------------------------------------------

        ReferencedEnvelope envelope =
                new ReferencedEnvelope(
                        minX,
                        maxX,
                        minY,
                        maxY,
                        crs
                );

        // ------------------------------------------------
        // 6. Create GeoTools GridCoverage
        // ------------------------------------------------

        GridCoverageFactory factory =
                new GridCoverageFactory();

        GridCoverage2D coverage =
                factory.create(
                        "GeoRaster",
                        writableRaster,
                        envelope
                );

        // ------------------------------------------------
        // 7. Prepare output file
        // ------------------------------------------------

        File outputFile =
                new File(outputPath);

        File parent =
                outputFile.getParentFile();

        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        // ------------------------------------------------
        // 8. Write GeoTIFF
        // ------------------------------------------------

        GeoTiffWriter writer =
                new GeoTiffWriter(outputFile);

        try {

            writer.write(
                    coverage,
                    null
            );

        } finally {

            writer.dispose();
            coverage.dispose(true);
        }

        // ------------------------------------------------
        // 9. Confirmation
        // ------------------------------------------------

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("GEOTIFF EXPORT SUCCESSFUL");
        System.out.println("----------------------------------------");
        System.out.println(
                "Output: "
                + outputFile.getAbsolutePath()
        );
        System.out.println();
    }
}