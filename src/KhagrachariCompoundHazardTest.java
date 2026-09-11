
//Multi hazard analysis:
/* 
public class KhagrachariCompoundHazardTest {

    public static void main(String[] args) {

        RasterIO rasterIO = new RasterIO();
        RasterOperations operations = new RasterOperations();

        try {

            // =====================================================
            // 1. Load real Khagrachhari rasters
            // =====================================================

            String landslidePath =
                    "data/Khagrachari_landslide.tif";

            String floodPath =
                    "data/Khagrachari_flood.tif";

            System.out.println();
            System.out.println("========================================");
            System.out.println("   Khagrachhari Multi-Raster Analysis");
            System.out.println("========================================");


            Raster landslide =
                    rasterIO.readGeoTiff(landslidePath);

            Raster flood =
                    rasterIO.readGeoTiff(floodPath);


            System.out.println();
            System.out.println(
                    "Both rasters loaded successfully."
            );


            // =====================================================
            // 2. Perform cell-by-cell difference
            //
            // Difference = Flood - Landslide
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("CELL-BY-CELL DIFFERENCE");
            System.out.println("----------------------------------------");

            Raster difference =
                    operations.difference(
                            flood,
                            landslide
                    );


            // =====================================================
            // 3. Display difference statistics
            // =====================================================

            RasterAnalyzer analyzer =
                    new RasterAnalyzer();

            System.out.println();
            System.out.println("Difference Raster Statistics");
            System.out.println("-----------------------------");

            System.out.println(
                    "Minimum: "
                    + analyzer.minimum(difference)
            );

            System.out.println(
                    "Maximum: "
                    + analyzer.maximum(difference)
            );

            System.out.println(
                    "Valid Cells: "
                    + analyzer.countValidCells(difference)
            );

            System.out.println(
                    "NoData Cells: "
                    + analyzer.countNoDataCells(difference)
            );


            // =====================================================
            // 4. Inspect selected cells
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("SAMPLE CELL COMPARISON");
            System.out.println("----------------------------------------");

            int[][] sampleCells = {
                {0, 0},
                {0, 100},
                {100, 100},
                {1000, 500},
                {2000, 1000}
            };


            for (int[] cell : sampleCells) {

                int row = cell[0];
                int column = cell[1];

                double landslideValue =
                        landslide.getValue(row, column);

                double floodValue =
                        flood.getValue(row, column);

                double differenceValue =
                        difference.getValue(row, column);


                System.out.println();

                System.out.println(
                        "Cell ["
                        + row
                        + ", "
                        + column
                        + "]"
                );

                System.out.println(
                        "Landslide: "
                        + landslideValue
                );

                System.out.println(
                        "Flood: "
                        + floodValue
                );

                System.out.println(
                        "Flood - Landslide: "
                        + differenceValue
                );
            }


            // =====================================================
            // 5. Completion
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println(
                    "MULTI-RASTER OPERATION COMPLETE"
            );
            System.out.println("----------------------------------------");


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "ERROR during multi-raster analysis:"
            );

            e.printStackTrace();
        }
    }
}*/

/* 

public class KhagrachariCompoundHazardTest {

    public static void main(String[] args) {

        RasterIO rasterIO = new RasterIO();
        RasterOperations operations = new RasterOperations();
        RasterAnalyzer analyzer = new RasterAnalyzer();

        try {

            // =====================================================
            // 1. Load real Khagrachhari rasters
            // =====================================================

            String landslidePath =
                    "data/Khagrachari_landslide.tif";

            String floodPath =
                    "data/Khagrachari_flood.tif";

            System.out.println();
            System.out.println("========================================");
            System.out.println("   Khagrachhari Hazard Normalization");
            System.out.println("========================================");


            Raster landslide =
                    rasterIO.readGeoTiff(landslidePath);

            Raster flood =
                    rasterIO.readGeoTiff(floodPath);


            System.out.println();
            System.out.println(
                    "Both rasters loaded successfully."
            );


            // =====================================================
            // 2. Normalize Landslide
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("NORMALIZING LANDSLIDE RASTER");
            System.out.println("----------------------------------------");

            Raster normalizedLandslide =
                    operations.normalize(landslide);

            System.out.println(
                    "Normalized Landslide Minimum: "
                    + analyzer.minimum(normalizedLandslide)
            );

            System.out.println(
                    "Normalized Landslide Maximum: "
                    + analyzer.maximum(normalizedLandslide)
            );


            // =====================================================
            // 3. Normalize Flood
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("NORMALIZING FLOOD RASTER");
            System.out.println("----------------------------------------");

            Raster normalizedFlood =
                    operations.normalize(flood);

            System.out.println(
                    "Normalized Flood Minimum: "
                    + analyzer.minimum(normalizedFlood)
            );

            System.out.println(
                    "Normalized Flood Maximum: "
                    + analyzer.maximum(normalizedFlood)
            );


            // =====================================================
            // 4. Inspect sample cells
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("NORMALIZED SAMPLE CELLS");
            System.out.println("----------------------------------------");

            int[][] sampleCells = {
                {2000, 1000},
                {2500, 1200},
                {3000, 1500}
            };

            for (int[] cell : sampleCells) {

                int row = cell[0];
                int column = cell[1];

                double landslideValue =
                        normalizedLandslide
                                .getValue(row, column);

                double floodValue =
                        normalizedFlood
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
                        "Normalized Landslide: "
                        + landslideValue
                );

                System.out.println(
                        "Normalized Flood: "
                        + floodValue
                );
            }


            // =====================================================
            // 5. Completion
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println(
                    "NORMALIZATION COMPLETE"
            );
            System.out.println("----------------------------------------");


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "ERROR during normalization:"
            );

            e.printStackTrace();
        }
    }
}*/


