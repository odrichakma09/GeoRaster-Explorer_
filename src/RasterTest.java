
public class RasterTest {

    public static void main(String[] args) {

        double[][] elevationData = {
            {10, 12, 15, 18},
         {11, -9999, 16, 20},
         {13, 15, 19, 22}

        
        };


        Raster elevationRaster = new Raster(
            elevationData,
            30.0,
            -9999.0
        );

        System.out.println("Raster Information");
        System.out.println("------------------");

        System.out.println("Rows: " + elevationRaster.getRows());
        System.out.println("Columns: " + elevationRaster.getColumns());
        System.out.println("Cell Size: " + elevationRaster.getCellSize() + " m");
        System.out.println("NoData Value: " + elevationRaster.getNoDataValue());
        




        RasterAnalyzer analyzer = new RasterAnalyzer();

System.out.println();
System.out.println("Raster Analysis");
System.out.println("----------------");

System.out.println("Minimum: " + analyzer.minimum(elevationRaster));
System.out.println("Maximum: " + analyzer.maximum(elevationRaster));
System.out.println("Mean: " + analyzer.mean(elevationRaster));
System.out.println("Valid Cells: " + analyzer.countValidCells(elevationRaster));
System.out.println("NoData Cells: " + analyzer.countNoDataCells(elevationRaster));
System.out.println(
    "Data Coverage: " +
    analyzer.dataCoverage(elevationRaster) +
    "%"
);

/* 
// -----------------------------
        // NDVI ANALYSIS
        // -----------------------------

        double[][] nirData = {
            {0.70, 0.65},
            {0.80, 0.60}
        };

        double[][] redData = {
            {0.20, 0.25},
            {0.10, 0.30}
        };


        


        Raster nirRaster = new Raster(
            nirData,
            30.0,
            -9999.0
        );

        Raster redRaster = new Raster(
            redData,
            30.0,
            -9999.0
        );


        NDVIAnalyzer ndviAnalyzer = new NDVIAnalyzer();

        Raster ndviRaster = ndviAnalyzer.calculate(
            nirRaster,
            redRaster
        );


        // -----------------------------
        // PRINT NDVI
        // -----------------------------

        System.out.println();
        System.out.println("NDVI Raster");
        System.out.println("-----------");


        analyzer.printRaster(ndviRaster);


        System.out.println();
        System.out.println("NDVI Statistics");
        System.out.println("----------------");
        System.out.println("Minimum: " + analyzer.minimum(ndviRaster));
        System.out.println("Maximum: " + analyzer.maximum(ndviRaster));
        System.out.println("Mean: " + analyzer.mean(ndviRaster));
        System.out.println("Valid Cells: " + analyzer.countValidCells(ndviRaster));
        System.out.println("NoData Cells: " + analyzer.countNoDataCells(ndviRaster));
        
        System.out.println(
 "Data Coverage: " +
    analyzer.dataCoverage(ndviRaster) +
    "%"
);
          NDVIClassifier classifier = new NDVIClassifier();
          Raster classifiedNDVI = classifier.classify(ndviRaster);
          System.out.println();
          System.out.println("NDVI Classification");
          System.out.println("--------------------");
          analyzer.printRaster(classifiedNDVI);*/






          // NDVI ANALYSIS
        // -----------------------------

        double[][] nirData = {
            {0.70, 0.65},
            {0.80, 0.60}
        };

        double[][] redData = {
            {0.20, 0.25},
            {0.10, 0.30}
        };

        Raster nirRaster = new Raster(
            nirData,
            30.0,
            -9999.0
        );

        Raster redRaster = new Raster(
            redData,
            30.0,
            -9999.0
        );

        NDVIAnalyzer ndviAnalyzer = new NDVIAnalyzer();

        Raster ndviRaster = ndviAnalyzer.calculate(
            nirRaster,
            redRaster
        );


        // -----------------------------
        // PRINT NDVI
        // -----------------------------

        System.out.println();
        System.out.println("NDVI Raster");
        System.out.println("-----------");

        analyzer.printRaster(ndviRaster);


        // -----------------------------
        // NDVI 2024
        // -----------------------------

        double[][] nirData2024 = {
            {0.80, 0.60},
            {0.75, 0.65}
        };

        double[][] redData2024 = {
            {0.20, 0.30},
            {0.15, 0.25}
        };


        Raster nirRaster2024 = new Raster(
            nirData2024,
            30.0,
            -9999.0
        );

        Raster redRaster2024 = new Raster(
            redData2024,
            30.0,
            -9999.0
        );


        Raster ndvi2024 = ndviAnalyzer.calculate(
            nirRaster2024,
            redRaster2024
        );


        // -----------------------------
        // NDVI CHANGE
        // -----------------------------

        RasterOperations operations = new RasterOperations();

        Raster ndviChange = operations.difference(
            ndvi2024,
            ndviRaster
        );


        // -----------------------------
        // PRINT NDVI CHANGE
        // -----------------------------

        System.out.println();
        System.out.println("NDVI Change");
        System.out.println("-----------");

        analyzer.printRaster(ndviChange);


        ChangeClassifier changeClassifier = new ChangeClassifier();
        Raster classifiedChange =changeClassifier.classify(ndviChange);
        System.out.println();
        System.out.println("NDVI Change Classification");
        System.out.println("--------------------------");
        analyzer.printRaster(classifiedChange);



        ///normalized change
        // -----------------------------
        // // RASTER OPERATIONS
        // // -----------------------------

        System.out.println();
        System.out.println("Raster Operations");
        System.out.println("-----------------");
        Raster normalizedChange = operations.normalize(ndviChange);
        
        System.out.println("Normalized NDVI Change");
        
        analyzer.printRaster(normalizedChange);
        
        Raster thresholdedNDVI = operations.threshold(ndviRaster, 0.5);
        System.out.println();
        System.out.println("Thresholded NDVI");
        analyzer.printRaster(thresholdedNDVI);



        ///// TEST Reclassification:

        double[] breakpoints = {0.2, 0.5};

int[] classes = {1, 2, 3};

Raster reclassifiedNDVI =
    operations.reclassify(
        ndviRaster,
        breakpoints,
        classes
    );

System.out.println();
System.out.println("Reclassified NDVI");
analyzer.printRaster(reclassifiedNDVI);




    }
}