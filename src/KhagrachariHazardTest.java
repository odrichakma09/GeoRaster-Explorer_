public class KhagrachariHazardTest {

    public static void main(String[] args) {

        RasterIO rasterIO = new RasterIO();

        try {

            // =====================================================
            // 1. File paths
            // =====================================================

            String landslidePath =
                    "data/Khagrachari_landslide.tif";

            String floodPath =
                    "data/Khagrachari_flood.tif";


            // =====================================================
            // 2. Load both rasters
            // =====================================================

            System.out.println();
            System.out.println("========================================");
            System.out.println("   Khagrachhari Hazard Raster Test");
            System.out.println("========================================");

            System.out.println();
            System.out.println("Loading landslide raster...");

            Raster landslide =
                    rasterIO.readGeoTiff(landslidePath);

            System.out.println("Landslide raster loaded successfully.");


            System.out.println();
            System.out.println("Loading flood raster...");

            Raster flood =
                    rasterIO.readGeoTiff(floodPath);

            System.out.println("Flood raster loaded successfully.");


            // =====================================================
            // 3. Display Landslide information
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("LANDSLIDE RASTER");
            System.out.println("----------------------------------------");

            rasterIO.printRasterInfo(landslide);


            // =====================================================
            // 4. Display Flood information
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("FLOOD RASTER");
            System.out.println("----------------------------------------");

            rasterIO.printRasterInfo(flood);


            // =====================================================
            // 5. Check raster compatibility
            // =====================================================

            System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("RASTER COMPATIBILITY CHECK");
            System.out.println("----------------------------------------");


            boolean sameRows =
                    landslide.getRows() == flood.getRows();

            boolean sameColumns =
                    landslide.getColumns() == flood.getColumns();

            boolean sameCellSize =
                    Math.abs(
                            landslide.getCellSize()
                            - flood.getCellSize()
                    ) < 0.000000001;

            boolean sameOriginX =
                    Math.abs(
                            landslide.getOriginX()
                            - flood.getOriginX()
                    ) < 0.000000001;

            boolean sameOriginY =
                    Math.abs(
                            landslide.getOriginY()
                            - flood.getOriginY()
                    ) < 0.000000001;

            boolean sameCRS =
                    landslide
                            .getCoordinateReferenceSystem()
                            .equals(
                                    flood
                                            .getCoordinateReferenceSystem()
                            );


            System.out.println(
                    "Same rows: " + sameRows
            );

            System.out.println(
                    "Same columns: " + sameColumns
            );

            System.out.println(
                    "Same cell size: " + sameCellSize
            );

            System.out.println(
                    "Same origin X: " + sameOriginX
            );

            System.out.println(
                    "Same origin Y: " + sameOriginY
            );

            System.out.println(
                    "Same CRS: " + sameCRS
            );


            // =====================================================
            // 6. Final compatibility result
            // =====================================================

            boolean compatible =
                    sameRows
                    && sameColumns
                    && sameCellSize
                    && sameOriginX
                    && sameOriginY
                    && sameCRS;


            System.out.println();
            System.out.println("----------------------------------------");

            if (compatible) {

                System.out.println(
                        "RESULT: RASTERS ARE COMPATIBLE"
                );

                System.out.println(
                        "They can be used together for "
                        + "cell-by-cell analysis."
                );

            } else {

                System.out.println(
                        "RESULT: RASTERS ARE NOT FULLY COMPATIBLE"
                );

                System.out.println(
                        "Further spatial alignment may be required."
                );
            }

            System.out.println("----------------------------------------");


        } catch (Exception e) {

            System.out.println();
            System.out.println(
                    "ERROR during hazard raster test:"
            );

            e.printStackTrace();
        }
    }
}