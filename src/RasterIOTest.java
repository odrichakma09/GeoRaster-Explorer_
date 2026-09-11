public class RasterIOTest {

    public static void main(String[] args) {

        RasterIO rasterIO = new RasterIO();

        try {

          //  String filePath = "data/Khagrachari_landslide.tif";
                    String filePath = "data/Khagrachari_flood.tif";

            Raster raster =
                    rasterIO.readGeoTiff(filePath);

            rasterIO.printRasterInfo(raster);

            System.out.println("Raster Statistics");
            System.out.println("-----------------");

            RasterAnalyzer analyzer =
                    new RasterAnalyzer();

            System.out.println(
                    "Minimum: "
                    + analyzer.minimum(raster)
            );

            System.out.println(
                    "Maximum: "
                    + analyzer.maximum(raster)
            );

            System.out.println(
                    "Mean: "
                    + analyzer.mean(raster)
            );

            System.out.println(
                    "Valid Cells: "
                    + analyzer.countValidCells(raster)
            );

            System.out.println(
                    "NoData Cells: "
                    + analyzer.countNoDataCells(raster)
            );

        } catch (Exception e) {

            System.out.println(
                    "Error reading GeoTIFF:"
            );

            e.printStackTrace();
        }
    }
}