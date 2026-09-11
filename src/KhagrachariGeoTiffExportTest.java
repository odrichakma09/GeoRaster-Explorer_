public class KhagrachariGeoTiffExportTest {

    public static void main(String[] args) {

        // ------------------------------------------------
        // 1. Initialize components
        // ------------------------------------------------

        RasterIO rasterIO = new RasterIO();
        RasterOperations operations = new RasterOperations();
        GeoTiffExporter exporter = new GeoTiffExporter();

        try {

            System.out.println();
            System.out.println("========================================");
            System.out.println(" Khagrachhari Compound Hazard Analysis");
            System.out.println("========================================");

            // ------------------------------------------------
            // 2. Load input rasters
            // ------------------------------------------------

            Raster landslide =
                    rasterIO.readGeoTiff(
                            "data/Khagrachari_landslide.tif"
                    );

            Raster flood =
                    rasterIO.readGeoTiff(
                            "data/Khagrachari_flood.tif"
                    );

            System.out.println();
            System.out.println(
                    "Both input rasters loaded successfully."
            );

            // ------------------------------------------------
            // 3. Normalize hazard rasters
            // ------------------------------------------------

            Raster normalizedLandslide =
                    operations.normalize(landslide);

            Raster normalizedFlood =
                    operations.normalize(flood);

            System.out.println(
                    "Both hazard rasters normalized to 0-1."
            );

            // ------------------------------------------------
            // 4. Define hazard weights
            // ------------------------------------------------

            // Equal-weight exploratory compound hazard model.
            // Future versions can use expert-based or
            // data-driven weights.

            double landslideWeight = 0.5;
            double floodWeight = 0.5;

            System.out.println();
            System.out.println("Hazard Weights:");
            System.out.println(
                    "Landslide Weight: " + landslideWeight
            );
            System.out.println(
                    "Flood Weight: " + floodWeight
            );

            // ------------------------------------------------
            // 5. Calculate compound hazard index
            // ------------------------------------------------

            double[][] compoundData =
                    new double[
                            landslide.getRows()
                    ][
                            landslide.getColumns()
                    ];

            for (int row = 0;
                 row < landslide.getRows();
                 row++) {

                for (int column = 0;
                     column < landslide.getColumns();
                     column++) {

                    double landslideValue =
                            normalizedLandslide
                                    .getValue(row, column);

                    double floodValue =
                            normalizedFlood
                                    .getValue(row, column);

                    // Handle invalid / missing values
                    if (Double.isNaN(landslideValue)
                            || Double.isNaN(floodValue)) {

                        compoundData[row][column] =
                                Double.NaN;

                    } else {

                        compoundData[row][column] =
                                (landslideWeight * landslideValue)
                                +
                                (floodWeight * floodValue);
                    }
                }
            }

            Raster compoundHazard =
                    new Raster(
                            compoundData,
                            landslide.getCellSize(),
                            landslide.getNoDataValue(),
                            landslide.getOriginX(),
                            landslide.getOriginY(),
                            landslide.getCoordinateReferenceSystem()
                    );

            System.out.println();
            System.out.println(
                    "Compound Hazard Index calculated."
            );

            // ------------------------------------------------
            // 6. Classify compound hazard
            // ------------------------------------------------

            // Demonstration classification thresholds.
            // These thresholds are not scientifically validated
            // hazard-category thresholds.

            double[][] classificationData =
                    new double[
                            landslide.getRows()
                    ][
                            landslide.getColumns()
                    ];

            for (int row = 0;
                 row < landslide.getRows();
                 row++) {

                for (int column = 0;
                     column < landslide.getColumns();
                     column++) {

                    double value =
                            compoundHazard
                                    .getValue(row, column);

                    if (Double.isNaN(value)) {

                        classificationData[row][column] =
                                landslide.getNoDataValue();

                    } else if (value < 0.25) {

                        classificationData[row][column] = 1;

                    } else if (value < 0.50) {

                        classificationData[row][column] = 2;

                    } else if (value < 0.75) {

                        classificationData[row][column] = 3;

                    } else {

                        classificationData[row][column] = 4;
                    }
                }
            }

            Raster classifiedHazard =
                    new Raster(
                            classificationData,
                            landslide.getCellSize(),
                            landslide.getNoDataValue(),
                            landslide.getOriginX(),
                            landslide.getOriginY(),
                            landslide.getCoordinateReferenceSystem()
                    );

            System.out.println(
                    "Compound Hazard classified."
            );

            // ------------------------------------------------
            // 7. Export classified hazard raster
            // ------------------------------------------------

            String outputPath =
                    "output/Khagrachari_Compound_Hazard.tif";

            exporter.writeGeoTiff(
                    classifiedHazard,
                    outputPath
            );

            // ------------------------------------------------
            // 8. Final status
            // ------------------------------------------------

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("FINAL PIPELINE COMPLETE");
            System.out.println("----------------------------------------");

            System.out.println(
                    "Input: Landslide + Flood"
            );

            System.out.println(
                    "Method: Min-Max Normalization"
            );

            System.out.println(
                    "Model: Weighted Linear Combination"
            );

            System.out.println(
                    "Weights: 0.5 Landslide + 0.5 Flood"
            );

            System.out.println(
                    "Output: "
                    + outputPath
            );

            System.out.println("----------------------------------------");
            System.out.println();

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "ERROR during compound hazard analysis:"
            );

            e.printStackTrace();
        }
    }
}