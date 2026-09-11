public class KhagrachariGeoTiffValidationTest {

    public static void main(String[] args) {

        RasterIO rasterIO = new RasterIO();

        String filePath =
                "output/Khagrachari_Compound_Hazard.tif";

        try {

            System.out.println();
            System.out.println("========================================");
            System.out.println(" Khagrachhari GeoTIFF Validation");
            System.out.println("========================================");

            // ------------------------------------------------
            // 1. Read exported GeoTIFF
            // ------------------------------------------------

            Raster raster =
                    rasterIO.readGeoTiff(filePath);

            System.out.println();
            System.out.println(
                    "Exported GeoTIFF loaded successfully."
            );

            // ------------------------------------------------
            // 2. Display raster information
            // ------------------------------------------------

            rasterIO.printRasterInfo(raster);

            // ------------------------------------------------
            // 3. Count hazard classes
            // ------------------------------------------------

            int low = 0;
            int moderate = 0;
            int high = 0;
            int veryHigh = 0;
            int noData = 0;

            for (int row = 0;
                 row < raster.getRows();
                 row++) {

                for (int column = 0;
                     column < raster.getColumns();
                     column++) {

                    double value =
                            raster.getValue(row, column);

                    if (value == raster.getNoDataValue()
                            || Double.isNaN(value)) {

                        noData++;

                    } else if (value == 1) {

                        low++;

                    } else if (value == 2) {

                        moderate++;

                    } else if (value == 3) {

                        high++;

                    } else if (value == 4) {

                        veryHigh++;
                    }
                }
            }

            // ------------------------------------------------
            // 4. Print class statistics
            // ------------------------------------------------

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("HAZARD CLASS COUNTS");
            System.out.println("----------------------------------------");

            System.out.println(
                    "Low: " + low
            );

            System.out.println(
                    "Moderate: " + moderate
            );

            System.out.println(
                    "High: " + high
            );

            System.out.println(
                    "Very High: " + veryHigh
            );

            System.out.println(
                    "NoData: " + noData
            );

            // ------------------------------------------------
            // 5. Validation summary
            // ------------------------------------------------

            int validCells =
                    low
                    + moderate
                    + high
                    + veryHigh;

            int totalCells =
                    raster.getRows()
                    * raster.getColumns();

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("VALIDATION SUMMARY");
            System.out.println("----------------------------------------");

            System.out.println(
                    "Valid Cells: " + validCells
            );

            System.out.println(
                    "Total Cells: " + totalCells
            );

            if (validCells > 0) {

                System.out.println();
                System.out.println(
                        "RESULT: EXPORTED GEOTIFF IS VALID"
                );

            } else {

                System.out.println();
                System.out.println(
                        "RESULT: NO VALID HAZARD CELLS FOUND"
                );
            }

            System.out.println();
            System.out.println(
                    "========================================"
            );

        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "ERROR during validation:"
            );

            e.printStackTrace();
        }
    }
}