public class KhagrachariCompoundHazardTest {

    public static void main(String[] args) {

        RasterIO rasterIO = new RasterIO();
        RasterOperations operations = new RasterOperations();
        RasterAnalyzer analyzer = new RasterAnalyzer();

        try {

            // =====================================================
            // 1. Load real Khagrachhari rasters
            // =====================================================

            String landslidePath =
                    "data/Khagrachari_landslide.tif";

            String floodPath =
                    "data/Khagrachari_flood.tif";

            System.out.println();
            System.out.println("========================================");
            System.out.println("   Khagrachhari Compound Hazard Index");
            System.out.println("========================================");


            Raster landslide =
                    rasterIO.readGeoTiff(landslidePath);

            Raster flood =
                    rasterIO.readGeoTiff(floodPath);


            System.out.println();
            System.out.println(
                    "Both rasters loaded successfully."
            );


            // =====================================================
            // 2. Normalize both hazard surfaces
            // =====================================================

            Raster normalizedLandslide =
                    operations.normalize(landslide);

            Raster normalizedFlood =
                    operations.normalize(flood);


            System.out.println();
            System.out.println(
                    "Both rasters normalized to 0–1."
            );


            // =====================================================
            // 3. Create compound hazard raster
            //
            // Equal weights:
            //
            // Landslide = 50%
            // Flood     = 50%
            // =====================================================

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


                    // Preserve NaN cells
                    if (Double.isNaN(landslideValue)
                            || Double.isNaN(floodValue)) {

                        compoundData[row][column] =
                                Double.NaN;

                    } else {

                        double compoundValue =
                                (landslideWeight
                                        * landslideValue)
                                +
                                (floodWeight
                                        * floodValue);

                        compoundData[row][column] =
                                compoundValue;
                    }
                }
            }


            // =====================================================
            // 4. Create compound Raster
            // =====================================================

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


            // =====================================================
            // 5. Analyze compound raster
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("COMPOUND HAZARD STATISTICS");
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

            System.out.println(
                    "NoData Cells: "
                    + analyzer.countNoDataCells(compoundHazard)
            );


            // =====================================================
            // 6. Inspect sample cells
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("COMPOUND HAZARD SAMPLE CELLS");
            System.out.println("----------------------------------------");

            int[][] sampleCells = {
                {2000, 1000},
                {2500, 1200},
                {3000, 1500}
            };


            for (int[] cell : sampleCells) {

                int row = cell[0];
                int column = cell[1];

                double landslideValue =
                        normalizedLandslide
                                .getValue(row, column);

                double floodValue =
                        normalizedFlood
                                .getValue(row, column);

                double compoundValue =
                        compoundHazard
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
                        "Normalized Landslide: "
                        + landslideValue
                );

                System.out.println(
                        "Normalized Flood: "
                        + floodValue
                );

                System.out.println(
                        "Compound Hazard: "
                        + compoundValue
                );
            }


            // =====================================================
            // 7. Completion
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println(
                    "COMPOUND HAZARD CALCULATION COMPLETE"
            );
            System.out.println("----------------------------------------");


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "ERROR during compound hazard analysis:"
            );

            e.printStackTrace();
        }
    }
}