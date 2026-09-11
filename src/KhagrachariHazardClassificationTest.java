// Demonstration classification thresholds.
// These thresholds are not scientifically validated
// hazard-category thresholds.

public class KhagrachariHazardClassificationTest {

    public static void main(String[] args) {

        RasterIO rasterIO = new RasterIO();
        RasterOperations operations = new RasterOperations();
        RasterAnalyzer analyzer = new RasterAnalyzer();

        try {

            String landslidePath =
                    "data/Khagrachari_landslide.tif";

            String floodPath =
                    "data/Khagrachari_flood.tif";

            System.out.println();
            System.out.println("========================================");
            System.out.println(" Khagrachhari Compound Hazard Classification");
            System.out.println("========================================");

            // ------------------------------------------------
            // 1. Read rasters
            // ------------------------------------------------

            Raster landslide =
                    rasterIO.readGeoTiff(landslidePath);

            Raster flood =
                    rasterIO.readGeoTiff(floodPath);

            System.out.println();
            System.out.println("Both rasters loaded successfully.");

            // ------------------------------------------------
            // 2. Normalize rasters
            // ------------------------------------------------

            Raster normalizedLandslide =
                    operations.normalize(landslide);

            Raster normalizedFlood =
                    operations.normalize(flood);

            System.out.println();
            System.out.println("Both rasters normalized to 0-1.");

            // ------------------------------------------------
            // 3. Calculate Compound Hazard Index
            // ------------------------------------------------

            double landslideWeight = 0.5;
            double floodWeight = 0.5;

            int rows =
                    normalizedLandslide.getRows();

            int columns =
                    normalizedLandslide.getColumns();

            double[][] compoundData =
                    new double[rows][columns];

            for (int row = 0; row < rows; row++) {

                for (int column = 0; column < columns; column++) {

                    double landslideValue =
                            normalizedLandslide
                                    .getValue(row, column);

                    double floodValue =
                            normalizedFlood
                                    .getValue(row, column);

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
                            normalizedLandslide.getCellSize(),
                            normalizedLandslide.getNoDataValue(),
                            normalizedLandslide.getOriginX(),
                            normalizedLandslide.getOriginY(),
                            normalizedLandslide
                                    .getCoordinateReferenceSystem()
                    );

            // ------------------------------------------------
            // 4. Classify Compound Hazard
            // ------------------------------------------------

            double[][] classificationData =
                    new double[rows][columns];

            for (int row = 0; row < rows; row++) {

                for (int column = 0; column < columns; column++) {

                    double value =
                            compoundHazard
                                    .getValue(row, column);

                    if (Double.isNaN(value)) {

                        classificationData[row][column] =
                                Double.NaN;

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
                            compoundHazard.getCellSize(),
                            compoundHazard.getNoDataValue(),
                            compoundHazard.getOriginX(),
                            compoundHazard.getOriginY(),
                            compoundHazard
                                    .getCoordinateReferenceSystem()
                    );

            // ------------------------------------------------
            // 5. Print statistics
            // ------------------------------------------------

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("COMPOUND HAZARD INDEX");
            System.out.println("----------------------------------------");

            System.out.println(
                    "Minimum: "
                    + analyzer.minimum(compoundHazard)
            );

            System.out.println(
                    "Maximum: "
                    + analyzer.maximum(compoundHazard)
            );

            System.out.println(
                    "Valid Cells: "
                    + analyzer.countValidCells(compoundHazard)
            );

            // ------------------------------------------------
            // 6. Print classification samples
            // ------------------------------------------------

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("HAZARD CLASSIFICATION SAMPLES");
            System.out.println("----------------------------------------");

            int[][] sampleCells = {
                {2000, 1000},
                {2500, 1200},
                {3000, 1500}
            };

            for (int[] cell : sampleCells) {

                int row = cell[0];
                int column = cell[1];

                double compoundValue =
                        compoundHazard
                                .getValue(row, column);

                double classValue =
                        classifiedHazard
                                .getValue(row, column);

                System.out.println();

                System.out.println(
                        "Cell ["
                        + row
                        + ", "
                        + column
                        + "]"
                );

                System.out.println(
                        "Compound Hazard Index: "
                        + compoundValue
                );

                System.out.println(
                        "Hazard Class: "
                        + classValue
                );

                if (!Double.isNaN(classValue)) {

                    if (classValue == 1) {
                        System.out.println("Category: LOW");
                    } else if (classValue == 2) {
                        System.out.println("Category: MODERATE");
                    } else if (classValue == 3) {
                        System.out.println("Category: HIGH");
                    } else if (classValue == 4) {
                        System.out.println("Category: VERY HIGH");
                    }
                }
            }

            // ------------------------------------------------
            // 7. Completion
            // ------------------------------------------------

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println(
                    "HAZARD CLASSIFICATION COMPLETE"
            );
            System.out.println("----------------------------------------");

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "ERROR during hazard classification:"
            );

            e.printStackTrace();
        }
    }
}
